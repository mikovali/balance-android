package io.github.mikovali.balance.android.application.transaction;

import java.util.UUID;

import io.github.mikovali.android.mvp.BasePresenter;
import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.DeviceService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.application.WindowService;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class TransactionUpdatePresenter extends BasePresenter<TransactionUpdateView>
        implements Action1<Transaction>, DeviceService.OnBackButtonClickListener {

    private static final int OBSERVABLE_CREATE = 0;

    private final NavigationService navigationService;

    private final ObservableRegistry observableRegistry;

    private final DeviceService deviceService;

    private final WindowService windowService;

    private final TransactionRepository transactionRepository;

    private Subscription createSubscription;

    private final int screenId;
    private final int viewId;

    public TransactionUpdatePresenter(TransactionUpdateView view,
                                      NavigationService navigationService,
                                      ObservableRegistry observableRegistry,
                                      DeviceService deviceService,
                                      WindowService windowService,
                                      TransactionRepository transactionRepository) {
        super(view);
        this.navigationService = navigationService;
        this.observableRegistry = observableRegistry;
        this.deviceService = deviceService;
        this.windowService = windowService;
        this.transactionRepository = transactionRepository;

        screenId = navigationService.getCurrent().getId();
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
                .publish().refCount().cacheWithInitialCapacity(1);
        observableRegistry.set(screenId, viewId, OBSERVABLE_CREATE, createObservable);
        createSubscription = createObservable.subscribe(this);
    }

    @Override
    public void call(Transaction transaction) {
        createSubscription.unsubscribe();
        createSubscription = null;
        observableRegistry.remove(screenId, viewId, OBSERVABLE_CREATE);

        navigationService.goBack();
    }

    @Override
    public boolean onBackButtonClick() {
        windowService.confirm(R.string.transaction_update_discard_message);
        return true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        deviceService.addOnBackButtonClickListener(this);

        if (observableRegistry.has(screenId, viewId, OBSERVABLE_CREATE)) {
            createSubscription = observableRegistry
                    .<Transaction>get(screenId, viewId, OBSERVABLE_CREATE)
                    .subscribe(this);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (createSubscription != null && !createSubscription.isUnsubscribed()) {
            createSubscription.unsubscribe();
            createSubscription = null;
        }
        deviceService.removeOnBackButtonClickListener(this);
        super.onDetachedFromWindow();
    }
}
