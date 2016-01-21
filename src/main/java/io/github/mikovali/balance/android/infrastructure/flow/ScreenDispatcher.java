package io.github.mikovali.balance.android.infrastructure.flow;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import flow.Flow;
import flow.History;

public class ScreenDispatcher implements Flow.Dispatcher {

    private AppCompatActivity activity;

    public void onCreate(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void onDestroy() {
        activity = null;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        if (activity == null) {
            throw new IllegalStateException("Activity has not been set for the dispatcher");
        }

        final History origin = traversal.origin;
        final Screen originScreen = origin.top();
        final View originView = originScreen.getView(activity);

        final History destination = traversal.destination;
        final Screen destinationScreen = destination.top();
        final View destinationView = destinationScreen.getView(activity);

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

        activity.setContentView(destinationView);

        callback.onTraversalCompleted();
    }
}
