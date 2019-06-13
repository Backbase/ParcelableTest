package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * An item that supports instantiating objects of a parametrized type.
 */
interface Instantiator<T> {

    val supportedType: Class<T>

    fun instantiate(): T
}
