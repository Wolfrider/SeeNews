package com.oliver.seenews.common.http;

import java.util.Locale;

public class ResponseData {

    private int mCode;
    private Object mData;

    public ResponseData(int code) {
        this(code, null);
    }

    public ResponseData(int code, Object data) {
        mCode = code;
        mData = data;
    }

    public boolean isSuccessful() {
        return mCode >= 200 && mCode < 400;
    }

    public int getCode() {
        return mCode;
    }

    public Object getData() {
        return mData;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{ResponseData: mCode = %d}", mCode);
    }
}
