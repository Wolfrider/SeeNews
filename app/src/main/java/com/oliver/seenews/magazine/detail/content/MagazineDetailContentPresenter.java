package com.oliver.seenews.magazine.detail.content;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;

public class MagazineDetailContentPresenter extends LifecyclePresenter implements MagazineDetailContentContract.Presenter {

    private MagazineDetailContentContract.View mView;
    private String mContent;

    public MagazineDetailContentPresenter(@NonNull MagazineDetailContentContract.View view, String content) {
        mView = view;
        mView.setPresenter(this);
        mContent = content;
    }

    @Override
    public void init() {
        mView.init(mContent);
    }
}
