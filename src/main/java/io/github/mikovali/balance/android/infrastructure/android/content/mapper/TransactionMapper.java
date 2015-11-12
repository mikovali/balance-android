package io.github.mikovali.balance.android.infrastructure.android.content.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import io.github.mikovali.balance.android.domain.model.Transaction;

import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_AMOUNT;
import static io.github.mikovali.balance.android.application.Constants.DB_APP_TRANSACTIONS_ID;

public class TransactionMapper {

    public static Transaction fromCursor(Cursor cursor) {
        final UUID id = UUID.fromString(cursor.getString(
                cursor.getColumnIndex(DB_APP_TRANSACTIONS_ID)));
        final long amount = cursor.getLong(
                cursor.getColumnIndex(DB_APP_TRANSACTIONS_AMOUNT));
        return new Transaction(id, amount);
    }

    public static ContentValues toContentValues(Transaction transaction) {
        final ContentValues values = new ContentValues();
        values.put(DB_APP_TRANSACTIONS_ID, transaction.getId().toString());
        values.put(DB_APP_TRANSACTIONS_AMOUNT, transaction.getAmount());
        return values;
    }
}
