package com.oliver.seenews.common.http;

import com.alibaba.fastjson.JSON;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.http.HttpCommunication;
import com.oliver.seenews.base.http.HttpResponse;
import com.oliver.seenews.base.http.IHttpListener;
import com.oliver.seenews.base.util.LogUtils;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class HttpDispatcher {

    private static final String TAG = "HttpDispatcher";

    private DispatcherDelegate mDelegate;

    private static class Holder {
        private static final HttpDispatcher INSTANCE = new HttpDispatcher();
    }

    private HttpDispatcher() {
        mDelegate = new DispatcherDelegate();
    }

    public static HttpDispatcher getInstance() {
        return Holder.INSTANCE;
    }

    public Single<ResponseData> sendRequest(@NonNull final RequestParams requestParams) {
        return Single.create(new SingleOnSubscribe<ResponseData>() {
            @Override
            public void subscribe(final SingleEmitter<ResponseData> emitter) throws Exception {
                HttpCommunication.getInstance().sendRequestAsync(mDelegate.createHttpRequest(requestParams),
                    new IHttpListener() {
                        @Override
                        public void onResponse(HttpResponse response) {
                            final ResponseData responseData;
                            if (response.isSuccessful()) {
                                responseData = new ResponseData(response.getCode(),
                                    JSON.parseObject(response.getData(), requestParams.getResClass()));
                            } else {
                                responseData = new ResponseData(response.getCode());
                            }
                            emitter.onSuccess(responseData);
                        }
                    });
            }
        });
    }
}
