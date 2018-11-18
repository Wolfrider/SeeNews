package com.oliver.seenews.magazine.detail.comments;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.rxjava.BaseSingleObserver;
import com.oliver.seenews.magazine.domain.MagazineCommentItem;
import com.oliver.seenews.magazine.domain.MagazineComments;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MagazineDetailCommentsPresenter extends LifecyclePresenter implements MagazineDetailCommentsContract.Presenter {

    private static final String TAG = "MagazineDetailCommentsPresenter";

    private MagazineDetailCommentsContract.View mView;
    private MagazineComments mComments;

    public MagazineDetailCommentsPresenter(@NonNull MagazineDetailCommentsContract.View view, int id) {
        mView = view;
        mView.setPresenter(this);
        mComments = new MagazineComments(id);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        onLoad();
    }

    @Override
    public void init() {
        mView.init();
    }

    @Override
    public void onLoad() {
        mComments.loadComments().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSingleObserver<List<MagazineCommentItem>>() {
                @Override
                public void onSuccess(List<MagazineCommentItem> commentItems) {
                    super.onSuccess(commentItems);
                    mView.showMore(commentItems);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
    }

}
