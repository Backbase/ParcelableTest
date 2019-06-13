package com.backbase.test.instantiator;

import com.backbase.test.instantiator.random.RandomBooleanInstantiator;
import com.backbase.test.instantiator.random.RandomByteInstantiator;
import com.backbase.test.instantiator.random.RandomCharInstantiator;
import com.backbase.test.instantiator.random.RandomDoubleInstantiator;
import com.backbase.test.instantiator.random.RandomFloatInstantiator;
import com.backbase.test.instantiator.random.RandomIntInstantiator;
import com.backbase.test.instantiator.random.RandomLongInstantiator;
import com.backbase.test.instantiator.random.RandomShortInstantiator;
import com.backbase.test.instantiator.random.RandomStringInstantiator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * A {@link MultiTypeInstantiator} that supports all Java primitive types, their boxed counterparts, and Strings. Support is provided via individual
 * constructor {@link Instantiator}s.
 */
public final class PrimitiveInstantiator implements ConstrainedMultiTypeInstantiator {

    private static final Set<Class<?>> SUPPORTED_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            boolean.class,
            Boolean.class,
            byte.class,
            Byte.class,
            short.class,
            Short.class,
            int.class,
            Integer.class,
            long.class,
            Long.class,
            float.class,
            Float.class,
            double.class,
            Double.class,
            char.class,
            Character.class,
            String.class
    )));

    private final Instantiator<Boolean> booleanInstantiator;
    private final Instantiator<Byte> byteInstantiator;
    private final Instantiator<Short> shortInstantiator;
    private final Instantiator<Integer> integerInstantiator;
    private final Instantiator<Long> longInstantiator;
    private final Instantiator<Float> floatInstantiator;
    private final Instantiator<Double> doubleInstantiator;
    private final Instantiator<Character> characterInstantiator;
    private final Instantiator<String> stringInstantiator;

    public static PrimitiveInstantiator ofRandom(Random random) {
        return new PrimitiveInstantiator(new RandomBooleanInstantiator(random), new RandomByteInstantiator(random),
                new RandomShortInstantiator(random), new RandomIntInstantiator(random), new RandomLongInstantiator(random),
                new RandomFloatInstantiator(random), new RandomDoubleInstantiator(random), new RandomCharInstantiator(random),
                new RandomStringInstantiator(random));
    }

    public PrimitiveInstantiator(Instantiator<Boolean> booleanInstantiator, Instantiator<Byte> byteInstantiator,
            Instantiator<Short> shortInstantiator, Instantiator<Integer> integerInstantiator, Instantiator<Long> longInstantiator,
            Instantiator<Float> floatInstantiator, Instantiator<Double> doubleInstantiator, Instantiator<Character> characterInstantiator,
            Instantiator<String> stringInstantiator) {
        this.booleanInstantiator = booleanInstantiator;
        this.byteInstantiator = byteInstantiator;
        this.shortInstantiator = shortInstantiator;
        this.integerInstantiator = integerInstantiator;
        this.longInstantiator = longInstantiator;
        this.floatInstantiator = floatInstantiator;
        this.doubleInstantiator = doubleInstantiator;
        this.characterInstantiator = characterInstantiator;
        this.stringInstantiator = stringInstantiator;
    }

    @NonNull
    @Override
    public Collection<Class<?>> getSupportedTypes() {
        return SUPPORTED_TYPES;
    }

    @Override
    public final <T> T instantiate(@NonNull Class<T> type) {
        if (isBooleanType(type)) {
            //noinspection unchecked
            return (T) booleanInstantiator.instantiate();
        } else if (isByteType(type)) {
            //noinspection unchecked
            return (T) byteInstantiator.instantiate();
        } else if (isShortType(type)) {
            //noinspection unchecked
            return (T) shortInstantiator.instantiate();
        } else if (isIntegerType(type)) {
            //noinspection unchecked
            return (T) integerInstantiator.instantiate();
        } else if (isLongType(type)) {
            //noinspection unchecked
            return (T) longInstantiator.instantiate();
        } else if (isFloatType(type)) {
            //noinspection unchecked
            return (T) floatInstantiator.instantiate();
        } else if (isDoubleType(type)) {
            //noinspection unchecked
            return (T) doubleInstantiator.instantiate();
        } else if (isCharacterType(type)) {
            //noinspection unchecked
            return (T) characterInstantiator.instantiate();
        } else if (isStringType(type)) {
            //noinspection unchecked
            return (T) stringInstantiator.instantiate();
        } else {
            throw new IllegalArgumentException(getClass().getSimpleName() + " does not support type " + type);
        }
    }

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
}
