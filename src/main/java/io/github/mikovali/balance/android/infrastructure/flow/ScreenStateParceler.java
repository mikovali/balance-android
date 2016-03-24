package io.github.mikovali.balance.android.infrastructure.flow;

import android.os.Parcelable;

import flow.StateParceler;

/**
 * Screen objects are {@link Parcelable}.
 */
public class ScreenStateParceler implements StateParceler {

    @Override
    public Parcelable wrap(Object instance) {
        if (!(instance instanceof Parcelable)) {
            throw new IllegalArgumentException("Screen object must be Parcelable");
        }
        return (Parcelable) instance;
    }

    @Override
    public Object unwrap(Parcelable parcelable) {
        return parcelable;
    }
}
