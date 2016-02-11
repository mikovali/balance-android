package io.github.mikovali.balance.android.infrastructure.flow;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;
import flow.History;
import io.github.mikovali.balance.android.R;

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

        // call this before creating the destination view because the view needs the screen ID
        callback.onTraversalCompleted();

        final History origin = traversal.origin;

        final History destination = traversal.destination;
        final Screen destinationScreen = destination.top();
        final View destinationView = destinationScreen.getView(activity);

        switch (traversal.direction) {
            case FORWARD:
                final Screen originScreen = origin.top();
                origin.currentViewState().save(originScreen.getView(activity));
                break;
            case BACKWARD:
                destination.currentViewState().restore(destinationView);
                break;
            case REPLACE:
            default:
                // view lifecycle takes care of the state
                break;
        }

        final ViewGroup contentView = (ViewGroup) activity.findViewById(R.id.content);
        contentView.removeAllViews();
        contentView.addView(destinationView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
