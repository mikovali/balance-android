package io.github.mikovali.balance.android.infrastructure.android.content;

import android.content.ContentValues;
import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.mapper.TransactionMapper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_AMOUNT;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_ID;

public class SqliteTransactionRepository implements TransactionRepository {

    private final BriteDatabase appDatabase;

    public SqliteTransactionRepository(BriteDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Observable<List<Transaction>> find() {
        return appDatabase
                .createQuery(DB_APP_TRANSACTIONS, String.format("SELECT %s, %s FROM %s",
                        DB_APP_TRANSACTIONS_ID, DB_APP_TRANSACTIONS_AMOUNT, DB_APP_TRANSACTIONS))
                .mapToList(new Func1<Cursor, Transaction>() {
                    @Override
                    public Transaction call(Cursor cursor) {
                        return TransactionMapper.fromCursor(cursor);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Transaction> insert(Transaction transaction) {
        return Observable.just(transaction)
                .map(new Func1<Transaction, Transaction>() {
                    @Override
                    public Transaction call(Transaction transaction) {
                        final ContentValues values = TransactionMapper.toContentValues(transaction);
                        appDatabase.insert(DB_APP_TRANSACTIONS, values);
                        return transaction;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
