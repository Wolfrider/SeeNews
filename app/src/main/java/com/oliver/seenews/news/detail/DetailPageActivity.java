package com.oliver.seenews.news.detail;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.oliver.seenews.base.arch.BaseActivity;
import com.oliver.seenews.common.Constant;

@Route(path = Constant.ARouter.DETAIL_PATH)
public class DetailPageActivity extends BaseActivity<DetailPagePresenter> {

    @Autowired(name = Constant.ARouter.PARAM_ID, required = true)
    int mId;

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mPresenter = new DetailPagePresenter(new DetailPageView(this), mId);
    }
}
