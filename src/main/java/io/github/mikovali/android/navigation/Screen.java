package io.github.mikovali.android.navigation;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

public interface Screen extends Parcelable {

    @IdRes
    int getId();

    View getView(Context context, ViewGroup container);
}
