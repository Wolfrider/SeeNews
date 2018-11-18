package com.oliver.seenews.magazine.detail.content;

import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;

public interface MagazineDetailContentContract {

    interface Presenter extends IPresenter {

        void init();

    }

    interface View extends IView<Presenter> {

        void init(String content);

    }

}
