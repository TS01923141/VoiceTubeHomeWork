package com.example.voicetubehomework.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //當不滑動時
        if (newState == RecyclerView.SCROLL_STATE_IDLE){
            //獲取最後一個完全顯示的itemPosition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            //判斷是否滑動到了最後一個item, 並且是向上滑動
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward){
                //加載更多
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //大於0表示正在向上滑動, 小於等於0表示停止或向下滑動
        isSlidingUpward = dy > 0;
    }

    public abstract void onLoadMore();
}
