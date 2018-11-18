package com.oliver.seenews.home;

import com.alibaba.android.arouter.facade.annotation.Route;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.oliver.seenews.base.arch.BaseActivity;
import com.oliver.seenews.common.Constant.ARouter;

@Route(path = ARouter.HOME_PATH)
public class HomeActivity extends BaseActivity<HomePresenter> {

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        mPresenter = new HomePresenter(new HomeView(this));
    }

}
