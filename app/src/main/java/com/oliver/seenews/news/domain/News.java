package com.oliver.seenews.news.domain;

import java.util.LinkedList;
import java.util.List;

import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.news.domain.page.NewsPage;
import com.oliver.seenews.news.domain.title.TitleList;
import com.oliver.seenews.news.domain.title.TitleVO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

public class News {

    private static final String TAG = "News";

    private TitleList mTitleList;
    private List<NewsPage> mNewsPages;

    public News() {
        mTitleList = new TitleList();
        mNewsPages = new LinkedList<>();
    }

    public Completable init() {
        Completable initNewPage = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                mNewsPages.clear();
                LogUtils.info(TAG, mTitleList.toString());
                for (TitleVO titleVO: mTitleList.get()) {
                    mNewsPages.add(new NewsPage(titleVO));
                }
                emitter.onComplete();
            }
        });
        return mTitleList.init().andThen(initNewPage);
    }

    public TitleList getTitleList() {
        return mTitleList;
    }

    public List<NewsPage> getNewsPages() {
        return mNewsPages;
    }

    public NewsPage find(int id) {
        for (NewsPage newsPage: mNewsPages) {
            if (newsPage.getTitle().getId() == id) {
                return newsPage;
            }
        }
        return null;
    }

}
