package io.github.mikovali.balance.android.application.transaction;

import java.util.List;

import io.github.mikovali.android.mvp.View;
import io.github.mikovali.balance.android.domain.model.Transaction;

public interface TransactionListView extends View {

    void setTransactions(List<Transaction> transactions);
}
