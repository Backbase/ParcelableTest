package com.backbase.test.instantiator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 * An {@link OldInstantiator} that delegates to a list of {@link OldInstantiator}s.
 * If multiple delegates support the same type, the first one in the list is
 * used.
 */
@Deprecated
public final class OldCompositeInstantiator extends TypeLimitedInstantiator {

    @NonNull private final List<OldInstantiator> delegates;

    public OldCompositeInstantiator(@NonNull List<OldInstantiator> delegates) {
        this.delegates = Collections.unmodifiableList(delegates);
    }

    public OldCompositeInstantiator(@NonNull OldInstantiator... delegates) {
        this(Arrays.asList(delegates));
    }

    @Override
    public boolean supports(@NonNull Class<?> objectClass) {
        for (OldInstantiator delegate : delegates) {
            if (delegate.supports(objectClass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected <O> O instantiateSupportedType(@NonNull Class<O> objectClass) {
        for (OldInstantiator delegate : delegates) {
            if (delegate.supports(objectClass)) {
                return delegate.instantiate(objectClass);
            }
        }
        throw new IllegalStateException("Expected a supported type, but received " + objectClass.getName());
    }
}
