package com.oliver.seenews.base.dmm.document;

import java.util.Locale;

public class Focus extends Body {

    private String mType;
    private String mDest;

    private Focus(Builder builder) {
        super(builder);
        mType = builder.mType;
        mDest = builder.mDest;
    }

    public String getDest() {
        return mDest;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{Focus: mX = %d, mY = %d, mWidth = %d, mHeight = %d, "
            + "mType = %s, mDest = %s}", mX, mY, mWidth, mHeight, mType, mDest);
    }

    public static class Builder extends Body.Builder {
        private String mType;
        private String mDest;

        public Builder() {

        }

        public Builder setType(String type) {
            mType = type;
            return this;
        }

        public Builder setDest(String dest) {
            mDest = dest;
            return this;
        }

        @Override
        public Focus build() {
            return new Focus(this);
        }
    }
}
