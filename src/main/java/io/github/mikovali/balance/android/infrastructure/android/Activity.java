package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListFragment;

public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, TransactionListFragment.newInstance())
                    .commit();
        }
    }
}
