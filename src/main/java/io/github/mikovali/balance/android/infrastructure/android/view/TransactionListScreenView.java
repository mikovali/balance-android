package io.github.mikovali.balance.android.infrastructure.android.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.flow.BaseScreen;

@SuppressLint("ViewConstructor")
public final class TransactionListScreenView extends CoordinatorLayout {

    @Bind(R.id.toolbar)
    Toolbar toolbarView;
    @Bind(R.id.transactionCreateButton)
    FloatingActionButton createButton;

    private final Flow flow;

    public TransactionListScreenView(AppCompatActivity activity) {
        super(activity);
        flow = Flow.get(activity);

        inflate(activity, R.layout.transaction_list_screen, this);
        ButterKnife.bind(this);

        activity.setSupportActionBar(toolbarView);

        final Drawable createButtonDrawable = DrawableCompat.wrap(createButton.getDrawable());
        DrawableCompat.setTint(createButtonDrawable, Color.WHITE);
    }

    @OnClick(R.id.transactionCreateButton)
    @SuppressWarnings("unused")
    public void onCreateButtonClick() {
        flow.set(new TransactionUpdateScreenView.TransactionUpdateScreen());
    }

    public static final class TransactionListScreen extends BaseScreen<TransactionListScreenView> {

        public TransactionListScreen() {
            super(TransactionListScreenView.class);
        }

        @Override
        protected TransactionListScreenView createView(AppCompatActivity activity) {
            return new TransactionListScreenView(activity);
        }

        private TransactionListScreen(Parcel in, ClassLoader loader) {
            super(in, loader);
        }

        public static final Creator<TransactionListScreen> CREATOR =
                new ClassLoaderCreator<TransactionListScreen>() {
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
}
