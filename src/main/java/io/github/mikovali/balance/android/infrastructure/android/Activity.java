package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import flow.Flow;
import flow.History;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListScreenView;
import io.github.mikovali.balance.android.infrastructure.flow.Screen;

public class Activity extends AppCompatActivity {

    private Flow flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flow = new Flow(History.single(new TransactionListScreenView.TransactionListScreen()));
        flow.setDispatcher(new Flow.Dispatcher() {
            @Override
            public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
                final History destination = traversal.destination;
                final Screen destinationScreen = destination.top();
                final View destinationView = destinationScreen.getView(Activity.this);

                setContentView(destinationView);

                callback.onTraversalCompleted();
            }
        });
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
