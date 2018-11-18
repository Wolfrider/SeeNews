package com.oliver.seenews.news.page;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.news.domain.page.BannerCard;
import com.oliver.seenews.news.domain.page.FeedsCard;
import com.oliver.seenews.news.domain.page.INewsPageCard;

public interface NewsPageContract {

    interface Presenter extends IPresenter {

        void init();

        void onLoad();
    }

    interface View extends IView<Presenter> {

        void init();

        void showPage(@NonNull List<INewsPageCard> newsPageCards);

        void showMore(@NonNull List<INewsPageCard> feedsCards);

        void start();

        void stop();
    }


}
