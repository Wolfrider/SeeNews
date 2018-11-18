package com.oliver.seenews.news.domain.page;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import android.support.annotation.NonNull;
import com.oliver.seenews.news.domain.title.TitleVO;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class NewsPage {

    private TitleVO mTitleVO;
    private BannerRepository mBannerRepository;
    private FeedsRepository mFeedsRepository;

    public NewsPage(@NonNull TitleVO titleVO) {
        mTitleVO = titleVO;
        mBannerRepository = new BannerRepository(mTitleVO);
        mFeedsRepository = new FeedsRepository(mTitleVO);
    }

    public TitleVO getTitle() {
        return mTitleVO;
    }

    public Single<List<INewsPageCard>> refresh() {
        return mBannerRepository.refresh().mergeWith(mFeedsRepository.refresh()).toSingle(
            new Callable<List<INewsPageCard>>() {
                @Override
                public List<INewsPageCard> call() throws Exception {
                    List<INewsPageCard> newsPageCards = new LinkedList<>();
                    newsPageCards.add(mBannerRepository.get());
                    newsPageCards.addAll(mFeedsRepository.get());
                    return newsPageCards;
                }
            });
    }

    public Single<List<INewsPageCard>> load() {
        return mFeedsRepository.load().map(new Function<List<FeedsCard>, List<INewsPageCard>>() {
            @Override
            public List<INewsPageCard> apply(List<FeedsCard> feedsCards) throws Exception {
                return new LinkedList<INewsPageCard>(feedsCards);
            }
        });
    }

}
