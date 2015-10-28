package io.github.mikovali.balance.android.infrastructure.android.content;

import android.os.SystemClock;

import java.util.Arrays;
import java.util.List;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SqliteTransactionRepository implements TransactionRepository {

    private static final Transaction[] TRANSACTIONS = new Transaction[] {
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100))),
            new Transaction(((long) (Math.random() * 100)))
    };

    private final ConnectableObservable<List<Transaction>> findObservable = Observable
            .defer(new Func0<Observable<List<Transaction>>>() {
                @Override
                public Observable<List<Transaction>> call() {
                    return Observable.just(doFind());
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .replay(1);

    public SqliteTransactionRepository() {
        findObservable.connect();
    }

    @Override
    public Observable<List<Transaction>> find() {
        return findObservable;
    }

    private List<Transaction> doFind() {
        Timber.e("START API CALL");
        SystemClock.sleep(5000);
        Timber.e("END API CALL");
        return Arrays.asList(TRANSACTIONS);
    }
}
