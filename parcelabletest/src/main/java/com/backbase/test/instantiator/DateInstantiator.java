package com.backbase.test.instantiator;

import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 */
public final class DateInstantiator extends RandomTypeLimitedInstantiator {

    public DateInstantiator() {
        super(null);
    }

    public DateInstantiator(Random random) {
        super(random);
    }

    @Override
    public boolean supports(@NonNull Class<?> objectClass) {
        return objectClass == Date.class;
    }

    @Override
    protected <O> O instantiateRandomObject(@NonNull Class<O> objectClass, @NonNull Random random) {
        // noinspection unchecked
        return (O) new Date(random.nextLong());
    }

    @Override
    protected <O> O instantiateFallbackObject(@NonNull Class<O> objectClass) {
        // noinspection unchecked
        return (O) new Date(1L);
    }
}
