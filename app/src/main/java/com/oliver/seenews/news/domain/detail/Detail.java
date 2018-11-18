package com.oliver.seenews.news.domain.detail;

import java.util.Locale;

import android.support.annotation.NonNull;

public class Detail {

    private int mId;
    private String mTitle;
    private String mContent;
    private String mAuthor;
    private String mPublishTime;

    public Detail() {
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setContent(@NonNull String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public void setAuthor(@NonNull String author) {
        mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setPublishTime(@NonNull String time) {
        mPublishTime = time.split(" ")[0];
    }

    public String getPublishTime() {
        return mPublishTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.CHINA, "{Detail: mId = %d, mTitle = %s, mAuthor = %s, mPublishTime = %s}",
            mId, mTitle, mAuthor, mPublishTime);
    }
}
