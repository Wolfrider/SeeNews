package com.oliver.seenews.welcome;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.oliver.seenews.R;
import com.oliver.seenews.welcome.WelcomeContract.Presenter;
import io.reactivex.disposables.Disposable;

public class WelcomeView implements WelcomeContract.View {

    private static final String TAG = "WelcomeView";

    private WelcomeContract.Presenter mPresenter;
    private AppCompatActivity mActivity;

    public WelcomeView(@NonNull AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPage() {
        mActivity.setContentView(R.layout.welcome_activity);
    }

    @Override
    public void closePage() {
        mActivity.finish();
    }

}
