package io.github.mikovali.android.mvp;

import android.view.View;

/**
 * Separate class instead of {@link Presenter} implementing because of redundant View parameters.
 */
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
