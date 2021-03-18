package com.example.voicetubehomework.util;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicetubehomework.MainApplication;
import com.example.voicetubehomework.R;

import java.util.Locale;

public abstract class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "LoadMoreWrapper";

    //普通佈局
//    private final int TYPE_ITEM = 1;
    //腳佈局
//    private final int TYPE_FOOTER = 2;
    private final int TYPE_FOOTER = -1;
    //目前加載狀態, 默認為加載完成
    private int loadState = 2;
    //正在加載
    public final int LOADING = 1;
    //加載完成
    public final int LOADING_COMPLETE = 2;
    //加載到底
    public final int LOADING_END = 3;

    @Override
    public int getItemViewType(int position) {
//        Log.d(TAG, "getItemViewType: getItemCount()" + getItemCount());
//        Log.d(TAG, "getItemViewType: position: " + position);
        //最後一個item設值為FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
//            return TYPE_ITEM;
            return position;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //進行判斷顯示類型, 來創建返回不同的View
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer, parent, false);
            return new FootViewHolder(view);
        } else {
            return _onCreateViewHolder(parent, viewType);
        }
    }


    public abstract RecyclerView.ViewHolder _onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
//            Log.d(TAG, "onBindViewHolder: loadState: " + loadState);
            String loading = MainApplication.getInstance().getString(R.string.loading);
            String load_end = MainApplication.getInstance().getString(R.string.load_end);
            switch (loadState) {
                case LOADING:
                    footViewHolder.textView_footer_loading.setText(loading);
                    footViewHolder.textView_footer_loading.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.textView_footer_loading.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    footViewHolder.textView_footer_loading.setText(load_end);
                    footViewHolder.textView_footer_loading.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        } else {
            _onBindViewHolder(holder, position);
        }
    }

    public abstract void _onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        int size = _getItemCount();
        Log.d(TAG, "getItemCount: size: " + size);
        if (size > 0) {
            return size + 1;
        } else {
            return 0;
        }
    }

    public abstract int _getItemCount();

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //如果目前是footer的位置, 那麼該item佔據兩個單位格, 正常情況下佔據一個單位格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        TextView textView_footer_loading;


        @SuppressLint({"WrongViewCast", "CutPasteId"})
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_footer_loading = itemView.findViewById(R.id.textView_footer_loading);
        }
    }

    /**
     * 設置上拉加載狀態
     *
     * @param loadState 0.正在加載 1.加載完成 2.加載到底
     */
    public void setLoadState(int loadState) {
        Log.d(TAG, "setLoadState: current load state: " + this.loadState);
        Log.d(TAG, "setLoadState: new load state: " + loadState);
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public int getLoadState() {
        return loadState;
    }
}
