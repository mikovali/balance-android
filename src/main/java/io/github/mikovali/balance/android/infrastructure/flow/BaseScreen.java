package io.github.mikovali.balance.android.infrastructure.flow;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseScreen<T extends View> implements Screen {

    private final int viewId;

    private final Class<T> type;

    public BaseScreen(int viewId, Class<T> type) {
        this.viewId = viewId;
        this.type = type;
    }

    protected abstract T createView(AppCompatActivity activity);

    @Override
    public View getView(AppCompatActivity activity) {
        final View view = activity.findViewById(viewId);
        if (view != null && type.isAssignableFrom(view.getClass())) {
            return view;
        }
        final T created = createView(activity);
        created.setId(viewId);
        return created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(viewId);
        dest.writeSerializable(type);
    }

    protected BaseScreen(Parcel in, ClassLoader loader) {
        viewId = in.readInt();
        //noinspection unchecked
        type = (Class<T>) in.readSerializable();
    }
}
