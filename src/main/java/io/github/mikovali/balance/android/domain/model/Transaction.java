package io.github.mikovali.balance.android.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import io.github.mikovali.android.utils.ParcelUtils;

public class Transaction implements Parcelable {

    private final UUID id;

    private long amount;

    public Transaction(UUID id, long amount) {
        if (id == null) {
            throw new IllegalArgumentException("Transaction id cannot be null");
        }
        this.id = id;
        setAmount(amount);
    }

    public UUID getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Transaction amount cannot be 0");
        }
        this.amount = amount;
    }

    // parcel

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeUuid(id, dest);
        dest.writeLong(amount);
    }

    private Transaction(Parcel in) {
        id = ParcelUtils.readUuid(in);
        amount = in.readLong();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }
        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
