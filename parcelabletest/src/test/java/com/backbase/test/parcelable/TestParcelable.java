package com.backbase.test.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * An implementation of {@link Parcelable} that contains every type of parcel-friendly field,
 * used to test {@link ParcelableTest} and its subclasses.
 */
final class TestParcelable implements Parcelable {

    public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
        @Override
        public TestParcelable createFromParcel(Parcel source) {
            return new TestParcelable(source);
        }

        @Override
        public TestParcelable[] newArray(int size) {
            return new TestParcelable[size];
        }
    };

    //region final members
    private final TestMemberParcelable finalParcelable;

    private final String finalString;

    private final int finalPrimitiveInt;
    @NonNull private final Integer finalObjectInteger;

    private final long finalPrimitiveLong;
    @NonNull private final Long finalObjectLong;

    private final float finalPrimitiveFloat;
    @NonNull private final Float finalObjectFloat;

    private final double finalPrimitiveDouble;
    @NonNull private final Double finalObjectDouble;

    private final boolean finalPrimitiveBoolean;
    @NonNull private final Boolean finalObjectBoolean;

    private final byte finalPrimitiveByte;
    @NonNull private final Byte finalObjectByte;

    private final short finalPrimitiveShort;
    @NonNull private final Short finalObjectShort;

    private final char finalPrimitiveChar;
    @NonNull private final Character finalObjectCharacter;

    private final TestEnum finalEnum;

    private final List<Integer> finalIntegerList;

    private final String finalBigDecimalString;

    private final long finalDateLong;

    private final Number finalNumber;
    //endregion

    //region mutable members
    private TestMemberParcelable mutableParcelable;

    private String mutableString;

    private int mutablePrimitiveInt;
    @NonNull private Integer mutableObjectInteger = 0;

    private long mutablePrimitiveLong;
    @NonNull private Long mutableObjectLong = 0L;

    private float mutablePrimitiveFloat;
    @NonNull private Float mutableObjectFloat = 0f;

    private double mutablePrimitiveDouble;
    @NonNull private Double mutableObjectDouble = 0d;

    private boolean mutablePrimitiveBoolean;
    @NonNull private Boolean mutableObjectBoolean = false;

    private byte mutablePrimitiveByte;
    @NonNull private Byte mutableObjectByte = 0;

    private short mutablePrimitiveShort;
    @NonNull private Short mutableObjectShort = 0;

    private char mutablePrimitiveChar;
    @NonNull private Character mutableObjectCharacter = '\u0000';

    private TestEnum mutableEnum;

    private List<Integer> mutableIntegerList;
    //endregion

    public TestParcelable(TestMemberParcelable finalParcelable, String finalString, int finalPrimitiveInt, @NonNull Integer finalObjectInteger,
            long finalPrimitiveLong, @NonNull Long finalObjectLong, float finalPrimitiveFloat, @NonNull Float finalObjectFloat,
            double finalPrimitiveDouble, @NonNull Double finalObjectDouble, boolean finalPrimitiveBoolean, @NonNull Boolean finalObjectBoolean,
            byte finalPrimitiveByte, @NonNull Byte finalObjectByte, short finalPrimitiveShort, @NonNull Short finalObjectShort,
            char finalPrimitiveChar, @NonNull Character finalObjectCharacter, TestEnum finalEnum, List<Integer> finalIntegerList,
            BigDecimal finalBigDecimal, Date finalDate, Number finalNumber) {
        this.finalParcelable = finalParcelable;
        this.finalString = finalString;
        this.finalPrimitiveInt = finalPrimitiveInt;
        this.finalObjectInteger = finalObjectInteger;
        this.finalPrimitiveLong = finalPrimitiveLong;
        this.finalObjectLong = finalObjectLong;
        this.finalPrimitiveFloat = finalPrimitiveFloat;
        this.finalObjectFloat = finalObjectFloat;
        this.finalPrimitiveDouble = finalPrimitiveDouble;
        this.finalObjectDouble = finalObjectDouble;
        this.finalPrimitiveBoolean = finalPrimitiveBoolean;
        this.finalObjectBoolean = finalObjectBoolean;
        this.finalPrimitiveByte = finalPrimitiveByte;
        this.finalObjectByte = finalObjectByte;
        this.finalPrimitiveShort = finalPrimitiveShort;
        this.finalObjectShort = finalObjectShort;
        this.finalPrimitiveChar = finalPrimitiveChar;
        this.finalObjectCharacter = finalObjectCharacter;
        this.finalEnum = finalEnum;
        this.finalIntegerList = finalIntegerList;
        this.finalBigDecimalString = finalBigDecimal.toPlainString();
        this.finalDateLong = finalDate.getTime();
        this.finalNumber = finalNumber;
    }

    private TestParcelable(Parcel in) {
        finalParcelable = in.readParcelable(TestMemberParcelable.class.getClassLoader());
        finalString = in.readString();
        finalPrimitiveInt = in.readInt();
        finalObjectInteger = in.readInt();
        finalPrimitiveLong = in.readLong();
        finalObjectLong = in.readLong();
        finalPrimitiveFloat = in.readFloat();
        finalObjectFloat = in.readFloat();
        finalPrimitiveDouble = in.readDouble();
        finalObjectDouble = in.readDouble();
        finalPrimitiveBoolean = readBoolean(in);
        finalObjectBoolean = readBoolean(in);
        finalPrimitiveByte = in.readByte();
        finalObjectByte = in.readByte();
        finalPrimitiveShort = (short) in.readInt();
        finalObjectShort = (short) in.readInt();
        finalPrimitiveChar = readChar(in);
        finalObjectCharacter = readChar(in);
        finalEnum = readEnum(in, TestEnum.class);
        //noinspection unchecked
        finalIntegerList = in.readArrayList(null);
        finalBigDecimalString = in.readString();
        finalDateLong = in.readLong();
        finalNumber = (Number) in.readSerializable();

        mutableParcelable = in.readParcelable(TestMemberParcelable.class.getClassLoader());
        mutableString = in.readString();
        mutablePrimitiveInt = in.readInt();
        mutableObjectInteger = in.readInt();
        mutablePrimitiveLong = in.readLong();
        mutableObjectLong = in.readLong();
        mutablePrimitiveFloat = in.readFloat();
        mutableObjectFloat = in.readFloat();
        mutablePrimitiveDouble = in.readDouble();
        mutableObjectDouble = in.readDouble();
        mutablePrimitiveBoolean = readBoolean(in);
        mutableObjectBoolean = readBoolean(in);
        mutablePrimitiveByte = in.readByte();
        mutableObjectByte = in.readByte();
        mutablePrimitiveShort = (short) in.readInt();
        mutableObjectShort = (short) in.readInt();
        mutablePrimitiveChar = readChar(in);
        mutableObjectCharacter = readChar(in);
        mutableEnum = readEnum(in, TestEnum.class);
        //noinspection unchecked
        mutableIntegerList = in.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(finalParcelable, flags);
        dest.writeString(finalString);
        dest.writeInt(finalPrimitiveInt);
        dest.writeInt(finalObjectInteger);
        dest.writeLong(finalPrimitiveLong);
        dest.writeLong(finalObjectLong);
        dest.writeFloat(finalPrimitiveFloat);
        dest.writeFloat(finalObjectFloat);
        dest.writeDouble(finalPrimitiveDouble);
        dest.writeDouble(finalObjectDouble);
        writeBoolean(dest, finalPrimitiveBoolean);
        writeBoolean(dest, finalObjectBoolean);
        dest.writeByte(finalPrimitiveByte);
        dest.writeByte(finalObjectByte);
        dest.writeInt(finalPrimitiveShort);
        dest.writeInt(finalObjectShort);
        writeChar(dest, finalPrimitiveChar);
        writeChar(dest, finalObjectCharacter);
        writeEnum(dest, finalEnum);
        dest.writeList(finalIntegerList);
        dest.writeString(finalBigDecimalString);
        dest.writeLong(finalDateLong);
        dest.writeSerializable(finalNumber);

        dest.writeParcelable(mutableParcelable, flags);
        dest.writeString(mutableString);
        dest.writeInt(mutablePrimitiveInt);
        dest.writeInt(mutableObjectInteger);
        dest.writeLong(mutablePrimitiveLong);
        dest.writeLong(mutableObjectLong);
        dest.writeFloat(mutablePrimitiveFloat);
        dest.writeFloat(mutableObjectFloat);
        dest.writeDouble(mutablePrimitiveDouble);
        dest.writeDouble(mutableObjectDouble);
        writeBoolean(dest, mutablePrimitiveBoolean);
        writeBoolean(dest, mutableObjectBoolean);
        dest.writeByte(mutablePrimitiveByte);
        dest.writeByte(mutableObjectByte);
        dest.writeInt(mutablePrimitiveShort);
        dest.writeInt(mutableObjectShort);
        writeChar(dest, mutablePrimitiveChar);
        writeChar(dest, mutableObjectCharacter);
        writeEnum(dest, mutableEnum);
        dest.writeList(mutableIntegerList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestParcelable)) return false;
        TestParcelable that = (TestParcelable) o;
        return finalPrimitiveInt == that.finalPrimitiveInt &&
                finalPrimitiveLong == that.finalPrimitiveLong &&
                Float.compare(that.finalPrimitiveFloat, finalPrimitiveFloat) == 0 &&
                Double.compare(that.finalPrimitiveDouble, finalPrimitiveDouble) == 0 &&
                finalPrimitiveBoolean == that.finalPrimitiveBoolean &&
                finalPrimitiveByte == that.finalPrimitiveByte &&
                finalPrimitiveShort == that.finalPrimitiveShort &&
                finalPrimitiveChar == that.finalPrimitiveChar &&
                mutablePrimitiveInt == that.mutablePrimitiveInt &&
                mutablePrimitiveLong == that.mutablePrimitiveLong &&
                Float.compare(that.mutablePrimitiveFloat, mutablePrimitiveFloat) == 0 &&
                Double.compare(that.mutablePrimitiveDouble, mutablePrimitiveDouble) == 0 &&
                mutablePrimitiveBoolean == that.mutablePrimitiveBoolean &&
                mutablePrimitiveByte == that.mutablePrimitiveByte &&
                mutablePrimitiveShort == that.mutablePrimitiveShort &&
                mutablePrimitiveChar == that.mutablePrimitiveChar &&
                Objects.equals(finalParcelable, that.finalParcelable) &&
                Objects.equals(finalString, that.finalString) &&
                Objects.equals(finalObjectInteger, that.finalObjectInteger) &&
                Objects.equals(finalObjectLong, that.finalObjectLong) &&
                Objects.equals(finalObjectFloat, that.finalObjectFloat) &&
                Objects.equals(finalObjectDouble, that.finalObjectDouble) &&
                Objects.equals(finalObjectBoolean, that.finalObjectBoolean) &&
                Objects.equals(finalObjectByte, that.finalObjectByte) &&
                Objects.equals(finalObjectShort, that.finalObjectShort) &&
                Objects.equals(finalObjectCharacter, that.finalObjectCharacter) &&
                finalEnum == that.finalEnum &&
                Objects.equals(finalIntegerList, that.finalIntegerList) &&
                Objects.equals(mutableParcelable, that.mutableParcelable) &&
                Objects.equals(mutableString, that.mutableString) &&
                Objects.equals(mutableObjectInteger, that.mutableObjectInteger) &&
                Objects.equals(mutableObjectLong, that.mutableObjectLong) &&
                Objects.equals(mutableObjectFloat, that.mutableObjectFloat) &&
                Objects.equals(mutableObjectDouble, that.mutableObjectDouble) &&
                Objects.equals(mutableObjectBoolean, that.mutableObjectBoolean) &&
                Objects.equals(mutableObjectByte, that.mutableObjectByte) &&
                Objects.equals(mutableObjectShort, that.mutableObjectShort) &&
                Objects.equals(mutableObjectCharacter, that.mutableObjectCharacter) &&
                mutableEnum == that.mutableEnum &&
                Objects.equals(mutableIntegerList, that.mutableIntegerList) &&
                Objects.equals(finalBigDecimalString, that.finalBigDecimalString) &&
                finalDateLong == that.finalDateLong &&
                Objects.equals(finalNumber, that.finalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalParcelable, finalString, finalPrimitiveInt, finalObjectInteger, finalPrimitiveLong, finalObjectLong,
                finalPrimitiveFloat, finalObjectFloat, finalPrimitiveDouble, finalObjectDouble, finalPrimitiveBoolean, finalObjectBoolean,
                finalPrimitiveByte, finalObjectByte, finalPrimitiveShort, finalObjectShort, finalPrimitiveChar, finalObjectCharacter, finalEnum,
                finalIntegerList, mutableParcelable, mutableString, mutablePrimitiveInt, mutableObjectInteger, mutablePrimitiveLong,
                mutableObjectLong, mutablePrimitiveFloat, mutableObjectFloat, mutablePrimitiveDouble, mutableObjectDouble, mutablePrimitiveBoolean,
                mutableObjectBoolean, mutablePrimitiveByte, mutableObjectByte, mutablePrimitiveShort, mutableObjectShort, mutablePrimitiveChar,
                mutableObjectCharacter, mutableEnum, mutableIntegerList, finalBigDecimalString, finalDateLong, finalNumber);
    }

    void setMutableParcelable(TestMemberParcelable mutableParcelable) {
        this.mutableParcelable = mutableParcelable;
    }

    void setMutableString(String mutableString) {
        this.mutableString = mutableString;
    }

    void setMutablePrimitiveInt(int mutablePrimitiveInt) {
        this.mutablePrimitiveInt = mutablePrimitiveInt;
    }

    void setMutableObjectInteger(@NonNull Integer mutableObjectInteger) {
        this.mutableObjectInteger = mutableObjectInteger;
    }

    void setMutablePrimitiveLong(long mutablePrimitiveLong) {
        this.mutablePrimitiveLong = mutablePrimitiveLong;
    }

    void setMutableObjectLong(@NonNull Long mutableObjectLong) {
        this.mutableObjectLong = mutableObjectLong;
    }

    void setMutablePrimitiveFloat(float mutablePrimitiveFloat) {
        this.mutablePrimitiveFloat = mutablePrimitiveFloat;
    }

    void setMutableObjectFloat(@NonNull Float mutableObjectFloat) {
        this.mutableObjectFloat = mutableObjectFloat;
    }

    void setMutablePrimitiveDouble(double mutablePrimitiveDouble) {
        this.mutablePrimitiveDouble = mutablePrimitiveDouble;
    }

    void setMutableObjectDouble(@NonNull Double mutableObjectDouble) {
        this.mutableObjectDouble = mutableObjectDouble;
    }

    void setMutablePrimitiveBoolean(boolean mutablePrimitiveBoolean) {
        this.mutablePrimitiveBoolean = mutablePrimitiveBoolean;
    }

    void setMutableObjectBoolean(@NonNull Boolean mutableObjectBoolean) {
        this.mutableObjectBoolean = mutableObjectBoolean;
    }

    void setMutablePrimitiveByte(byte mutablePrimitiveByte) {
        this.mutablePrimitiveByte = mutablePrimitiveByte;
    }

    void setMutableObjectByte(@NonNull Byte mutableObjectByte) {
        this.mutableObjectByte = mutableObjectByte;
    }

    void setMutablePrimitiveShort(short mutablePrimitiveShort) {
        this.mutablePrimitiveShort = mutablePrimitiveShort;
    }

    void setMutableObjectShort(@NonNull Short mutableObjectShort) {
        this.mutableObjectShort = mutableObjectShort;
    }

    void setMutablePrimitiveChar(char mutablePrimitiveChar) {
        this.mutablePrimitiveChar = mutablePrimitiveChar;
    }

    void setMutableObjectCharacter(@NonNull Character mutableObjectCharacter) {
        this.mutableObjectCharacter = mutableObjectCharacter;
    }

    void setMutableEnum(TestEnum mutableEnum) {
        this.mutableEnum = mutableEnum;
    }

    void setMutableIntegerList(List<Integer> mutableIntegerList) {
        this.mutableIntegerList = mutableIntegerList;
    }

    //region Parcel helpers
    private static boolean readBoolean(Parcel in) {
        return in.readInt() == 1;
    }

    private static void writeBoolean(Parcel dest, boolean bool) {
        dest.writeInt(bool ? 1 : 0);
    }

    private static char readChar(Parcel in) {
        return in.readString().charAt(0);
    }

    private static void writeChar(Parcel dest, char character) {
        dest.writeString(String.valueOf(character));
    }

    private static <E extends Enum<E>> E readEnum(Parcel in, Class<E> enumType) {
        final String name = in.readString();
        return name == null ? null : E.valueOf(enumType, name);
    }

    private static <E extends Enum<E>> void writeEnum(Parcel dest, E value) {
        dest.writeString(value == null ? null : value.name());
    }
    //endregion
}
