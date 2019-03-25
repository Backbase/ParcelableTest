package com.backbase.test.parcelable;

import android.os.Parcelable;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import androidx.annotation.NonNull;

/**
 * Implements {@link ParcelableTest} for a complex {@link Parcelable} implementation
 */
@RunWith(RobolectricTestRunner.class)
public class ParcelableTestTest extends ParcelableTest<TestParcelable> {

    @NonNull
    @Override
    protected TestParcelable newItem() {
        final TestParcelable item = new TestParcelable(new TestMemberParcelable(1), "Final string", 2, 3, 4L, 5L, 6f, 7f, 8d, 9d, true, true,
                (byte) 10, (byte) 11, (short) 12, (short) 13, 'a', 'b', TestEnum.A, Collections.singletonList(14), new BigDecimal(15), new Date(16),
                17);
        item.setMutableParcelable(new TestMemberParcelable(99));
        item.setMutableString("Mutable string");
        item.setMutablePrimitiveInt(98);
        item.setMutableObjectInteger(97);
        item.setMutablePrimitiveLong(96L);
        item.setMutableObjectLong(95L);
        item.setMutablePrimitiveFloat(94f);
        item.setMutableObjectFloat(93f);
        item.setMutablePrimitiveDouble(92d);
        item.setMutableObjectDouble(91d);
        item.setMutablePrimitiveBoolean(true);
        item.setMutableObjectBoolean(true);
        item.setMutablePrimitiveByte((byte) 90);
        item.setMutableObjectByte((byte) 89);
        item.setMutablePrimitiveShort((short) 88);
        item.setMutableObjectShort((short) 87);
        item.setMutablePrimitiveChar('z');
        item.setMutableObjectCharacter('y');
        item.setMutableEnum(TestEnum.C);
        item.setMutableIntegerList(Collections.singletonList(86));
        return item;
    }

    @NonNull
    @Override
    protected Parcelable.Creator<TestParcelable> itemCreator() {
        return TestParcelable.CREATOR;
    }
}
