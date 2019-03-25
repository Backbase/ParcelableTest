package com.backbase.test.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import static org.junit.Assert.assertEquals;

/**
 * Extend this class in order to easily test classes that implement {@link Parcelable}.
 * Tests depend upon a correct implementation of {@link P#equals(Object)}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public abstract class ParcelableTest<P extends Parcelable> {

    private Parcel parcel;

    /**
     * @return a new {@link P} created with the normal (non-{@link Parcel}) constructor.
     */
    @NonNull
    protected abstract P newItem();

    /**
     * @return {@link P}{@code #CREATOR}
     */
    @NonNull
    protected abstract Parcelable.Creator<P> itemCreator();

    @Before
    @CallSuper
    public void setUp() {
        parcel = Parcel.obtain();
    }

    @After
    @CallSuper
    public void tearDown() {
        parcel.recycle();
    }

    @Test
    public final void writeToAndCreateFromParcel_createsEquivalentItem() {
        final Parcelable before = newItem();

        before.writeToParcel(parcel, before.describeContents());
        parcel.setDataPosition(0);

        final Parcelable after = itemCreator().createFromParcel(parcel);

        assertEquals(before, after);
        assertEquals(before.hashCode(), after.hashCode());
    }

    protected final Parcel getParcel() {
        return parcel;
    }
}
