package com.backbase.test.parcelable.reflection

import com.backbase.test.instantiator.MultiTypeInstantiator
import com.backbase.test.instantiator.PrimitiveInstantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-13.
 * A wrapper for [ReflectiveInstantiator] that first attempts to instantiate known problem classes via non-reflection using [preferredInstantiator]
 * and [primitiveInstantiator].
 */
internal class RobustReflectiveInstantiator(
    private val preferredInstantiator: MultiTypeInstantiator,
    private val primitiveInstantiator: PrimitiveInstantiator,
    private val reflectiveInstantiator: ReflectiveInstantiator
) : MultiTypeInstantiator {

    override fun supports(type: Class<*>) =
            preferredInstantiator.supports(type) || primitiveInstantiator.supports(type) || reflectiveInstantiator.supports(type)

    override fun <T> instantiate(type: Class<T>) = when {
        preferredInstantiator.supports(type) -> preferredInstantiator.instantiate(type)
        primitiveInstantiator.supports(type) -> primitiveInstantiator.instantiate(type)
        else -> reflectiveInstantiator.instantiate(type)
    }
}
