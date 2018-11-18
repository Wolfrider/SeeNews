package com.oliver.seenews.base.dmm.document;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.support.annotation.NonNull;

public class Page implements Iterable<Body> {

    private String mName;
    private String mThumbnail;
    private List<Body> mBodyList;

    private Page(Builder builder) {
        mName = builder.mName;
        mThumbnail = builder.mThumbnail;
        mBodyList = builder.mBodyList;
    }

    public String getName() {
        return mName;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{Page:");
        sb.append(String.format(Locale.US, " mName = %s, mThumbnail = %s, mBodyList = ", mName, mThumbnail));
        for (Body body: this) {
            sb.append(String.format(Locale.US, "\n\t\t %s", body));
        }
        sb.append("}");
        return sb.toString();
    }

    @NonNull
    @Override
    public Iterator<Body> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Body> {

        private int mIndex;

        public Iter() {
            mIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return mIndex < mBodyList.size();
        }

        @Override
        public Body next() {
            return mBodyList.get(mIndex++);
        }
    }

    public static class Builder {

        private String mName;
        private String mThumbnail;
        private List<Body> mBodyList;

        public Builder() {
            mBodyList = new LinkedList<>();
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setThumbnail(String thumbnail) {
            mThumbnail = thumbnail;
            return this;
        }

        public Builder addBody(@NonNull Body body) {
            mBodyList.add(body);
            return this;
        }

        public Page build() {
            return new Page(this);
        }

    }
}
