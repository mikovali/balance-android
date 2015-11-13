package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import io.github.mikovali.balance.android.R;

public class TransactionUpdateView extends ScrollView {

    public TransactionUpdateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFillViewport(true);
        inflate(context, R.layout.transaction_update, this);
    }
}
