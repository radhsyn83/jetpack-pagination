package com.radhsyn83.jetpackpaggingexample;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private final int size;

    public PaddingItemDecoration(int size) {
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Apply offset only to first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left += size;
        }

        Log.d("SCROLL", parent.getChildAdapterPosition(view)+"");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            parent.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            });
        }
    }
}
