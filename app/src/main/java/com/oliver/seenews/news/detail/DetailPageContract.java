package com.oliver.seenews.news.detail;

import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.news.domain.detail.Detail;

public interface DetailPageContract {

    interface Presenter extends IPresenter {

    }

    interface View extends IView<Presenter> {

        void init();

        void showPage(Detail detail);

    }
}
