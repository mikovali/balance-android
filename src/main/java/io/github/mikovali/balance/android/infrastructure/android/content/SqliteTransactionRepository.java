package io.github.mikovali.balance.android.infrastructure.android.content;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.mapper.TransactionMapper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_AMOUNT;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_ID;

public class SqliteTransactionRepository implements TransactionRepository {

    private final SQLiteOpenHelper appDatabaseOpenHelper;

    public SqliteTransactionRepository(SQLiteOpenHelper appDatabaseOpenHelper) {
        this.appDatabaseOpenHelper = appDatabaseOpenHelper;
    }

    @Override
    public Observable<List<Transaction>> find() {
        return Observable
                .defer(new Func0<Observable<List<Transaction>>>() {
                    @Override
                    public Observable<List<Transaction>> call() {
                        return Observable.just(doFind());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Transaction> insert(Transaction transaction) {
        return null;
    }

    private List<Transaction> doFind() {
        SystemClock.sleep(3000);

        final List<Transaction> transactions = new ArrayList<>();

        final Cursor cursor = appDatabaseOpenHelper.getReadableDatabase()
                .rawQuery(String.format("SELECT %s, %s FROM %s",
                        DB_APP_TRANSACTIONS_ID, DB_APP_TRANSACTIONS_AMOUNT,
                        DB_APP_TRANSACTIONS), null);
        if (cursor.moveToFirst()) {
            do {
                transactions.add(TransactionMapper.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return transactions;
    }
}
