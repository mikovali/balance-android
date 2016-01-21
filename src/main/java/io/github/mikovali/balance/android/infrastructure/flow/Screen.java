package io.github.mikovali.balance.android.infrastructure.flow;

import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public interface Screen extends Parcelable {

    @IdRes
    int getId();

    View getView(AppCompatActivity activity);
}
