package com.backbase.test.instantiator;

import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 * A {@link TypeLimitedInstantiator} that uses a {@link Random} instance to instantiate
 * supported types. The provided {@link Random} can be null, in which case the fallback
 * method will be used.
 */
public abstract class RandomTypeLimitedInstantiator extends TypeLimitedInstantiator {

    private Random random;

    public RandomTypeLimitedInstantiator(Random random) {
        this.random = random;
    }

    @Override
    protected final <O> O instantiateSupportedType(@NonNull Class<O> objectClass) {
        if (random == null) {
            return instantiateFallbackObject(objectClass);
        } else {
            return instantiateRandomObject(objectClass, random);
        }
    }

    protected abstract <O> O instantiateRandomObject(@NonNull Class<O> objectClass, @NonNull Random random);

    protected abstract <O> O instantiateFallbackObject(@NonNull Class<O> objectClass);
}
