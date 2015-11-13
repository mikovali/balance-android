package io.github.mikovali.android.utils;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

public final class FragmentUtils {

    /**
     * Returns the "parent" of this fragment.
     *
     * Used for getting listeners as "parent" classes.
     * For example, if activity has implemented some listener interface
     * you can get this safely in the fragment like this:
     *
     * private Listener listener;
     * public void onAttach(android.app.Activity activity) {
     *     super.onAttach(activity);
     *     listener = FragmentUtil.getParentAs(this, Listener.class);
     * }
     * public void onDetach() {
     *     listener = null;
     *     super.onDetach();
     * }
     *
     * Order of "parent" class finding
     * - {@link android.support.v4.app.Fragment#getTargetFragment()}  target fragment}
     * - {@link android.support.v4.app.Fragment#getParentFragment() parent fragment, grandparent fragment, etc}
     * - {@link android.support.v4.app.Fragment#getActivity() activity}
     * - {@link android.support.v4.app.FragmentActivity#getApplication() application}
     *
     * Returns null if none matches.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParentAs(Fragment fragment, Class<T> type) {
        // check target fragment
        final Fragment target = fragment.getTargetFragment();
        if (target != null && type.isInstance(target)) {
            return (T) target;
        }

        // check for parent fragments
        Fragment parent = fragment.getParentFragment();
        while (parent != null) {
            if (type.isInstance(parent)) {
                return (T) parent;
            }
            parent = parent.getParentFragment();
        }

        // check activity
        final Activity activity = fragment.getActivity();
        if (activity == null) {
            return null;
        }
        if (type.isInstance(activity)) {
            return (T) activity;
        }

        // check application
        final Application application = activity.getApplication();
        if (type.isInstance(application)) {
            return (T) application;
        }

        return null;
    }
}
