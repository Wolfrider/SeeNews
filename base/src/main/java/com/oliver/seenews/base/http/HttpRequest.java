package com.oliver.seenews.base.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.Constant;
import com.oliver.seenews.base.Constant.Http;

public class HttpRequest {

    private String mUrl;
    private int mMethod;
    private Map<String, String> mHeaders;
    private Map<String, String> mForms;
    private int mTimeOut;
    private int mRetryCount;

    protected HttpRequest(Builder builder) {
        mUrl = builder.formatUrl();
        mMethod = builder.mMethod;
        mHeaders = builder.mHeaders;
        mForms = builder.mForms;
        mTimeOut = builder.mTimeOut;
        mRetryCount = builder.mRetryCount;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getMethod() {
        return mMethod;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getForms() {
        return mForms;
    }

    public int getTimeOut() {
        return mTimeOut;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public static class Builder {

        private String mUrl;
        private int mMethod = Http.Method.GET;
        private Map<String, String> mHeaders;
        private Map<String, String> mForms;
        private Map<String, String> mQueryPaths;
        private int mTimeOut = Constant.Http.Request.TIME_OUT;
        private int mRetryCount = Constant.Http.Request.RETRY_COUNT;

        public Builder() {
            mHeaders = new HashMap<>();
            mForms = new HashMap<>();
            mQueryPaths = new HashMap<>();
        }

        public Builder setUrl(@NonNull String url) {
            mUrl = url;
            return this;
        }

        public Builder setMethod(int method) {
            mMethod = method;
            return this;
        }

        public Builder setTimeOut(int timeOut) {
            if (timeOut > 0) {
                mTimeOut = timeOut;
            }
            return this;
        }

        public Builder setRetryCount(int retryCount) {
            if (mRetryCount > 0) {
                mRetryCount = retryCount;
            }
            return this;
        }

        public Builder addHeader(@NonNull String name, @NonNull String value) {
            mHeaders.put(name, value);
            return this;
        }

        public Builder addHeaders(@NonNull Map<String, String> headers) {
            mHeaders.putAll(headers);
            return this;
        }

        public Builder addForm(@NonNull String name, @NonNull String value) {
            mForms.put(name, value);
            return this;
        }

        public Builder addForms(@NonNull Map<String, String> forms) {
            mForms.putAll(forms);
            return this;
        }

        public Builder addQueryPath(@NonNull String name, @NonNull String value) {
            mQueryPaths.put(name, value);
            return this;
        }

        public Builder addQueryPaths(@NonNull Map<String, String> paths) {
            mQueryPaths.putAll(paths);
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }

        private String formatUrl() {
            StringBuilder sb = new StringBuilder(mUrl);
            if (!mQueryPaths.isEmpty()) {
                boolean hasUrlDelimiter = mUrl.contains("?");
                for (Entry<String, String> entry : mQueryPaths.entrySet()) {
                    if (hasUrlDelimiter) {
                        sb.append("&");
                    } else {
                        sb.append("?");
                        hasUrlDelimiter = true;
                    }
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
            return sb.toString();
        }
    }

}
