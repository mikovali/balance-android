package io.github.mikovali.balance.android.application.transaction;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import io.github.mikovali.android.mvp.BasePresenter;
import io.github.mikovali.android.utils.BundleUtils;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.App;
import rx.Subscription;
import rx.functions.Action1;

import static io.github.mikovali.balance.android.application.Constants.KEY_TRANSACTION_LIST;

public class TransactionListPresenter extends BasePresenter<TransactionListView> {

    @Inject
    TransactionRepository transactionRepository;

    private final Action1<List<Transaction>> findAction = new Action1<List<Transaction>>() {
        @Override
        public void call(List<Transaction> transactions) {
            setTransactions(transactions);
        }
    };

    private Subscription findSubscription;

    private List<Transaction> transactions;

    public TransactionListPresenter(TransactionListView view) {
        super(view);
        App.getAppComponent(view.getContext()).inject(this);
    }

    private void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        view.setTransactions(transactions);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (transactions == null) {
            findSubscription = transactionRepository.find().subscribe(findAction);
        } else {
            setTransactions(transactions);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (findSubscription != null && !findSubscription.isUnsubscribed()) {
            findSubscription.unsubscribe();
            findSubscription = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void onSaveState(Bundle state) {
        super.onSaveState(state);
        BundleUtils.putParcelableList(KEY_TRANSACTION_LIST, transactions, Transaction.CREATOR,
                state);
    }

    @Override
    public void onRestoreState(Bundle state) {
        super.onRestoreState(state);
        transactions = BundleUtils.getParcelableList(KEY_TRANSACTION_LIST, state);
    }
}
