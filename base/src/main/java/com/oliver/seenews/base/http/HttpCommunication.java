package com.oliver.seenews.base.http;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLPeerUnverifiedException;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.Constant;
import com.oliver.seenews.base.Constant.Http;
import com.oliver.seenews.base.Constant.Http.Header;
import com.oliver.seenews.base.util.NumUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpCommunication {

    private boolean mLogging;
    private CookieJar mCookieJar;
    private String mUserAgent;

    private static class Holder {
        private static final HttpCommunication INSTANCE = new HttpCommunication() ;
    }

    private HttpCommunication() {
        mCookieJar = new CookieJar() {
            private Map<String, List<Cookie>> mCookies = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                mCookies.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                if (mCookies.containsKey(url.host())) {
                    return mCookies.get(url.host());
                }
                return Collections.emptyList();
            }
        };
    }

    public static HttpCommunication getInstance() {
        return Holder.INSTANCE;
    }

    public void openLog() {
        mLogging = true;
    }

    public void setUserAgent(@NonNull String userAgent) {
        mUserAgent = userAgent;
    }

    public void sendRequestAsync(@NonNull HttpRequest httpRequest, @NonNull final IHttpListener listener) {
        OkHttpClient okHttpClient = createHttpClient(httpRequest);
        okHttpClient.newCall(createRequest(httpRequest)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                HttpResponse.Builder builder = new HttpResponse.Builder();
                builder.setCode(getErrorCode(e));
                listener.onResponse(builder.build());
            }

            @Override
            public void onResponse(Call call, Response response) {
                HttpResponse.Builder builder = new HttpResponse.Builder();
                try {
                    ResponseBody responseBody = response.body();
                    if (null != responseBody) {
                        builder.setBytes(responseBody.bytes());
                    }
                    builder.setCode(response.code());
                } catch (IOException ex) {
                    builder.setCode(getErrorCode(ex));
                } finally {
                    listener.onResponse(builder.build());
                }
            }
        });
    }

    public void sendDownloadRequest(@NonNull HttpDownloadRequest httpRequest, @NonNull final IHttpDownloadListener listener) {
        OkHttpClient okHttpClient = createHttpClient(httpRequest);
        try {
            Response response = okHttpClient.newCall(createRequest(httpRequest)).execute();
            if (!response.isSuccessful()) {
                listener.onFail(response.code());
                return;
            }
            ResponseBody body = response.body();
            if (null == body) {
                listener.onFail(Http.ErrorCode.NO_RESPONSE_BODY);
                return;
            }
            int contentLength = NumUtils.toInt(response.header(Header.CONTENT_LENGTH));
            listener.onStart(contentLength);
            byte[] buffer = new byte[Constant.BUFFER_SIZE];
            int length;
            try (FileOutputStream outputStream = new FileOutputStream(httpRequest.getFilePath())) {
                try (InputStream inputStream = body.byteStream()) {
                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                        if (contentLength > 0) {
                            listener.onProgress(length);
                        }
                    }
                }
            }
            listener.onFinish();
        } catch (IOException ex) {
            ex.printStackTrace();
            listener.onFail(getErrorCode(ex));
        }
    }

    private OkHttpClient createHttpClient(HttpRequest httpRequest) {
        OkHttpClient.Builder builder = new Builder();
        builder.followRedirects(true)
            .connectTimeout(httpRequest.getTimeOut(), TimeUnit.SECONDS)
            .readTimeout(httpRequest.getTimeOut(), TimeUnit.SECONDS)
            .writeTimeout(httpRequest.getTimeOut(), TimeUnit.SECONDS)
            .cookieJar(mCookieJar)
            .connectionPool(new ConnectionPool());
        if (httpRequest.getRetryCount() > 0) {
            builder.addInterceptor(new RetryInterceptor(httpRequest.getRetryCount()));
        }
        if (mLogging) {
            builder.addInterceptor(new LoggingInterceptor());
        }
        return builder.build();
    }

    private Request createRequest(HttpRequest httpRequest) {
        Request.Builder builder = new Request.Builder();
        builder.url(httpRequest.getUrl());
        for (Entry<String, String> entry : httpRequest.getHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        if (httpRequest.getMethod() == Http.Method.POST) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Entry<String, String> entry : httpRequest.getForms().entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.post(formBuilder.build());
        } else {
            builder.get();
        }
        builder.addHeader("User-Agent", mUserAgent);
        return builder.build();
    }

    private int getErrorCode(IOException ex) {
        if (ex instanceof NoRouteToHostException) {
            return Http.ErrorCode.NO_ROUTE_HOST;
        } else if (ex instanceof ConnectException) {
            return Http.ErrorCode.CONNECT_EXCEPTION;
        } else if (ex instanceof SocketException) {
            return Http.ErrorCode.SOCKET_EXCEPTION;
        } else if (ex instanceof SocketTimeoutException) {
            return Http.ErrorCode.SOCKET_TIME_OUT;
        } else if (ex instanceof UnknownServiceException) {
            return Http.ErrorCode.UNKNOWN_SERVICE;
        } else if (ex instanceof UnknownHostException) {
            return Http.ErrorCode.UNKNOWN_HOST;
        } else if (ex instanceof SSLPeerUnverifiedException) {
            return Http.ErrorCode.SSL_PEER_UNVERIFIED;
        }
        return Http.ErrorCode.UNKNOWN;
    }

}
