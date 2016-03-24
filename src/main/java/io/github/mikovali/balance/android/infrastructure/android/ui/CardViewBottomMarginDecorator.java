package io.github.mikovali.balance.android.infrastructure.android.ui;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.mikovali.balance.android.R;

public class CardViewBottomMarginDecorator extends RecyclerView.ItemDecoration {

    private int cardViewBottomMargin = -1;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        final int count = parent.getAdapter().getItemCount();
        final int position = parent.getChildAdapterPosition(view);
        if (position + 1 == count) {
            if (cardViewBottomMargin == -1) {
                final TypedArray a = parent.getContext().obtainStyledAttributes(new int[] {
                        R.attr.cardViewBottomMargin});
                cardViewBottomMargin = a.getDimensionPixelSize(0, 0);
                a.recycle();
            }
            outRect.set(0, 0, 0, cardViewBottomMargin);
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
