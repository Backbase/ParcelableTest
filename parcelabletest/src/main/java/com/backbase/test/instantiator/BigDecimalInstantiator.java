package com.backbase.test.instantiator;

import java.math.BigDecimal;
import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 * Instantiates a {@link BigDecimal}. If a non-null {@link Random} is provided,
 * each instantiated {@link BigDecimal} is initialized with a random int.
 */
@Deprecated
public final class BigDecimalInstantiator extends RandomTypeLimitedInstantiator {

    public BigDecimalInstantiator() {
        super(null);
    }

    public BigDecimalInstantiator(Random random) {
        super(random);
    }

    @Override
    public boolean supports(@NonNull Class<?> objectClass) {
        return objectClass == BigDecimal.class;
    }

    @Override
    protected <O> O instantiateRandomObject(@NonNull Class<O> objectClass, @NonNull Random random) {
        // noinspection unchecked
        return (O) new BigDecimal(random.nextInt());
    }

    @Override
    protected <O> O instantiateFallbackObject(@NonNull Class<O> objectClass) {
        // noinspection unchecked
        return (O) new BigDecimal(1);
    }
}
