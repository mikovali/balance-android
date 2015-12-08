package io.github.mikovali.android.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BundleUtils {

    public static <T extends Parcelable> void putParcelableList(String key, List<T> value,
                                                                Parcelable.Creator<T> creator,
                                                                Bundle bundle) {
        final T[] array = value == null ? null : value.toArray(creator.newArray(value.size()));
        bundle.putParcelableArray(key, array);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getParcelableList(String key, Bundle bundle) {
        final T[] array = (T[]) bundle.getParcelableArray(key);
        return array == null ? null : new ArrayList<>(Arrays.asList(array));
    }
}
