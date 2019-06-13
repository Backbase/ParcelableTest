package com.backbase.test.instantiator

internal fun Array<Instantiator<*>>.toMultiTypeInstantiators() = Array(size) {
    this[it].toMultiTypeInstantiator()
}

internal fun Instantiator<*>.toMultiTypeInstantiator(): MultiTypeInstantiator = CompositeInstantiator(this)
