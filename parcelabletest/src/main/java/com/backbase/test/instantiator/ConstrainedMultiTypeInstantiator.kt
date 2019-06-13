package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-13.
 * A [MultiTypeInstantiator] that supports a finite number of types.
 */
interface ConstrainedMultiTypeInstantiator : MultiTypeInstantiator {

    val supportedTypes: Collection<Class<*>>

    @JvmDefault override fun supports(type: Class<*>) = supportedTypes.contains(type)
}
