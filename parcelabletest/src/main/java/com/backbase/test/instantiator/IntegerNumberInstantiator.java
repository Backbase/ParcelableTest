package com.backbase.test.instantiator;

import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 12/12/2018.
 */
public final class IntegerNumberInstantiator extends RandomTypeLimitedInstantiator {

    public IntegerNumberInstantiator() {
        this(null);
    }

    public IntegerNumberInstantiator(Random random) {
        super(random);
    }

    @Override
    public boolean supports(@NonNull Class<?> objectClass) {
        return objectClass == Number.class;
    }

    @Override
    protected <O> O instantiateRandomObject(@NonNull Class<O> objectClass, @NonNull Random random) {
        //noinspection unchecked
        return (O) (Number) random.nextInt();
    }

    @Override
    protected <O> O instantiateFallbackObject(@NonNull Class<O> objectClass) {
        //noinspection unchecked
        return (O) (Number) 1;
    }
}
