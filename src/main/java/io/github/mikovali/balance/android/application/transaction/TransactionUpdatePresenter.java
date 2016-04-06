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

public class TransactionUpdatePresenter extends BasePresenter<TransactionUpdateView>
        implements DeviceService.OnBackButtonClickListener {

    private static final int OBSERVABLE_CREATE = 0;
    private static final int OBSERVABLE_DISCARD = 1;

    private final DeviceService deviceService;

    private final WindowService windowService;

    private final TransactionRepository transactionRepository;

    private final Subscriber<Transaction> createSubscriber
            = new Subscriber<Transaction>(OBSERVABLE_CREATE) {
        @Override
        public void onNext(Transaction transaction) {
            super.onNext(transaction);
            windowService.hide();
            navigationService.goBack();
        }
    };

    private final Subscriber<Boolean> discardSubscriber
            = new Subscriber<Boolean>(OBSERVABLE_DISCARD) {
        @Override
        public void onNext(Boolean positiveButtonClick) {
            super.onNext(positiveButtonClick);
            if (positiveButtonClick != null && positiveButtonClick) {
                navigationService.goBack();
            }
        }
    };

    public TransactionUpdatePresenter(TransactionUpdateView view,
                                      NavigationService navigationService,
                                      ObservableRegistry observableRegistry,
                                      DeviceService deviceService,
                                      WindowService windowService,
                                      TransactionRepository transactionRepository) {
        super(view, navigationService, observableRegistry);
        this.deviceService = deviceService;
        this.windowService = windowService;
        this.transactionRepository = transactionRepository;

        putSubscriber(OBSERVABLE_CREATE, createSubscriber);
        putSubscriber(OBSERVABLE_DISCARD, discardSubscriber);
    }

    public void onDoneButtonClick() {
        final long amount = view.getAmount();

        // validation
        if (amount == 0) {
            view.setAmountInvalid(true);
            return;
        }

        windowService.progress();

        final Transaction transaction = new Transaction(UUID.randomUUID(), amount);
        subscribe(transactionRepository.insert(transaction), OBSERVABLE_CREATE);
    }

    @Override
    public boolean onBackButtonClick() {
        subscribe(windowService.confirm(R.string.transaction_update_discard_message),
                OBSERVABLE_DISCARD);
        return true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        deviceService.addOnBackButtonClickListener(this);
    }

    @Override
    public void onDetachedFromWindow() {
        deviceService.removeOnBackButtonClickListener(this);
        super.onDetachedFromWindow();
    }
}
