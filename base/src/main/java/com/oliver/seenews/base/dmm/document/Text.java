package com.oliver.seenews.base.dmm.document;

import java.util.Locale;

import android.graphics.Color;

public class Text extends Body {

    private int mFontColor;
    private int mFontSize;
    private String mContent;

    private Text(Builder builder) {
        super(builder);
        mFontColor = builder.mFontColor;
        mFontSize = builder.mFontSize;
        mContent = builder.mContent;
    }

    public int getFontColor() {
        return mFontColor;
    }

    public int getFontSize() {
        return mFontSize;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{Text, mX = %d, mY = %d, mWidth = %d, mHeight = %d, "
            + "mFontColor = %x, mFontSize = %d, mContent.size = %d}", mX, mY, mWidth, mHeight,
            mFontColor, mFontSize, mContent.length());
    }

    private String toContentString() {
        final int contentLimitSize = 100;
        if (mContent.length() > contentLimitSize) {
            return String.format(Locale.CHINESE, "%s ... %s", mContent.substring(0, contentLimitSize/2), mContent.substring(mContent.length() - contentLimitSize/2));
        } else {
            return mContent;
        }
    }

    public static class Builder extends Body.Builder {

        private String mContent;
        private int mFontColor;
        private int mFontSize;

        public Builder() {

        }

        public Builder setContent(String content) {
            mContent = formatContent(content);
            return this;
        }

        public Builder setFontColor(String color) {
            mFontColor = formatColor(color);
            return this;
        }

        public Builder setFontSize(int size) {
            mFontSize = size;
            return this;
        }

        @Override
        public Text build() {
            return new Text(this);
        }

        private String formatContent(String content) {
            return content.replace("&#xD;&#xA;", "\r\n");
        }

        private int formatColor(String color) {
            return Color.parseColor(color.replace("0x", "#"));
        }
    }
}
