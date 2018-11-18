package com.oliver.seenews.magazine.detail;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.oliver.seenews.base.arch.BaseActivity;
import com.oliver.seenews.common.Constant;

@Route(path = Constant.ARouter.MAGAZINE_DETAIL_PATH)
public class MagazineDetailActivity extends BaseActivity<MagazineDetailPresenter> {

    @Autowired(name = Constant.ARouter.PARAM_ID, required = true)
    int mId;

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mPresenter = new MagazineDetailPresenter(new MagazineDetailView(this), mId);
    }
}
