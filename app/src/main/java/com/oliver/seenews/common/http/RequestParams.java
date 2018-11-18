package com.oliver.seenews.common.http;

public class RequestParams {

    private int mType;
    private Class mResClass;
    private int mTitleId;
    private int mPageNo;
    private int mDetailId;
    private int mMagazineId;

    private RequestParams(Builder builder) {
        mType = builder.mType;
        mResClass = builder.mResClass;
        mTitleId = builder.mTitleId;
        mPageNo = builder.mPageNo;
        mDetailId = builder.mDetailId;
        mMagazineId = builder.mMagazineId;
    }

    public int getType() {
        return mType;
    }

    public Class getResClass() {
        return mResClass;
    }

    public int getTitleId() {
        return mTitleId;
    }

    public int getPageNo() {
        return mPageNo;
    }

    public int getDetailId() {
        return mDetailId;
    }

    public int getMagazineId() {
        return mMagazineId;
    }

    public static class Builder {
        private int mType;
        private Class mResClass;
        private int mTitleId;
        private int mPageNo;
        private int mDetailId;
        private int mMagazineId;

        public Builder setType(int type) {
            mType = type;
            return this;
        }

        public Builder setResClass(Class resClass) {
            mResClass = resClass;
            return this;
        }

        public Builder setTitleId(int titleId) {
            mTitleId = titleId;
            return this;
        }

        public Builder setPageNo(int pageNo) {
            mPageNo = pageNo;
            return this;
        }

        public Builder setDetailId(int detailId) {
            mDetailId = detailId;
            return this;
        }

        public Builder setMagazineId(int magazineId) {
            mMagazineId = magazineId;
            return this;
        }

        public RequestParams build() {
            return new RequestParams(this);
        }

    }

}
