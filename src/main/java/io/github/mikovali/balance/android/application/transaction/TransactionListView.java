package io.github.mikovali.balance.android.application.transaction;

import java.util.List;

import io.github.mikovali.balance.android.domain.model.Transaction;

public interface TransactionListView {

    void setInProgress(boolean inProgress);

    void setTransactions(List<Transaction> transactions);
}
