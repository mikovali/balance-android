package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import flow.Flow;
import flow.History;
import flow.StateParceler;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListScreenView;
import io.github.mikovali.balance.android.infrastructure.flow.Screen;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenStateParceler;

public class Activity extends AppCompatActivity {

    private static final String KEY_HISTORY = "history";

    private final StateParceler stateParceler = new ScreenStateParceler();

    private Flow flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final History history;
        if (savedInstanceState == null) {
            history = History.single(new TransactionListScreenView.TransactionListScreen());
        } else {
            final Parcelable historyState = savedInstanceState.getParcelable(KEY_HISTORY);
            //noinspection ConstantConditions
            history = History.from(historyState, stateParceler);
        }

        flow = new Flow(history);
        flow.setDispatcher(new Flow.Dispatcher() {
            @Override
            public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
                final History origin = traversal.origin;
                final Screen originScreen = origin.top();
                final View originView = originScreen.getView(Activity.this);

                final History destination = traversal.destination;
                final Screen destinationScreen = destination.top();
                final View destinationView = destinationScreen.getView(Activity.this);

                switch (traversal.direction) {
                    case FORWARD:
                        origin.currentViewState().save(originView);
                        break;
                    case BACKWARD:
                        destination.currentViewState().restore(destinationView);
                        break;
                    case REPLACE:
                    default:
                        // view lifecycle takes care of the state
                        break;
                }

                setContentView(destinationView);

                callback.onTraversalCompleted();
            }
        });
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
        if (!flow.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
