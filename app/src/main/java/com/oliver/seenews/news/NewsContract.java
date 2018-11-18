package com.oliver.seenews.news;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.news.domain.page.NewsPage;

public interface NewsContract {

    interface Presenter extends IPresenter {

    }

    interface View extends IView<Presenter> {

        void showLoadingPage();

        void showPage(@NonNull List<NewsPage> newsPages);

    }
}
