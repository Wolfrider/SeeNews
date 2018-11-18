package com.oliver.seenews.base.dmm.document;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.support.annotation.NonNull;

public class Magazine implements Iterable<Page> {

    private int mWidth;
    private int mHeight;
    private List<Page> mPages;

    private Magazine(Builder builder) {
        mWidth = builder.mWidth;
        mHeight = builder.mHeight;
        mPages = builder.mPages;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public List<Page> getPages() {
        return mPages;
    }

    @NonNull
    @Override
    public Iterator<Page> iterator() {
        return new Iter();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{Magazine:");
        sb.append(String.format(Locale.US, " mWidth = %d, mHeight = %d, mPages = ", mWidth, mHeight));
        for (Page page: this) {
            sb.append(String.format(Locale.US, "\n\t %s", page));
        }
        sb.append("}");
        return sb.toString();
    }

    public static class Builder {
        private int mWidth;
        private int mHeight;
        private List<Page> mPages;

        public Builder() {
            mPages = new LinkedList<>();
        }

        public Builder setWidth(int width) {
            mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mHeight = height;
            return this;
        }

        public Builder addPage(@NonNull Page page) {
            mPages.add(page);
            return this;
        }

        public Magazine build() {
            return new Magazine(this);
        }
    }

    private class Iter implements Iterator<Page> {

        private int mIndex;

        Iter() {
            mIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return mIndex < mPages.size();
        }

        @Override
        public Page next() {
            return mPages.get(mIndex++);
        }
    }
}
