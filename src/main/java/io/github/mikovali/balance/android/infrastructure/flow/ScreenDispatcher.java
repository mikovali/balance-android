package io.github.mikovali.balance.android.infrastructure.flow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;
import flow.History;
import io.github.mikovali.android.navigation.Screen;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.ActivityProvider;

public class ScreenDispatcher implements Flow.Dispatcher {

    public final ActivityProvider activityProvider;

    public ScreenDispatcher(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        final Activity activity = activityProvider.getCurrentActivity();
        if (activity == null) {
            throw new IllegalStateException("Activity has not been set for the dispatcher");
        }

        // call this before creating the destination view because the view needs the screen ID
        callback.onTraversalCompleted();

        final ViewGroup contentView = (ViewGroup) activity.findViewById(R.id.content);

        final History origin = traversal.origin;

        final History destination = traversal.destination;
        final Screen destinationScreen = destination.top();
        final View destinationView = destinationScreen.getView(activity, contentView);

        switch (traversal.direction) {
            case FORWARD:
                final Screen originScreen = origin.top();
                origin.currentViewState().save(originScreen.getView(activity, contentView));
                break;
            case BACKWARD:
                destination.currentViewState().restore(destinationView);
                break;
            case REPLACE:
            default:
                // view lifecycle takes care of the state
                break;
        }

        contentView.removeAllViews();
        contentView.addView(destinationView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
