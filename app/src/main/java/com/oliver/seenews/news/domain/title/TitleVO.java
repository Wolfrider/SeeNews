package com.oliver.seenews.news.domain.title;

import java.util.Locale;

import android.text.TextUtils;

public class TitleVO {

    private int mId;
    private String mName;

    public TitleVO() {
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TitleVO) {
            TitleVO other = (TitleVO)obj;
            return mId == other.mId && TextUtils.equals(mName, other.mName);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{TitleVO: mId = %s, mName = %s}", mId, mName);
    }
}
