package io.github.mikovali.android.mvp;

import android.os.Bundle;

public abstract class BasePresenter<T extends View> implements Presenter {

    protected final T view;

    public BasePresenter(T view) {
        this.view = view;
        view.addOnAttachStateChangeListener(new PresenterOnAttachStateChangeListener(this));
    }

    @Override
    public void onAttachedToWindow() {
    }

    @Override
    public void onDetachedFromWindow() {
    }

    @Override
    public void onSaveState(Bundle state) {
    }

    @Override
    public void onRestoreState(Bundle state) {
    }
}
