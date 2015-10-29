package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EmptyViewLinearLayoutManager extends LinearLayoutManager
        implements EmptyViewLayoutManager {

    private boolean isEmptyView = false;

    public EmptyViewLinearLayoutManager(Context context) {
        super(context);
    }

    public EmptyViewLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public EmptyViewLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setIsEmptyView(boolean isEmptyView) {
        this.isEmptyView = isEmptyView;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (!isEmptyView) {
            return super.generateDefaultLayoutParams();
        } else {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!isEmptyView) {
            super.onLayoutChildren(recycler, state);
        } else if (state.didStructureChange()) {
            final View view = recycler.getViewForPosition(0);
            removeAllViews();
            addView(view);
            measureChildWithMargins(view, 0, 0);
            layoutDecorated(view, 0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!isEmptyView) {
            return super.scrollHorizontallyBy(dx, recycler, state);
        } else {
            return 0;
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
                                  RecyclerView.State state) {
        if (!isEmptyView) {
            return super.scrollVerticallyBy(dy, recycler, state);
        } else {
            return 0;
        }
    }
}
