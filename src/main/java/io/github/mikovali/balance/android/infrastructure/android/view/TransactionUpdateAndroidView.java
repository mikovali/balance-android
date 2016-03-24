package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.android.mvp.ViewSavedState;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.NavigationService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.application.WindowService;
import io.github.mikovali.balance.android.application.transaction.TransactionUpdatePresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionUpdateView;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.App;
import timber.log.Timber;

public class TransactionUpdateAndroidView extends ScrollView implements TransactionUpdateView {

    @Inject ObservableRegistry observableRegistry;
    @Inject NavigationService navigationService;
    @Inject WindowService windowService;
    @Inject TransactionRepository transactionRepository;

    @Bind(R.id.transactionUpdateAmountSign)
    RadioGroup amountSignView;

    @Bind(R.id.transactionUpdateAmountLabel)
    TextInputLayout amountLabelView;

    @Bind(R.id.transactionUpdateAmount)
    TextView amountView;

    private final TransactionUpdatePresenter presenter;

    public TransactionUpdateAndroidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        App.getAppComponent(context).inject(this);

        presenter = new TransactionUpdatePresenter(this, observableRegistry, navigationService,
                windowService,transactionRepository);

        inflate(context, R.layout.transaction_update, this);
        ButterKnife.bind(this);

        setFillViewport(true);
    }

    @Override
    public long getAmount() {
        final CharSequence amountText = amountView.getText();
        if (TextUtils.isEmpty(amountText) || !TextUtils.isDigitsOnly(amountText)) {
            return 0;
        }
        final int sign = amountSignView.getCheckedRadioButtonId();

        final StringBuilder builder = new StringBuilder();
        if (sign == R.id.transactionUpdateAmountSignNegative) {
            builder.append("-");
        }
        builder.append(amountText);

        try {
            return Long.parseLong(builder.toString());
        } catch (NumberFormatException e) {
            Timber.e(e, "Transaction amount cannot be parsed to long");
            return 0;
        }
    }

    @Override
    public void setAmountInvalid(boolean amountInvalid) {
        amountLabelView.setError(getResources().getString(
                R.string.transaction_update_amount_validation_invalid));
    }

    public void onDoneButtonClick() {
        presenter.onDoneButtonClick();
    }

    // lifecycle

    @Override
    protected Parcelable onSaveInstanceState() {
        return ViewSavedState.onSaveInstanceState(super.onSaveInstanceState(), presenter);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(ViewSavedState.onRestoreInstanceState(state, presenter));
    }
}
