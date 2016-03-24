package io.github.mikovali.balance.android.infrastructure.flow;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import flow.Flow;
import flow.History;
import flow.StateParceler;
import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.android.navigation.Screen;
import io.github.mikovali.balance.android.application.transaction.TransactionListScreen;

public class FlowNavigationService implements NavigationService {

    private static final String KEY_HISTORY = "navigationHistory";

    private final StateParceler stateParceler;

    private final Flow.Dispatcher dispatcher;

    private Flow flow;

    public FlowNavigationService(StateParceler stateParceler, Flow.Dispatcher dispatcher) {
        this.stateParceler = stateParceler;
        this.dispatcher = dispatcher;
    }

    @Override
    public void goTo(Screen screen) {
        flow.set(screen);
    }

    @Override
    public boolean goBack() {
        return flow.goBack();
    }

    @Override
    public Screen getCurrent() {
        return flow.getHistory().top();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_HISTORY, flow.getHistory().getParcelable(stateParceler));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final History history;
        if (savedInstanceState == null) {
            history = History.single(new TransactionListScreen());
        } else {
            final Parcelable historyState = savedInstanceState.getParcelable(KEY_HISTORY);
            //noinspection ConstantConditions
            history = History.from(historyState, stateParceler);
        }
        flow = new Flow(history);
        flow.setDispatcher(dispatcher);
    }
}
