package io.github.mikovali.balance.android.application.transaction;

import java.util.UUID;

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

public class TransactionUpdatePresenter extends BasePresenter<TransactionUpdateView>
        implements Action1<Transaction> {

    private static final int OBSERVABLE_CREATE = 0;

    @Inject
    ObservableRegistry observableRegistry;

    @Inject
    TransactionRepository transactionRepository;

    private final Flow flow;

    private Subscription createSubscription;

    private final int screenId;
    private final int viewId;

    public TransactionUpdatePresenter(TransactionUpdateView view) {
        super(view);
        App.getAppComponent(view.getContext()).inject(this);
        flow = Flow.get(view.getContext());

        screenId = flow.getHistory().<Screen>top().getId();
        viewId = view.getId();
    }

    public void onDoneButtonClick() {
        final long amount = view.getAmount();

        // validation
        if (amount == 0) {
            view.setAmountInvalid(true);
            return;
        }

        final Transaction transaction = new Transaction(UUID.randomUUID(), amount);

        final Observable<Transaction> createObservable = transactionRepository.insert(transaction)
                .publish().refCount().cache(1);
        observableRegistry.set(screenId, viewId, OBSERVABLE_CREATE, createObservable);
        createSubscription = createObservable.subscribe(this);
    }

    @Override
    public void call(Transaction transaction) {
        createSubscription.unsubscribe();
        createSubscription = null;
        observableRegistry.remove(screenId, viewId, OBSERVABLE_CREATE);

        flow.goBack();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (observableRegistry.has(screenId, viewId, OBSERVABLE_CREATE)) {
            createSubscription = observableRegistry
                    .<Transaction>get(screenId, viewId, OBSERVABLE_CREATE)
                    .subscribe(this);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (createSubscription != null && !createSubscription.isUnsubscribed()) {
            createSubscription.unsubscribe();
            createSubscription = null;
        }
    }
}
