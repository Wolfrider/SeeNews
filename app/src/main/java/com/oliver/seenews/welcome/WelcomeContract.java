package com.oliver.seenews.welcome;

import com.oliver.seenews.base.arch.ILifecycleListener;
import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;

public interface WelcomeContract {

    interface View extends IView<Presenter> {

        void showPage();

        void closePage();

    }

    interface Presenter extends IPresenter {

        ILifecycleListener getLifecycleListener();
    }

    interface UseCaseListener {

        void onFinish();
    }
}
