package com.oliver.seenews.magazine;

import java.util.Locale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.magazine.domain.MagazineItem;

public class MagazineViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "MagazineViewHolder";

    private Context mContext;
    private AppCompatImageView mImageView;
    private AppCompatTextView mPublishDate;
    private AppCompatTextView mPublishVol;

    public static MagazineViewHolder create(@NonNull Context context, ViewGroup parent) {
        return new MagazineViewHolder(context,
            LayoutInflater.from(context).inflate(R.layout.magazine_card_layout, parent, false));
    }

    private MagazineViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mImageView = itemView.findViewById(R.id.magazine_card_image);
        mPublishDate = itemView.findViewById(R.id.magazine_card_publish_date);
        mPublishVol = itemView.findViewById(R.id.magazine_card_publish_vol);
    }

    public void bindData(final MagazineItem magazineItem) {
        LogUtils.debug(TAG, "bindData, url = " + magazineItem.getCoverImgUrl());
        Glide.with(mContext).load(magazineItem.getCoverImgUrl()).into(mImageView);
        mPublishDate.setText(magazineItem.getPublishTime());
        mPublishVol.setText(String.format(Locale.CHINA,
            mContext.getResources().getString(R.string.magazine_vol_format), magazineItem.getVol()));
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                magazineItem.onClick();
            }
        });
    }


}
