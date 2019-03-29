package com.backbase.test.instantiator;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 *
 * A specialized {@link Instantiator} that supports primitives, strings, and enums.
 */
public abstract class PrimitiveInstantiator extends TypeLimitedInstantiator {

    @Override
    public final boolean supports(@NonNull Class<?> objectClass) {
        return isBooleanType(objectClass)
                || isByteType(objectClass)
                || isShortType(objectClass)
                || isIntegerType(objectClass)
                || isLongType(objectClass)
                || isFloatType(objectClass)
                || isDoubleType(objectClass)
                || isCharacterType(objectClass)
                || isStringType(objectClass)
                || isEnumType(objectClass);
    }

    @Override
    public final <O> O instantiateSupportedType(@NonNull Class<O> objectClass) {
        if (isBooleanType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateBoolean();
        } else if (isByteType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateByte();
        } else if (isShortType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateShort();
        } else if (isIntegerType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateInteger();
        } else if (isLongType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateLong();
        } else if (isFloatType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateFloat();
        } else if (isDoubleType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateDouble();
        } else if (isCharacterType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateCharacter();
        } else if (isStringType(objectClass)) {
            //noinspection unchecked
            return (O) instantiateString();
        } else if (isEnumType(objectClass)) {
            //noinspection unchecked
            return (O) provideEnum((Class<Enum>) objectClass);
        } else {
            throw new IllegalStateException("Expected a supported type, by got " + objectClass.getName() + " instead");
        }
    }

    @NonNull
    protected abstract Boolean instantiateBoolean();

    @NonNull
    protected abstract Byte instantiateByte();

    @NonNull
    protected abstract Short instantiateShort();

    @NonNull
    protected abstract Integer instantiateInteger();

    @NonNull
    protected abstract Long instantiateLong();

    @NonNull
    protected abstract Float instantiateFloat();

    @NonNull
    protected abstract Double instantiateDouble();

    @NonNull
    protected abstract Character instantiateCharacter();

    @NonNull
    protected abstract String instantiateString();

    @NonNull
    protected abstract <E extends Enum<E>> E provideEnum(@NonNull Class<E> enumType);

    private static boolean isBooleanType(Class<?> objectClass) {
        return objectClass == boolean.class || objectClass == Boolean.class;
    }

    private static boolean isByteType(Class<?> objectClass) {
        return objectClass == byte.class || objectClass == Byte.class;
    }

    private static boolean isShortType(Class<?> objectClass) {
        return objectClass == short.class || objectClass == Short.class;
    }

    private static boolean isIntegerType(Class<?> objectClass) {
        return objectClass == int.class || objectClass == Integer.class;
    }

    private static boolean isLongType(Class<?> objectClass) {
        return objectClass == long.class || objectClass == Long.class;
    }

    private static boolean isFloatType(Class<?> objectClass) {
        return objectClass == float.class || objectClass == Float.class;
    }

    private static boolean isDoubleType(Class<?> objectClass) {
        return objectClass == double.class || objectClass == Double.class;
    }

    private static boolean isCharacterType(Class<?> objectClass) {
        return objectClass == char.class || objectClass == Character.class;
    }

    private static boolean isStringType(Class<?> objectClass) {
        return objectClass == String.class;
    }

    private static boolean isEnumType(Class<?> objectClass) {
        return Enum.class.isAssignableFrom(objectClass);
    }
}
