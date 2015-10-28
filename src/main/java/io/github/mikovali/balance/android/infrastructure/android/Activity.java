package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.mikovali.balance.android.R;

public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
    }
}
