package io.github.mikovali.balance.android.application.transaction;

import java.util.List;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import rx.Subscription;
import rx.functions.Action1;

public class TransactionListPresenter {

    private final TransactionRepository transactionRepository;

    private TransactionListView view;

    private Subscription findSubscription;

    public TransactionListPresenter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void attachView(final TransactionListView view) {
        this.view = view;

        findSubscription = transactionRepository.find()
                .subscribe(new Action1<List<Transaction>>() {
                    @Override
                    public void call(List<Transaction> transactions) {
                        view.setTransactions(transactions);
                    }
                });
    }

    public void detachView() {
        if (findSubscription != null && !findSubscription.isUnsubscribed()) {
            findSubscription.unsubscribe();
            findSubscription = null;
        }
        view = null;
    }
}
