package com.oliver.seenews.base.dmm.document;

import java.util.Locale;

public class Img extends Body {

    private String mSrc;

    private Img(Builder builder) {
        super(builder);
        mSrc = builder.mSrc;
    }

    public String getSrc() {
        return mSrc;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{Img: mX = %d, mY = %d, mWidth = %d, mHeight = %d, "
            + "mSrc = %s}", mX, mY, mWidth, mHeight, mSrc);
    }

    public static class Builder extends Body.Builder {
        private String mSrc;

        public Builder() {

        }

        public Builder setSrc(String src) {
            mSrc = src;
            return this;
        }

        @Override
        public Img build() {
            return new Img(this);
        }
    }
}
