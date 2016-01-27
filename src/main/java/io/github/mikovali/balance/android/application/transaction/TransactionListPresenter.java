package io.github.mikovali.balance.android.application.transaction;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import io.github.mikovali.android.mvp.BasePresenter;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.App;
import io.github.mikovali.balance.android.infrastructure.flow.Screen;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import static io.github.mikovali.android.utils.BundleUtils.getParcelableList;
import static io.github.mikovali.android.utils.BundleUtils.putParcelableList;
import static io.github.mikovali.balance.android.application.Constants.KEY_TRANSACTION_LIST;

public class TransactionListPresenter extends BasePresenter<TransactionListView>
        implements Action1<List<Transaction>> {

    private static final int OBSERVABLE_FIND = 0;

    @Inject
    ObservableRegistry observableRegistry;

    @Inject
    TransactionRepository transactionRepository;

    private List<Transaction> transactions;

    private Subscription findSubscription;

    private final int screenId;
    private final int viewId;

    public TransactionListPresenter(TransactionListView view) {
        super(view);
        App.getAppComponent(view.getContext()).inject(this);
        final Flow flow = Flow.get(view.getContext());

        screenId = flow.getHistory().<Screen>top().getId();
        viewId = view.getId();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (transactions == null) {
            Observable<List<Transaction>> findObservable = observableRegistry
                    .get(screenId, viewId, OBSERVABLE_FIND);
            if (findObservable == null) {
                findObservable = transactionRepository.find().publish().refCount().cache(1);
                observableRegistry.set(screenId, viewId, OBSERVABLE_FIND, findObservable);
            }
            findSubscription = findObservable.subscribe(this);
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
    public void call(List<Transaction> transactions) {
        if (findSubscription != null && !findSubscription.isUnsubscribed()) {
            findSubscription.unsubscribe();
            findSubscription = null;
        }
        observableRegistry.remove(screenId, viewId, OBSERVABLE_FIND);

        setTransactions(transactions);
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

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        view.setTransactions(transactions);
    }
}
