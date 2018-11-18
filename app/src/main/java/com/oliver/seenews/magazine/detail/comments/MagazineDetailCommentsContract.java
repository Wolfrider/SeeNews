package com.oliver.seenews.magazine.detail.comments;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.IPresenter;
import com.oliver.seenews.base.arch.IView;
import com.oliver.seenews.magazine.domain.MagazineCommentItem;

public interface MagazineDetailCommentsContract {

    interface Presenter extends IPresenter {

        void init();

        void onLoad();

    }

    interface View extends IView<Presenter> {
        void init();

        void showMore(@NonNull List<MagazineCommentItem> commentItems);
    }
}
