package com.oliver.seenews.magazine.read.nav;

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

public class MagazineNavCardViewHolder extends RecyclerView.ViewHolder {

    private AppCompatImageView mImageView;
    private AppCompatTextView mPositionView;
    private MagazineNavAdapter.OnItemSelectedListener mSelectedListener;

    public static MagazineNavCardViewHolder create(@NonNull Context context, @NonNull ViewGroup parent,
                                                   @NonNull MagazineNavAdapter.OnItemSelectedListener listener) {
        return new MagazineNavCardViewHolder(LayoutInflater.from(context).inflate(
            R.layout.magazine_nav_card_layout, parent, false), listener);
    }

    public MagazineNavCardViewHolder(View itemView, @NonNull MagazineNavAdapter.OnItemSelectedListener listener) {
        super(itemView);
        mSelectedListener = listener;
        mImageView = itemView.findViewById(R.id.magazine_nav_card_image);
        mPositionView = itemView.findViewById(R.id.magazine_nav_card_position);
    }

    public void bindData(@NonNull String imagePath, int current, int size) {
        Glide.with(mImageView).load(imagePath).into(mImageView);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setBackgroundResource(R.drawable.magazine_nav_selected);
                mSelectedListener.onSelected(getAdapterPosition());
            }
        });
        mPositionView.setText(formatPosition(size));
        if (current == getAdapterPosition()) {
            mImageView.setBackgroundResource(R.drawable.magazine_nav_selected);
        } else {
            mImageView.setBackground(null);
        }
    }

    private String formatPosition(int size) {
        int position = getAdapterPosition() + 1;
        return String.format(Locale.US, "%d/%d", position, size);
    }

}
