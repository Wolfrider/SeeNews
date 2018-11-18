package com.oliver.seenews.magazine.read;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.oliver.seenews.base.arch.BaseActivity;
import com.oliver.seenews.common.Constant;

@Route(path = Constant.ARouter.MAGAZINE_READ_PATH)
public class MagazineReaderActivity extends BaseActivity<MagazineReaderPresenter> {


    @Autowired(name = Constant.ARouter.PARAM_PATH, required = true)
    String mLocalDmmPath;

    @Autowired(name = Constant.ARouter.PARAM_TITLE, required = true)
    String mTitle;

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mPresenter = new MagazineReaderPresenter(new MagazineReaderView(this, mTitle), mLocalDmmPath);
    }
}
