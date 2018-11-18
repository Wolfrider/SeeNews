package com.oliver.seenews.magazine.detail.comments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.base.arch.BaseFragment;

public class MagazineDetailCommentsFragment extends BaseFragment<MagazineDetailCommentsPresenter> {

    private static final String PARAM_ID = "id";

    public static MagazineDetailCommentsFragment createInstance(int id) {
        MagazineDetailCommentsFragment fragment = new MagazineDetailCommentsFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            int id = savedInstanceState.getInt(PARAM_ID);
            mPresenter = new MagazineDetailCommentsPresenter(new MagazineDetailCommentsView(this), id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.magazine_detail_comments_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.init();
    }
}
