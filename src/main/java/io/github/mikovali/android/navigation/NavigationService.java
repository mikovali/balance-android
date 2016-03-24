package io.github.mikovali.android.navigation;

import android.os.Bundle;

public interface NavigationService {

    void goTo(Screen screen);

    boolean goBack();

    Screen getCurrent();

    // Activity lifecycle

    void onSaveInstanceState(Bundle outState);

    void onCreate(Bundle savedInstanceState);
}
