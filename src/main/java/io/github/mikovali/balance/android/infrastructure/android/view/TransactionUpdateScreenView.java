package io.github.mikovali.balance.android.infrastructure.android.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.flow.BaseScreen;

@SuppressLint("ViewConstructor")
public class TransactionUpdateScreenView extends LinearLayout {

    @Bind(R.id.toolbar)
    Toolbar toolbarView;

    public TransactionUpdateScreenView(AppCompatActivity activity) {
        super(activity);

        inflate(activity, R.layout.transaction_update_screen, this);
        ButterKnife.bind(this);

        setOrientation(VERTICAL);

        toolbarView.setTitle(R.string.transaction_update_create_title);
        final Drawable navigationIcon = DrawableCompat.wrap(ResourcesCompat
                .getDrawable(activity.getResources(), R.drawable.ic_clear, null));
        DrawableCompat.setTint(navigationIcon, Color.WHITE);
        toolbarView.setNavigationIcon(navigationIcon);
        toolbarView.setNavigationContentDescription(R.string.transaction_update_close);
        activity.setSupportActionBar(toolbarView);
    }

    public static final class TransactionUpdateScreen extends BaseScreen<TransactionUpdateScreenView> {

        public TransactionUpdateScreen() {
            super(TransactionUpdateScreenView.class);
        }

        @Override
        protected TransactionUpdateScreenView createView(AppCompatActivity activity) {
            return new TransactionUpdateScreenView(activity);
        }

        private TransactionUpdateScreen(Parcel in, ClassLoader loader) {
            super(in, loader);
        }

        public static final Creator<TransactionUpdateScreen> CREATOR =
                new ClassLoaderCreator<TransactionUpdateScreen>() {
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
}
