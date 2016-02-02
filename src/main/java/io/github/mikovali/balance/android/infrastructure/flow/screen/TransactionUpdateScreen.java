package io.github.mikovali.balance.android.infrastructure.flow.screen;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;

import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionUpdateScreenView;
import io.github.mikovali.balance.android.infrastructure.flow.BaseScreen;

public class TransactionUpdateScreen extends BaseScreen<TransactionUpdateScreenView> {

    public TransactionUpdateScreen() {
        super(R.id.transactionUpdateScreen, TransactionUpdateScreenView.class);
    }

    @Override
    protected TransactionUpdateScreenView createView(AppCompatActivity activity) {
        return new TransactionUpdateScreenView(activity);
    }

    private TransactionUpdateScreen(Parcel in, ClassLoader loader) {
        super(in, loader);
    }

    public static final Creator<TransactionUpdateScreen> CREATOR = new ClassLoaderCreator<TransactionUpdateScreen>() {
        @Override
        public TransactionUpdateScreen createFromParcel(Parcel source, ClassLoader loader) {
            return new TransactionUpdateScreen(source, loader);
        }
        @Override
        public TransactionUpdateScreen createFromParcel(Parcel source) {
            return createFromParcel(source, null);
        }
        @Override
        public TransactionUpdateScreen[] newArray(int size) {
            return new TransactionUpdateScreen[size];
        }
    };
}
