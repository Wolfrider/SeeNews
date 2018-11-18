package com.oliver.seenews.news.page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.news.domain.page.INewsPageCard;

public class FooterViewHolder extends BaseNewsPageViewHolder<INewsPageCard> {

    private AppCompatTextView mLoadingTextView;

    public static FooterViewHolder create(@NonNull NewsPageViewContext viewContext, @NonNull ViewGroup parent) {
        return new FooterViewHolder(viewContext,
            LayoutInflater.from(viewContext.getContext()).inflate(R.layout.news_page_loading, parent, false));
    }

    private FooterViewHolder(@NonNull NewsPageViewContext viewContext, @NonNull View itemView) {
        super(viewContext, itemView);
        mLoadingTextView = itemView.findViewById(R.id.news_page_loading_text);
    }

    @Override
    public void bindData(INewsPageCard card) {
        mLoadingTextView.setText(R.string.news_page_loading);
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onLeft() {

    }
}
