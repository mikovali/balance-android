package io.github.mikovali.balance.android.infrastructure.android.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.SystemClock;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.UUID;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
        Timber.e("CREATE FIND OBSERVABLE IN REPOSITORY");
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
                        Timber.e("START INSET TRANSACTION");
                        SystemClock.sleep(3000);

                        final ContentValues values = new ContentValues();
                        values.put(DB_APP_TRANSACTIONS_ID, transaction.getId().toString());
                        values.put(DB_APP_TRANSACTIONS_AMOUNT, transaction.getAmount());

                        appDatabase.insert(DB_APP_TRANSACTIONS, values);

                        Timber.e("END INSET TRANSACTION");

                        return transaction;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
