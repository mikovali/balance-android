package io.github.mikovali.balance.android.application.transaction;

import io.github.mikovali.balance.android.domain.model.TransactionRepository;

public class TransactionListPresenter {

    private final TransactionRepository transactionRepository;

    public TransactionListPresenter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
