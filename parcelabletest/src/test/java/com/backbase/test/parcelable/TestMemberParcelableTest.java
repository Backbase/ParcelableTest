package com.backbase.test.parcelable;

import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Ensure {@link TestMemberParcelable} works correctly so that other tests in this test suite can
 * be trusted.
 */
public class TestMemberParcelableTest extends ParcelableTest<TestMemberParcelable> {

    @NonNull
    @Override
    protected TestMemberParcelable newItem() {
        return new TestMemberParcelable(2345);
    }

    @NonNull
    @Override
    protected Parcelable.Creator<TestMemberParcelable> itemCreator() {
        return TestMemberParcelable.CREATOR;
    }
}
