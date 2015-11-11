package io.github.mikovali.balance.android.domain.model;

import java.util.List;

import rx.Observable;

public interface TransactionRepository {

    Observable<List<Transaction>> find();

    Observable<Transaction> insert(Transaction transaction);
}
