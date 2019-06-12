package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * An item that supports instantiating objects of a parametrized type.
 */
interface Instantiator<T> {

    fun supportedClass(): Class<T>

    fun instantiate(): T
}

// TODO Move to another file?
// TODO Determine if isAssignableFrom is used correctly
fun <O> Instantiator<*>.supports(type: Class<O>) = type.isAssignableFrom(supportedClass())
