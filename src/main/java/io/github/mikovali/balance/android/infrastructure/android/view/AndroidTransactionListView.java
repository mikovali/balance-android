package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import javax.inject.Inject;

import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionListView;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.infrastructure.android.App;

public class AndroidTransactionListView extends RecyclerView implements TransactionListView {

    @Inject
    TransactionListPresenter presenter;

    private final TransactionAdapter adapter;

    public AndroidTransactionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            App.getAppComponent(context).inject(this);
        }

        adapter = new TransactionAdapter();

        setHasFixedSize(true);
        setLayoutManager(new EmptyViewLinearLayoutManager(context));
        setAdapter(adapter);
        addItemDecoration(new CardViewBottomMarginDecorator());
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        adapter.setEmptyState(TransactionAdapter.EMPTY_STATE_EMPTY);
        adapter.setTransactions(transactions);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.detachView();
        super.onDetachedFromWindow();
    }
}
