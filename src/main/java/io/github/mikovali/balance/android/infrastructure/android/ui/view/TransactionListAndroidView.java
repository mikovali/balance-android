package io.github.mikovali.balance.android.infrastructure.android.ui.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import javax.inject.Inject;

import io.github.mikovali.android.mvp.ViewSavedState;
import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionListView;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.App;
import io.github.mikovali.balance.android.infrastructure.android.ui.CardViewBottomMarginDecorator;
import io.github.mikovali.balance.android.infrastructure.android.ui.EmptyViewLinearLayoutManager;

public class TransactionListAndroidView extends RecyclerView implements TransactionListView {

    @Inject NavigationService navigationService;
    @Inject ObservableRegistry observableRegistry;
    @Inject TransactionRepository transactionRepository;

    private final TransactionListPresenter presenter;

    private final TransactionAdapter adapter;

    public TransactionListAndroidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        App.getAppComponent(context).inject(this);
        presenter = new TransactionListPresenter(this, navigationService, observableRegistry,
                transactionRepository);
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
