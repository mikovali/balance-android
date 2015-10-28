package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import javax.inject.Inject;

import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionListView;
import io.github.mikovali.balance.android.infrastructure.android.App;

public class AndroidTransactionListView extends FrameLayout implements TransactionListView {

    @Inject
    TransactionListPresenter presenter;

    public AndroidTransactionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        App.getAppComponent(context).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("AAA", "presenter: " + presenter);
    }
}
