package com.oliver.seenews.magazine;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.magazine.domain.MagazineItem;
import com.oliver.seenews.news.domain.page.NewsPage;

public interface MagazineContract {

    interface Presenter extends IPresenter {

        void init();

        void onLoad();

    }

    interface View extends IView<Presenter> {

        void init();

        void showPage(@NonNull List<MagazineItem> magazineItems);

        void add(@NonNull List<MagazineItem> magazineItems);


    }
}
