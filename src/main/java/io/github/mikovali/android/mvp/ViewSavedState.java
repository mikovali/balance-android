package io.github.mikovali.android.mvp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * View implementations should use this to pass the state saving to the presenter.
 *
 * @see #onSaveInstanceState(Parcelable, Presenter)
 * @see #onRestoreInstanceState(Parcelable, Presenter)
 */
public class ViewSavedState extends View.BaseSavedState {

    private final Bundle presenterState;

    /**
     * Use in view implementation to save state.
     *
     * Example:
     * <pre>
     * &#64;Override
     * protected Parcelable onSaveInstanceState() {
     *     return ViewSavedState.onSaveInstanceState(super.onSaveInstanceState(), presenter);
     * }
     * </pre>
     */
    public static Parcelable onSaveInstanceState(Parcelable parentState, Presenter presenter) {
        final Bundle state = new Bundle();
        presenter.onSaveState(state);
        return new ViewSavedState(parentState, state);
    }

    /**
     * Use in view implementation to restore state.
     *
     * Example:
     * <pre>
     * &#64;Override
     * protected void onRestoreInstanceState(Parcelable state) {
     *     super.onRestoreInstanceState(ViewSavedState.onRestoreInstanceState(state, presenter));
     * }
     * </pre>
     */
    public static Parcelable onRestoreInstanceState(Parcelable state, Presenter presenter) {
        if (!(state instanceof ViewSavedState)) {
            return state;
        }
        final ViewSavedState savedState = (ViewSavedState) state;
        presenter.onRestoreState(savedState.presenterState);
        return savedState.getSuperState();
    }

    private ViewSavedState(Parcelable superState, Bundle presenterState) {
        super(superState);
        this.presenterState = presenterState;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeBundle(presenterState);
    }

    private ViewSavedState(Parcel source) {
        super(source);
        presenterState = source.readBundle();
    }

    public static final Creator<ViewSavedState> CREATOR = new Creator<ViewSavedState>() {
        @Override
        public ViewSavedState createFromParcel(Parcel source) {
            return new ViewSavedState(source);
        }
        @Override
        public ViewSavedState[] newArray(int size) {
            return new ViewSavedState[size];
        }
    };
}
