package com.oliver.seenews.base.http;

import java.io.IOException;

import com.oliver.seenews.base.util.LogUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "LoggingInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.verbose(TAG, String.format("%s, %s, %s", request.method(), request.url().toString(), request.headers().toString()));
        return chain.proceed(request);
    }
}
