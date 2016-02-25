package io.github.mikovali.balance.android.infrastructure.android.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import io.github.mikovali.balance.android.R;

@SuppressLint("ViewConstructor")
public class TransactionUpdateScreenView extends LinearLayout implements
        Toolbar.OnMenuItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbarView;
    @Bind(R.id.transactionUpdate)
    TransactionUpdateAndroidView updateView;

    public TransactionUpdateScreenView(AppCompatActivity activity) {
        super(activity);
        final Flow flow = Flow.get(activity);

        inflate(activity, R.layout.transaction_update_screen, this);
        ButterKnife.bind(this);

        setOrientation(VERTICAL);

        toolbarView.setTitle(R.string.transaction_update_create_title);
        toolbarView.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.goBack();
            }
        });
        toolbarView.inflateMenu(R.menu.transaction_update);
        // TODO color
        final Drawable doneButtonIcon = DrawableCompat.wrap(toolbarView.getMenu()
                .findItem(R.id.transactionUpdateDoneButton).getIcon());
        DrawableCompat.setTint(doneButtonIcon, Color.WHITE);
        toolbarView.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.transactionUpdateDoneButton:
                updateView.onDoneButtonClick();
                return true;
            default:
                return false;
        }
    }
}
