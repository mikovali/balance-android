package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import io.github.mikovali.android.mvp.ViewSavedState;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionListView;
import io.github.mikovali.balance.android.domain.model.Transaction;

public class TransactionListAndroidView extends RecyclerView implements TransactionListView {

    private final TransactionListPresenter presenter;

    private final TransactionAdapter adapter;

    public TransactionListAndroidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        presenter = new TransactionListPresenter(this);
        adapter = new TransactionAdapter();
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        adapter.setEmptyState(TransactionAdapter.EMPTY_STATE_EMPTY);
        adapter.setTransactions(transactions);
        adapter.notifyDataSetChanged();
    }

    // lifecycle

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setHasFixedSize(true);
        setLayoutManager(new EmptyViewLinearLayoutManager(getContext()));
        setAdapter(adapter);
        addItemDecoration(new CardViewBottomMarginDecorator());
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
