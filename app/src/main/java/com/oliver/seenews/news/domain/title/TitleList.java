package com.oliver.seenews.news.domain.title;

import java.util.List;

import com.oliver.seenews.base.util.LogUtils;
import io.reactivex.Completable;

public class TitleList {

    private static final String TAG = "TitleList";

    private TitleRepository mTitleRepository;

    public TitleList() {
        mTitleRepository = new TitleRepository();
    }

    public List<TitleVO> get() {
        return mTitleRepository.get();
    }

    public TitleVO getFirst() {
        return get().get(0);
    }

    public Completable init() {
        LogUtils.debug(TAG, "init");
        return mTitleRepository.refresh();
    }

    public void sort() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{TitleList:");
        for (TitleVO titleVO: get()) {
            builder.append("\n\t").append(titleVO);
        }
        builder.append("}");
        return builder.toString();
    }
}
