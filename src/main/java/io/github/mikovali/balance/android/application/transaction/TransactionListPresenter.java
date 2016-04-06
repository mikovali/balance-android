package io.github.mikovali.balance.android.application.transaction;

import android.os.Bundle;

import java.util.List;

import io.github.mikovali.android.mvp.BasePresenter;
import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;

import static io.github.mikovali.android.utils.BundleUtils.getParcelableList;
import static io.github.mikovali.android.utils.BundleUtils.putParcelableList;
import static io.github.mikovali.balance.android.application.Constants.KEY_TRANSACTION_LIST;

public class TransactionListPresenter extends BasePresenter<TransactionListView> {

    private static final int OBSERVABLE_FIND = 0;

    private final TransactionRepository transactionRepository;

    private final Subscriber<List<Transaction>> findSubscriber
            = new Subscriber<List<Transaction>>(OBSERVABLE_FIND) {
        @Override
        public void onNext(List<Transaction> transactions) {
            super.onNext(transactions);
            setTransactions(transactions);
        }
    };

    private List<Transaction> transactions;

    public TransactionListPresenter(TransactionListView view, NavigationService navigationService,
                                    ObservableRegistry observableRegistry,
                                    TransactionRepository transactionRepository) {
        super(view, navigationService, observableRegistry);
        this.transactionRepository = transactionRepository;

        putSubscriber(OBSERVABLE_FIND, findSubscriber);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (transactions == null) {
            subscribe(transactionRepository.find(), OBSERVABLE_FIND);
        } else {
            setTransactions(transactions);
        }
    }

    @Override
    public void onSaveState(Bundle state) {
        super.onSaveState(state);
        putParcelableList(KEY_TRANSACTION_LIST, transactions, Transaction.CREATOR, state);
    }

    @Override
    public void onRestoreState(Bundle state) {
        super.onRestoreState(state);
        transactions = getParcelableList(KEY_TRANSACTION_LIST, state);
    }

    private void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        view.setTransactions(transactions);
    }
}
