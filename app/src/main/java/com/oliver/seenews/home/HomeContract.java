package com.oliver.seenews.home;

import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;

public interface HomeContract {

    interface Presenter extends IPresenter {

        void onClear();

    }

    interface View extends IView<Presenter> {

        void showPage();

        void showClearTips(double size);

    }
}
