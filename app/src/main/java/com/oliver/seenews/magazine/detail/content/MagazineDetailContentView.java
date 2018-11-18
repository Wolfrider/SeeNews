package com.oliver.seenews.magazine.detail.content;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.oliver.seenews.R;
import com.oliver.seenews.magazine.detail.content.MagazineDetailContentContract.Presenter;

public class MagazineDetailContentView implements MagazineDetailContentContract.View {

    private Fragment mFragment;
    private MagazineDetailContentContract.Presenter mPresenter;

    public MagazineDetailContentView(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init(String content) {
        View view = mFragment.getView();
        AppCompatTextView contentView = view.findViewById(R.id.magazine_detail_content);
        contentView.setText(content);
    }
}
