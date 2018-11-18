package com.oliver.seenews.news.page;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.news.domain.page.CardType;
import com.oliver.seenews.news.domain.page.FeedsCard;
import com.oliver.seenews.news.domain.page.INewsPageCard;

public class NewsPageAdapter extends RecyclerView.Adapter<BaseNewsPageViewHolder<INewsPageCard>> {

    private static final String TAG = "NewsPageAdapter";

    private List<INewsPageCard> mNewsPageCards;
    private NewsPageViewContext mViewContext;

    public NewsPageAdapter(@NonNull NewsPageViewContext viewContext,
                           @NonNull List<INewsPageCard> newsPageCards) {
        mViewContext = viewContext;
        mNewsPageCards = newsPageCards;
    }

    @NonNull
    @Override
    public BaseNewsPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CardType.BANNER:
                return BannerViewHolder.create(mViewContext, parent);
            case CardType.FEEDS:
                return FeedsViewHolder.create(mViewContext, parent);
            //case CardType.FOOTER:
            //    return FooterViewHolder.create(mViewContext, parent);
            default:
                break;
        }
        throw new IllegalStateException();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseNewsPageViewHolder holder, int position) {
        //if (position == mNewsPageCards.size()) {
        //    holder.bindData(null);
        //} else {
        //    holder.bindData(mNewsPageCards.get(position));
        //}
        holder.bindData(mNewsPageCards.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        //if (position == mNewsPageCards.size()) {
        //    return CardType.FOOTER;
        //} else {
        //    return mNewsPageCards.get(position).getType();
        //}
        return mNewsPageCards.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mNewsPageCards.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseNewsPageViewHolder<INewsPageCard> holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getAdapterPosition();
        if (position >= 0 && position < mNewsPageCards.size()) {
            mNewsPageCards.get(position).onShow();
            holder.onShow();
        }
        LogUtils.debug(TAG, "onViewAttachedToWindow, " + position);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseNewsPageViewHolder<INewsPageCard> holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getAdapterPosition();
        if (position >= 0 && position < mNewsPageCards.size()) {
            mNewsPageCards.get(position).onLeft();
            holder.onLeft();
        }
        LogUtils.debug(TAG, "onViewDetachedFromWindow, " + position);
    }

    public void add(List<INewsPageCard> cards) {
        int start = mNewsPageCards.size();
        mNewsPageCards.addAll(cards);
        notifyItemRangeInserted(start, cards.size());
    }
}
