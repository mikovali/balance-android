package io.github.mikovali.android.navigation;

import android.content.Context;
import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseScreen implements Screen {

    private final int id;

    public BaseScreen(int id) {
        this.id = id;
    }

    protected abstract View createView(Context context, ViewGroup container);

    @Override
    public int getId() {
        return id;
    }

    @Override
    public View getView(Context context, ViewGroup container) {
        View view = container.findViewById(id);
        if (view == null) {
            view = createView(context, container);
            view.setId(id);
        }
        return view;
    }

    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
    }

    protected BaseScreen(Parcel in) {
        id = in.readInt();
    }
}
