package com.oliver.seenews.magazine.detail;

import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.magazine.domain.MagazineDetailItem;

public interface MagazineDetailContract {

    interface Presenter extends IPresenter {

    }

    interface View extends IView<Presenter> {

        void init();

        void showPage(MagazineDetailItem detailItem);

        void showError();

    }
}
