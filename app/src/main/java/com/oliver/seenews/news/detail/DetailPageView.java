package com.oliver.seenews.news.detail;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.AssetUtils;
import com.oliver.seenews.base.widget.LoadingDialog;
import com.oliver.seenews.common.Constant.DetailPage;
import com.oliver.seenews.news.detail.DetailPageContract.Presenter;
import com.oliver.seenews.news.domain.detail.Detail;

public class DetailPageView implements DetailPageContract.View {

    private static final String TAG = "DetailPageView";

    private DetailPageContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private LoadingDialog mLoadingDialog;
    private Toolbar mToolbar;
    private WebView mWebView;

    public DetailPageView(@NonNull AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init() {
        mActivity.setContentView(R.layout.news_detail_page_activity);
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(mActivity.getSupportFragmentManager(), TAG);
        initToolbar();
        initWebView();
    }

    @Override
    public void showPage(Detail detail) {
        mLoadingDialog.dismiss();
        String content = getDisplayContent(detail);
        mWebView.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");
    }

    private void initWebView() {
        mWebView = mActivity.findViewById(R.id.detail_page_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setScrollContainer(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
    }

    private void initToolbar() {
        mToolbar = mActivity.findViewById(R.id.detail_page_toolbar);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.detail_close);
        mActivity.getSupportActionBar().setTitle(R.string.home_news);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    private String getDisplayContent(Detail detail) {
        String template = AssetUtils.read(DetailPage.TEMPLATE_PATH);
        //LogUtils.debug(TAG, "getDisplayContent, " + template);
        return template.replace(DetailPage.TITLE_TAG, detail.getTitle())
            .replace(DetailPage.TIME_TAG, detail.getPublishTime())
            .replace(DetailPage.AUTHOR_TAG, detail.getAuthor())
            .replace(DetailPage.DESC_TAG, detail.getContent());
    }


}
