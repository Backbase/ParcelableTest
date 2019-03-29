package com.backbase.test.instantiator;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 * An {@link Instantiator} that instantiates an {@link ArrayList} given any
 * superclass of {@link ArrayList}.
 */
public abstract class ArrayListInstantiator extends TypeLimitedInstantiator {

    @Override
    public final boolean supports(@NonNull Class<?> objectClass) {
        return isArrayListAbstractSuperclass(objectClass);
    }

    @Override
    protected <O> O instantiateSupportedType(@NonNull Class<O> objectClass) {
        final ArrayList arrayList = new ArrayList();
        populateArrayList(arrayList);
        //noinspection unchecked
        return (O) arrayList;
    }

    protected abstract <T> void populateArrayList(@NonNull ArrayList<T> list);

    private static boolean isArrayListAbstractSuperclass(Class<?> objectClass) {
        return objectClass == Iterable.class
                || objectClass == Collection.class
                || objectClass == List.class
                || objectClass == AbstractCollection.class
                || objectClass == AbstractList.class;
    }
}
