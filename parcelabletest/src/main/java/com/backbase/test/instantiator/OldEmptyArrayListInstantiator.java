package com.backbase.test.instantiator;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 * Instantiates an empty {@link ArrayList} given an abstract superclass
 * of {@link ArrayList}
 */
@Deprecated
public final class OldEmptyArrayListInstantiator extends OldArrayListInstantiator {

    @Override
    protected <T> void populateArrayList(@NonNull ArrayList<T> list) {
        // no-op; leave list empty
    }
}
