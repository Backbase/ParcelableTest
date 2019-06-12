package com.backbase.test.instantiator;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * A {@link MultiTypeInstantiator} that supports any enum type.
 */
public abstract class EnumInstantiator implements MultiTypeInstantiator {

    @Override
    public final boolean supports(@NotNull Class<?> type) {
        return Enum.class.isAssignableFrom(type);
    }

    @Override
    public final <T> T instantiate(@NotNull Class<T> type) {
        if (supports(type)) {
            //noinspection unchecked
            return (T) safelyInstantiate((Class<Enum>) type);
        } else {
            throw new IllegalArgumentException("");
        }
    }

    protected abstract <E extends Enum<E>> E safelyInstantiate(@NonNull Class<E> enumType);
}
