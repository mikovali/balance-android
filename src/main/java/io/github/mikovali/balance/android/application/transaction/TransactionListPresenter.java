package io.github.mikovali.balance.android.application.transaction;

import java.util.List;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class TransactionListPresenter {

    private final TransactionRepository transactionRepository;

    private TransactionListView view;

    private Subscription findSubscription;

    public TransactionListPresenter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        Timber.e("CONSTRUCTOR: %s", transactionRepository);
    }

    public void attachView(final TransactionListView view) {
        Timber.e("attachView: %s", view);
        this.view = view;

        view.setInProgress(true);

        findSubscription = transactionRepository.find()
                .subscribe(new Action1<List<Transaction>>() {
                    @Override
                    public void call(List<Transaction> transactions) {
                        Timber.e("TRANSACTIONS: %s", transactions);
                        view.setInProgress(false);
                        view.setTransactions(transactions);
                    }
                });
    }

    public void detachView() {
        Timber.e("detachView: %s", view);
        if (findSubscription != null && !findSubscription.isUnsubscribed()) {
            findSubscription.unsubscribe();
        }
        view = null;
    }
}
