package io.github.mikovali.balance.android.application.transaction;

import android.content.Context;
import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;

import io.github.mikovali.android.navigation.BaseScreen;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.android.ui.screen.TransactionListScreenView;

public class TransactionListScreen extends BaseScreen {

    public TransactionListScreen() {
        super(R.id.transactionListScreen);
    }

    @Override
    protected View createView(Context context, ViewGroup container) {
        return new TransactionListScreenView(context);
    }

    // Parcelable

    protected TransactionListScreen(Parcel parcel) {
        super(parcel);
    }

    public static final Creator<TransactionListScreen> CREATOR
            = new Creator<TransactionListScreen>() {
        @Override
        public TransactionListScreen createFromParcel(Parcel parcel) {
            return new TransactionListScreen(parcel);
        }
        @Override
        public TransactionListScreen[] newArray(int size) {
            return new TransactionListScreen[size];
        }
    };
}
