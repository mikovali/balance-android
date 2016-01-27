package io.github.mikovali.android.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;

public abstract class BasePresenter<T extends View> implements Presenter {

    protected final T view;

    public BasePresenter(T view) {
        this.view = view;
        view.addOnAttachStateChangeListener(new PresenterOnAttachStateChangeListener(this));
    }

    @CallSuper
    @Override
    public void onAttachedToWindow() {
    }

    @CallSuper
    @Override
    public void onDetachedFromWindow() {
    }

    @CallSuper
    @Override
    public void onSaveState(Bundle state) {
    }

    @CallSuper
    @Override
    public void onRestoreState(Bundle state) {
    }
}
