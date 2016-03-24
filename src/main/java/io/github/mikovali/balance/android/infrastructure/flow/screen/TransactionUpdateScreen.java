package io.github.mikovali.balance.android.infrastructure.flow.screen;

import android.content.Context;
import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;

import io.github.mikovali.android.navigation.BaseScreen;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionUpdateScreenView;

public class TransactionUpdateScreen extends BaseScreen {

    public TransactionUpdateScreen() {
        super(R.id.transactionUpdateScreen);
    }

    @Override
    protected View createView(Context context, ViewGroup container) {
        return new TransactionUpdateScreenView(context);
    }

    // Parcelable

    protected TransactionUpdateScreen(Parcel parcel) {
        super(parcel);
    }

    public static final Creator<TransactionUpdateScreen> CREATOR
            = new Creator<TransactionUpdateScreen>() {
        @Override
        public TransactionUpdateScreen createFromParcel(Parcel parcel) {
            return new TransactionUpdateScreen(parcel);
        }
        @Override
        public TransactionUpdateScreen[] newArray(int size) {
            return new TransactionUpdateScreen[size];
        }
    };
}
