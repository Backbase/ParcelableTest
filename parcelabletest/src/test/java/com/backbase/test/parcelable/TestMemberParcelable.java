package com.backbase.test.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * A basic {@link Parcelable} implementation for use by {@link TestParcelable}.
 */
public final class TestMemberParcelable implements Parcelable {

    public static final Creator<TestMemberParcelable> CREATOR = new Creator<TestMemberParcelable>() {
        @Override
        public TestMemberParcelable createFromParcel(Parcel source) {
            return new TestMemberParcelable(source);
        }

        @Override
        public TestMemberParcelable[] newArray(int size) {
            return new TestMemberParcelable[size];
        }
    };

    private final int value;

    TestMemberParcelable(int value) {
        this.value = value;
    }

    private TestMemberParcelable(Parcel in) {
        this.value = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestMemberParcelable)) return false;

        final TestMemberParcelable that = (TestMemberParcelable) o;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    int getValue() {
        return value;
    }
}
