package io.github.mikovali.android.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Fix common problems with the original {@link android.support.design.widget.TextInputLayout}.
 *
 * - Save error state
 */
public class TextInputLayout extends android.support.design.widget.TextInputLayout {

    public TextInputLayout(Context context) {
        super(context);
    }

    public TextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getError());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setError(savedState.error);
    }

    private static class SavedState extends BaseSavedState {

        final CharSequence error;

        public SavedState(Parcelable superState, CharSequence error) {
            super(superState);
            this.error = error;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(error, out, flags);
        }

        public SavedState(Parcel source) {
            super(source);
            error = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source);
            }
            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, null);
            }
            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
