package com.backbase.test.instantiator;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 10/12/2018.
 *
 * An item that supports instantiating objects given an input class type.
 */
public interface Instantiator {

    boolean supports(@NonNull Class<?> objectClass);

    <O> O instantiate(@NonNull Class<O> objectClass);
}
