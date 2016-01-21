package io.github.mikovali.android.mvp;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View.OnAttachStateChangeListener;

public interface View {

    @IdRes
    int getId();

    Context getContext();

    void addOnAttachStateChangeListener(OnAttachStateChangeListener listener);
}
