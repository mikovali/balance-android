package io.github.mikovali.android.mvp;

import android.os.Bundle;

public interface Presenter {

    void onAttachedToWindow();

    void onDetachedFromWindow();

    void onSaveState(Bundle state);

    void onRestoreState(Bundle state);
}
