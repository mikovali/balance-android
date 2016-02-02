package io.github.mikovali.balance.android.infrastructure.flow.screen;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;

import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListScreenView;
import io.github.mikovali.balance.android.infrastructure.flow.BaseScreen;

public class TransactionListScreen extends BaseScreen<TransactionListScreenView> {

    public TransactionListScreen() {
        super(R.id.transactionListScreen, TransactionListScreenView.class);
    }

    @Override
    protected TransactionListScreenView createView(AppCompatActivity activity) {
        return new TransactionListScreenView(activity);
    }

    private TransactionListScreen(Parcel in, ClassLoader loader) {
        super(in, loader);
    }

    public static final Creator<TransactionListScreen> CREATOR
            = new ClassLoaderCreator<TransactionListScreen>() {
        @Override
        public TransactionListScreen createFromParcel(Parcel source, ClassLoader loader) {
            return new TransactionListScreen(source, loader);
        }
        @Override
        public TransactionListScreen createFromParcel(Parcel source) {
            return createFromParcel(source, null);
        }
        @Override
        public TransactionListScreen[] newArray(int size) {
            return new TransactionListScreen[size];
        }
    };
}
