package io.github.mikovali.android.mvp;

import android.view.View;

public class PresenterOnAttachStateChangeListener implements View.OnAttachStateChangeListener {

    private final Presenter presenter;

    public PresenterOnAttachStateChangeListener(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        presenter.onAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        presenter.onDetachedFromWindow();
    }
}
