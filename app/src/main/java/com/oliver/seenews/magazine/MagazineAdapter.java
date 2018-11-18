package com.oliver.seenews.magazine;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.magazine.domain.MagazineItem;

public class MagazineAdapter extends RecyclerView.Adapter<MagazineViewHolder> {

    private static final String TAG = "MagazineAdapter";

    private Context mContext;
    private List<MagazineItem> mMagazineItems;

    public MagazineAdapter(@NonNull Context context) {
        mContext = context;
        mMagazineItems = new LinkedList<>();
    }

    @NonNull
    @Override
    public MagazineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtils.debug(TAG, "onCreateViewHolder");
        return MagazineViewHolder.create(mContext, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MagazineViewHolder holder, int position) {
        LogUtils.debug(TAG, "onCreateViewHolder, " + mMagazineItems.get(position));
        holder.bindData(mMagazineItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mMagazineItems.size();
    }

    public void add(List<MagazineItem> items) {
        int start = mMagazineItems.size();
        mMagazineItems.addAll(items);
        notifyItemRangeInserted(start, items.size());
    }
}
