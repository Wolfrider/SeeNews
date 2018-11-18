package com.oliver.seenews.base.dmm.document;

public abstract class Body {

    protected int mX;
    protected int mY;
    protected int mWidth;
    protected int mHeight;

    public Body(Builder builder) {
        mX = builder.mX;
        mY = builder.mY;
        mWidth = builder.mWidth;
        mHeight = builder.mHeight;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public static abstract class Builder {

        protected int mX;
        protected int mY;
        protected int mWidth;
        protected int mHeight;

        public Builder() {

        }

        public Builder setX(int x) {
            mX = x;
            return this;
        }

        public Builder setY(int y) {
            mY = y;
            return this;
        }

        public Builder setWidth(int width) {
            mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mHeight = height;
            return this;
        }

        public abstract Body build();
    }

}
