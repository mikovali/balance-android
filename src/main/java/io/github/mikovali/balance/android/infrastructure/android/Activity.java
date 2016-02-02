package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import flow.Flow;
import flow.History;
import flow.StateParceler;
import io.github.mikovali.balance.android.application.BackButtonService;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListScreenView;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenDispatcher;

public class Activity extends AppCompatActivity {

    private static final String KEY_HISTORY = "history";

    @Inject
    StateParceler stateParceler;

    @Inject
    ScreenDispatcher dispatcher;

    @Inject
    BackButtonService backButtonService;

    private Flow flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(this).inject(this);

        final History history;
        if (savedInstanceState == null) {
            history = History.single(new TransactionListScreenView.TransactionListScreen());
        } else {
            final Parcelable historyState = savedInstanceState.getParcelable(KEY_HISTORY);
            //noinspection ConstantConditions
            history = History.from(historyState, stateParceler);
        }

        dispatcher.onCreate(this);
        backButtonService.onCreate(this);

        flow = new Flow(history);
        flow.setDispatcher(dispatcher);
    }

    @Override
    protected void onDestroy() {
        backButtonService.onDestroy();
        dispatcher.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_HISTORY, flow.getHistory().getParcelable(stateParceler));
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Flow.isFlowSystemService(name)) {
            return flow;
        }
        return super.getSystemService(name);
    }

    @Override
    public void onBackPressed() {
        if (!backButtonService.onBackButtonClick() && !flow.goBack()) {
            super.onBackPressed();
        }
    }
}
