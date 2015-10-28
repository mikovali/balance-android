package io.github.mikovali.balance.android.domain.model;

public class Transaction {

    private long amount;

    public Transaction(long amount) {
        setAmount(amount);
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Amount cannot be 0");
        }
        this.amount = amount;
    }
}
