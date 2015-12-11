package io.github.mikovali.android.mvp;

import android.content.Context;
import android.view.View.OnAttachStateChangeListener;

public interface View {

    Context getContext();

    void addOnAttachStateChangeListener(OnAttachStateChangeListener listener);
}
