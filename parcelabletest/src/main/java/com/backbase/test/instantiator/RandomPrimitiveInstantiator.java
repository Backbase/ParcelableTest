package com.backbase.test.instantiator;

import java.nio.charset.Charset;
import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 *
 * Instantiates primitive types based on an instance of {@link java.util.Random}.
 */
public final class RandomPrimitiveInstantiator extends PrimitiveInstantiator {

    private final Random random;

    public RandomPrimitiveInstantiator(@NonNull Random random) {
        this.random = random;
    }

    @NonNull
    @Override
    protected Boolean instantiateBoolean() {
        return random.nextBoolean();
    }

    @NonNull
    @Override
    protected Byte instantiateByte() {
        final byte[] byteArray = new byte[1];
        random.nextBytes(byteArray);
        return byteArray[0];
    }

    @NonNull
    @Override
    protected Short instantiateShort() {
        return (short) random.nextInt(Short.MAX_VALUE);
    }

    @NonNull
    @Override
    protected Integer instantiateInteger() {
        return random.nextInt();
    }

    @NonNull
    @Override
    protected Long instantiateLong() {
        return random.nextLong();
    }

    @NonNull
    @Override
    protected Float instantiateFloat() {
        return random.nextFloat();
    }

    @NonNull
    @Override
    protected Double instantiateDouble() {
        return random.nextDouble();
    }

    @NonNull
    @Override
    protected Character instantiateCharacter() {
        return instantiateString(1).charAt(0);
    }

    @NonNull
    @Override
    protected String instantiateString() {
        final int maxLength = 50;
        return instantiateString(random.nextInt(maxLength));
    }

    @NonNull
    private String instantiateString(int length) {
        final byte[] byteArray = new byte[length];
        random.nextBytes(byteArray);
        return new String(byteArray, Charset.defaultCharset());
    }

    @NonNull
    @Override
    protected <E extends Enum<E>> E provideEnum(@NonNull Class<E> enumType) {
        final E[] values = enumType.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
