package io.github.mikovali.balance.android.infrastructure.android.content;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.UUID;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_AMOUNT;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_ID;

public class SqliteTransactionRepository implements TransactionRepository {

    private final BriteDatabase appDatabase;

    private final ConnectableObservable<List<Transaction>> findObservable = Observable
            .defer(new Func0<Observable<List<Transaction>>>() {
                @Override
                public Observable<List<Transaction>> call() {
                    return doFind();
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .replay(1);

    public SqliteTransactionRepository(BriteDatabase appDatabase) {
        this.appDatabase = appDatabase;
        findObservable.connect();
    }

    @Override
    public Observable<List<Transaction>> find() {
        return findObservable;
    }

    private Observable<List<Transaction>> doFind() {
        return appDatabase
                .createQuery(DB_APP_TRANSACTIONS, String.format("SELECT %s, %s FROM %s",
                        DB_APP_TRANSACTIONS_ID, DB_APP_TRANSACTIONS_AMOUNT, DB_APP_TRANSACTIONS))
                .mapToList(new Func1<Cursor, Transaction>() {
                    @Override
                    public Transaction call(Cursor cursor) {
                        final UUID id = UUID.fromString(cursor.getString(
                                cursor.getColumnIndex(DB_APP_TRANSACTIONS_ID)));
                        final long amount = cursor.getLong(
                                cursor.getColumnIndex(DB_APP_TRANSACTIONS_AMOUNT));

                        return new Transaction(id, amount);
                    }
                });
    }
}
