package io.github.mikovali.balance.android.infrastructure.flow;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.mikovali.balance.android.R;

public abstract class BaseScreen<T extends View> implements Screen {

    private final Class<T> type;

    public BaseScreen(Class<T> type) {
        this.type = type;
    }

    protected abstract T createView(AppCompatActivity activity);

    @Override
    public View getView(AppCompatActivity activity) {
        final View view = activity.findViewById(R.id.screen);
        if (view != null && type.isAssignableFrom(view.getClass())) {
            return view;
        }
        final T created = createView(activity);
        created.setId(R.id.screen);
        return created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(type);
    }

    protected BaseScreen(Parcel in, ClassLoader loader) {
        //noinspection unchecked
        type = (Class<T>) in.readSerializable();
    }
}
