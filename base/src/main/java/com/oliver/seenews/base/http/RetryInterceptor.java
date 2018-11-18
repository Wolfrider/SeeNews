package com.oliver.seenews.base.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {

    private int mMaxRetry;
    private int mCurrent;

    public RetryInterceptor(int maxRetry) {
        mMaxRetry = maxRetry;
        mCurrent = 0;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (!response.isSuccessful() && mCurrent++ < mMaxRetry) {
            response = chain.proceed(request);
        }
        return response;
    }
}
