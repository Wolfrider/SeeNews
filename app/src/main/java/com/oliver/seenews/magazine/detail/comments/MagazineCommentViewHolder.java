package com.oliver.seenews.magazine.detail.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.oliver.seenews.R;
import com.oliver.seenews.magazine.domain.MagazineCommentItem;

public class MagazineCommentViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private AppCompatImageView mAvatarView;
    private AppCompatTextView mUserNameView;
    private AppCompatTextView mPublishTimeView;
    private AppCompatRatingBar mRatingBar;
    private AppCompatTextView mContentView;

    public static MagazineCommentViewHolder create(@NonNull Context context, @NonNull ViewGroup parent) {
        return new MagazineCommentViewHolder(context,
            LayoutInflater.from(context).inflate(R.layout.magazine_detail_comment_layout, parent, false));
    }

    public MagazineCommentViewHolder(@NonNull Context context, View itemView) {
        super(itemView);
        mContext = context;
        mAvatarView = itemView.findViewById(R.id.magazine_comment_avatar);
        mUserNameView = itemView.findViewById(R.id.magazine_comment_user_name);
        mPublishTimeView = itemView.findViewById(R.id.magazine_comment_publish_time);
        mRatingBar = itemView.findViewById(R.id.magazine_comment_rating);
        mContentView = itemView.findViewById(R.id.magazine_comment_content);
    }

    public void bindData(@NonNull MagazineCommentItem commentVO) {
        Glide.with(mContext).load(commentVO.getAvatarUrl())
            .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mAvatarView);
        mUserNameView.setText(commentVO.getUserName());
        mPublishTimeView.setText(commentVO.getPublishTime());
        mRatingBar.setRating(commentVO.getScorePoint() * 1.0F / 2);
        mContentView.setText(commentVO.getDesc());
    }

}
