package com.oliver.seenews.magazine.detail.comments;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.oliver.seenews.magazine.domain.MagazineCommentItem;

public class MagazineCommentsAdapter extends RecyclerView.Adapter<MagazineCommentViewHolder> {

    private Context mContext;
    private List<MagazineCommentItem> mComments;

    public MagazineCommentsAdapter(@NonNull Context context, @NonNull List<MagazineCommentItem> comments) {
        mContext = context;
        mComments = comments;
    }

    @NonNull
    @Override
    public MagazineCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MagazineCommentViewHolder.create(mContext, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MagazineCommentViewHolder holder, int position) {
        holder.bindData(mComments.get(position));
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MagazineCommentViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getAdapterPosition();
        mComments.get(position).onShow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MagazineCommentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getAdapterPosition();
        mComments.get(position).onLeft();
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void add(@NonNull List<MagazineCommentItem> newComments) {
        int start = mComments.size();
        mComments.addAll(newComments);
        notifyItemRangeInserted(start, newComments.size());
    }
}
