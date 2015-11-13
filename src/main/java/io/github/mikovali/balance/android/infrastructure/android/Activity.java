package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListFragment;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionUpdateFragment;

public class Activity extends AppCompatActivity implements TransactionListFragment.Listener {

    private FragmentManager fragmentManager;

    @Override
    public void onTransactionCreateButtonClick() {
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, TransactionUpdateFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    // lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(android.R.id.content) == null) {
            fragmentManager.beginTransaction()
                    .add(android.R.id.content, TransactionListFragment.newInstance())
                    .commit();
        }
    }
}
