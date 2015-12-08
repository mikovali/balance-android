package io.github.mikovali.android.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Utilities to make working with nullable values and parcels easier.
 */
public final class ParcelUtils {

    private static final int NULL = -1;
    private static final int NOT_NULL = 0;

    // boolean

    public static void writeBoolean(boolean value, Parcel dest) {
        dest.writeInt(value ? 1 : 0);
    }

    public static boolean readBoolean(Parcel in) {
        return in.readInt() == 1;
    }

    // Integer

    public static void writeInteger(Integer value, Parcel dest) {
        if (!setNull(value, dest)) {
            dest.writeInt(value);
        }
    }

    public static Integer readInteger(Parcel in) {
        return isNull(in) ? null : in.readInt();
    }

    // Long

    public static void writeLong(Long value, Parcel dest) {
        if (!setNull(value, dest)) {
            dest.writeLong(value);
        }
    }

    public static Long readLong(Parcel in) {
        return isNull(in) ? null : in.readLong();
    }

    // Float

    public static void writeFloat(Float value, Parcel dest) {
        if (!setNull(value, dest)) {
            dest.writeFloat(value);
        }
    }

    public static Float readFloat(Parcel in) {
        return isNull(in) ? null : in.readFloat();
    }

    // Double

    public static void writeDouble(Double value, Parcel dest) {
        if (!setNull(value, dest)) {
            dest.writeDouble(value);
        }
    }

    public static Double readDouble(Parcel in) {
        return isNull(in) ? null : in.readDouble();
    }

    // Parcelable

    public static <T extends Parcelable> void writeParcelable(T value, Parcel dest, int flags) {
        if (!setNull(value, dest)) {
            value.writeToParcel(dest, flags);
        }
    }

    public static <T extends Parcelable> T readParcelable(Parcelable.Creator<T> creator,
                                                          Parcel in) {
        return isNull(in) ? null : creator.createFromParcel(in);
    }

    public static <T extends Enum<T>> void writeEnum(T value, Parcel dest) {
        if (!setNull(value, dest)) {
            dest.writeString(value.name());
        }
    }

    public static <T extends Enum<T>> T readEnum(Class<T> type, Parcel in) {
        return isNull(in) ? null : Enum.valueOf(type, in.readString());
    }

    // UUID

    public static void writeUuid(UUID value, Parcel dest) {
        final String string = value == null ? null : value.toString();
        dest.writeString(string);
    }

    public static UUID readUuid(Parcel in) {
        final String string = in.readString();
        return string == null ? null : UUID.fromString(string);
    }

    // helpers

    private static boolean setNull(Object value, Parcel dest) {
        final boolean isNull = value == null;
        dest.writeInt(isNull ? NULL : NOT_NULL);
        return isNull;
    }

    private static boolean isNull(Parcel in) {
        return in.readInt() == NULL;
    }
}
