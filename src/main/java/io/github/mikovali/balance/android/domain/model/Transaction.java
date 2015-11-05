package io.github.mikovali.balance.android.domain.model;

import java.util.UUID;

public class Transaction {

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
}
