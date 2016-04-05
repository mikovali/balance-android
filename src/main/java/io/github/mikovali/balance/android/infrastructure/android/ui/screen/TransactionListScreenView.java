package io.github.mikovali.balance.android.infrastructure.android.ui.screen;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.WindowService;
import io.github.mikovali.balance.android.application.transaction.TransactionUpdateScreen;
import io.github.mikovali.balance.android.infrastructure.android.App;

public final class TransactionListScreenView extends CoordinatorLayout {

    @Inject
    NavigationService navigationService;

    @Inject
    WindowService windowService;

    @Bind(R.id.toolbar)
    Toolbar toolbarView;
    @Bind(R.id.transactionCreateButton)
    FloatingActionButton createButton;

    public TransactionListScreenView(Context context) {
        super(context);
        App.getAppComponent(context).inject(this);

        inflate(context, R.layout.transaction_list_screen, this);
        ButterKnife.bind(this);

        toolbarView.setTitle(R.string.transaction_list_title);
        toolbarView.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                windowService.navigation();
            }
        });
    }

    @OnClick(R.id.transactionCreateButton)
    @SuppressWarnings("unused")
    public void onCreateButtonClick() {
        navigationService.goTo(new TransactionUpdateScreen());
    }
}
