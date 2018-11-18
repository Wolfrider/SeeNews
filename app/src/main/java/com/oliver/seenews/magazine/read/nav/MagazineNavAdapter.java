package com.oliver.seenews.magazine.read.nav;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class MagazineNavAdapter extends RecyclerView.Adapter<MagazineNavCardViewHolder> {

    public interface OnItemSelectedListener {
        void onSelected(int index);
    }

    private Context mContext;
    private ArrayList<String> mItems;
    private int mCurrent;

    private OnItemSelectedListener mSelectedListener;

    public MagazineNavAdapter(@NonNull Context context, @NonNull ArrayList<String> items, int current,
                              @NonNull OnItemSelectedListener listener) {
        mContext = context;
        mItems = items;
        mCurrent = current;
        mSelectedListener = listener;
    }

    @NonNull
    @Override
    public MagazineNavCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MagazineNavCardViewHolder.create(mContext, parent, new OnItemSelectedListener() {
            @Override
            public void onSelected(int index) {
                mCurrent = index;
                mSelectedListener.onSelected(index);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MagazineNavCardViewHolder holder, int position) {
        holder.bindData(mItems.get(position), mCurrent, mItems.size());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
