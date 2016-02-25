package io.github.mikovali.balance.android.infrastructure.android.view;

import android.annotation.SuppressLint;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.WindowService;
import io.github.mikovali.balance.android.infrastructure.android.App;
import io.github.mikovali.balance.android.infrastructure.flow.screen.TransactionUpdateScreen;

@SuppressLint("ViewConstructor")
public final class TransactionListScreenView extends CoordinatorLayout {

    @Inject
    WindowService windowService;

    @Bind(R.id.toolbar)
    Toolbar toolbarView;
    @Bind(R.id.transactionCreateButton)
    FloatingActionButton createButton;

    private final Flow flow;

    public TransactionListScreenView(AppCompatActivity activity) {
        super(activity);
        App.getAppComponent(activity).inject(this);
        flow = Flow.get(activity);

        inflate(activity, R.layout.transaction_list_screen, this);
        ButterKnife.bind(this);

        toolbarView.setTitle(R.string.transaction_list_title);
        toolbarView.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                windowService.showNavigation();
            }
        });
    }

    @OnClick(R.id.transactionCreateButton)
    @SuppressWarnings("unused")
    public void onCreateButtonClick() {
        flow.set(new TransactionUpdateScreen());
    }
}
