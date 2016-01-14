package io.github.mikovali.balance.android.infrastructure.android.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.flow.Screen;

public final class TransactionListScreenView extends CoordinatorLayout {

    public static final class TransactionListScreen implements Screen {
        @Override
        public View getView(AppCompatActivity activity) {
            return new TransactionListScreenView(activity);
        }
    }

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

        setId(R.id.screen);
        activity.setSupportActionBar(toolbarView);

        final Drawable createButtonDrawable = DrawableCompat.wrap(createButton.getDrawable());
        DrawableCompat.setTint(createButtonDrawable, Color.WHITE);
    }

    @OnClick(R.id.transactionCreateButton)
    @SuppressWarnings("unused")
    public void onCreateButtonClick() {
        flow.set(new TransactionUpdateScreenView.TransactionUpdateScreen());
    }
}
