package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ScrollView;

import io.github.mikovali.android.mvp.ViewSavedState;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.transaction.TransactionUpdatePresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionUpdateView;

public class TransactionUpdateAndroidView extends ScrollView implements TransactionUpdateView {

    private final TransactionUpdatePresenter presenter;

    public TransactionUpdateAndroidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        presenter = new TransactionUpdatePresenter(this);
        setFillViewport(true);
        inflate(context, R.layout.transaction_update, this);
    }

    // lifecycle

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return ViewSavedState.onSaveInstanceState(super.onSaveInstanceState(), presenter);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(ViewSavedState.onRestoreInstanceState(state, presenter));
    }
}
