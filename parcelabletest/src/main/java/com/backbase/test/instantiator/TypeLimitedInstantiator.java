package com.backbase.test.instantiator;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 *
 * An {@link OldInstantiator} that throws an exception in {@link #instantiate(Class)} if the given
 * {@link Class} is not supported.
 */
@Deprecated
public abstract class TypeLimitedInstantiator implements OldInstantiator {

    @Override
    public final <O> O instantiate(@NonNull Class<O> objectClass) {
        if (!supports(objectClass)) {
            throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support instantiating " + objectClass.getName());
        }

        return instantiateSupportedType(objectClass);
    }

    protected abstract <O> O instantiateSupportedType(@NonNull Class<O> objectClass);
}
