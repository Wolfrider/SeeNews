package com.oliver.seenews.magazine.detail.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.base.arch.BaseFragment;

public class MagazineDetailContentFragment extends BaseFragment<MagazineDetailContentPresenter> {

    private static final String KEY_CONTENT = "content";

    public static MagazineDetailContentFragment createInstance(String content) {
        MagazineDetailContentFragment fragment = new MagazineDetailContentFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            String content = savedInstanceState.getString(KEY_CONTENT);
            mPresenter = new MagazineDetailContentPresenter(new MagazineDetailContentView(this), content);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.magazine_detail_content_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.init();
    }
}
