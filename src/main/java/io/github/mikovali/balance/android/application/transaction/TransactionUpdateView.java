package io.github.mikovali.balance.android.application.transaction;

import io.github.mikovali.android.mvp.View;

public interface TransactionUpdateView extends View {

    long getAmount();

    void setAmountInvalid(boolean amountInvalid);
}
