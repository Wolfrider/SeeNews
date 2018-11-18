package com.oliver.seenews.magazine.read.nav;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;

public class MagazineNavDialog extends AppCompatDialogFragment {

    private static final String TAG = "MagazineNavDialog";

    public interface OnItemSelectedListener {

        void onSelected(int index);
    }

    private static final String PARAM_ITEMS = "items";
    private static final String PARAM_CURRENT = "current";

    private OnItemSelectedListener mSelectedListener;
    private ArrayList<String> mItems;
    private int mCurrent;

    public static MagazineNavDialog create(@NonNull ArrayList<String> items, int current) {
        MagazineNavDialog dialog = new MagazineNavDialog();
        Bundle args = new Bundle();
        args.putStringArrayList(PARAM_ITEMS, items);
        args.putInt(PARAM_CURRENT, current);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null != args) {
            mItems = args.getStringArrayList(PARAM_ITEMS);
            mCurrent = args.getInt(PARAM_CURRENT);
            LogUtils.debug(TAG, String.format(Locale.US, "mCurrent = %d, mItems size = %d", mCurrent, mItems.size()));
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        if (null != window) {
            window.setGravity(Gravity.END);
        }
        return inflater.inflate(R.layout.magazine_nav_dialog, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(getView());
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mSelectedListener = listener;
    }

    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.magazine_nav_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MagazineNavAdapter adapter = new MagazineNavAdapter(getContext(), mItems, mCurrent,
            new MagazineNavAdapter.OnItemSelectedListener() {
                @Override
                public void onSelected(int index) {
                    if (null != mSelectedListener) {
                        mSelectedListener.onSelected(index);
                    }
                }
            });
        recyclerView.setAdapter(adapter);
        final int left = getResources().getDimensionPixelSize(R.dimen.magazine_nav_recycler_view_padding_left);
        final int top = getResources().getDimensionPixelSize(R.dimen.magazine_nav_recycler_view_padding_top);
        final int right = getResources().getDimensionPixelSize(R.dimen.magazine_nav_recycler_view_padding_right);
        final int bottom = getResources().getDimensionPixelSize(R.dimen.magazine_nav_recycler_view_padding_bottom);
        recyclerView.addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                outRect.left = left;
                outRect.top = top;
                outRect.right = right;
                outRect.bottom = bottom;
            }
        });
        recyclerView.scrollToPosition(mCurrent);
    }

}
