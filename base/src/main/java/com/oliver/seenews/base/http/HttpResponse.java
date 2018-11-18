package com.oliver.seenews.base.http;

import java.util.Locale;

public class HttpResponse {
    private byte[] mData;
    private int mCode;

    private HttpResponse(Builder builder) {
        mData = builder.mData;
        mCode = builder.mCode;
    }

    public byte[] getData() {
        return mData;
    }

    public int getCode() {
        return mCode;
    }

    public boolean isSuccessful() {
        return mCode >= 200 && mCode < 400;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{HttpResponse: mCode = %d}", mCode);
    }

    public static class Builder {

        private byte[] mData;
        private int mCode;

        public Builder() {
        }

        public Builder setBytes(byte[] data) {
            mData = data;
            return this;
        }

        public Builder setCode(int code) {
            mCode = code;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
