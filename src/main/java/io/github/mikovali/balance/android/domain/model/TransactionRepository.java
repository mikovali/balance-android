package io.github.mikovali.balance.android.domain.model;

import java.util.List;

import io.github.mikovali.balance.android.domain.DomainException;
import rx.Observable;

public interface TransactionRepository {

    Observable<List<Transaction>> find();
}
