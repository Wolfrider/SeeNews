package com.oliver.seenews.magazine.read;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.base.dmm.document.Magazine;

public interface MagazineReaderContract {

    interface Presenter extends IPresenter {

    }

    interface View extends IView<Presenter> {

        void init();

        void load(@NonNull String localDirPath, @NonNull Magazine magazine);
    }
}
