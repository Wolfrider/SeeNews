package com.oliver.seenews.news.page;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.oliver.seenews.news.domain.page.INewsPageCard;

public abstract class BaseNewsPageViewHolder<T extends INewsPageCard> extends RecyclerView.ViewHolder {

    protected NewsPageViewContext mViewContext;

    public BaseNewsPageViewHolder(NewsPageViewContext viewContext, View itemView) {
        super(itemView);
        mViewContext = viewContext;
    }

    public abstract void bindData(T card);

    public abstract void onShow();

    public abstract void onLeft();


}
