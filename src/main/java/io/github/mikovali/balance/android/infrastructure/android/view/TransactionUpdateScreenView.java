package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.DeviceService;
import io.github.mikovali.balance.android.infrastructure.android.App;

public class TransactionUpdateScreenView extends LinearLayout implements
        Toolbar.OnMenuItemClickListener {

    @Inject
    DeviceService deviceService;

    @Bind(R.id.toolbar)
    Toolbar toolbarView;
    @Bind(R.id.transactionUpdate)
    TransactionUpdateAndroidView updateView;

    public TransactionUpdateScreenView(Context context) {
        super(context);
        App.getAppComponent(context).inject(this);
        inflate(context, R.layout.transaction_update_screen, this);
        ButterKnife.bind(this);

        setOrientation(VERTICAL);

        toolbarView.setTitle(R.string.transaction_update_create_title);
        toolbarView.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceService.triggerBackButtonClick();
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
