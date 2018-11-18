package com.oliver.seenews.welcome;

import com.alibaba.android.arouter.facade.annotation.Route;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.oliver.seenews.base.arch.BaseActivity;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant.ARouter;

@Route(path = ARouter.WELCOME_PATH)
public class WelcomeActivity extends BaseActivity<WelcomePresenter> {

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        LogUtils.debug(TAG, "onCreateStart");
        mPresenter = new WelcomePresenter(new WelcomeView(this));
    }

    @Override
    protected void onDestroyFinish() {
        super.onDestroyFinish();
        LogUtils.debug(TAG, "onDestroyFinish");
    }
}
