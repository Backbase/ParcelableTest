package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-13.
 * A collection of multiple [MultiTypeInstantiator]s that can function as a single [MultiTypeInstantiator]. If multiple [MultiTypeInstantiator]s
 * provided during construction support the same type, only the first one will be used.
 */
internal class CompositeMultiTypeInstantiator(
    private vararg val instantiators: MultiTypeInstantiator
) : MultiTypeInstantiator {

    constructor(instantiators: List<MultiTypeInstantiator>) : this(*instantiators.toTypedArray())

    override fun supports(type: Class<*>) = getSupportingInstantiator(type) != null

    override fun <T> instantiate(type: Class<T>): T {
        val instantiator = getSupportingInstantiator(type)
        if (instantiator == null) {
            throw IllegalArgumentException("${javaClass.simpleName} does not support type $type")
        } else {
            return instantiator.instantiate(type)
        }
    }

    private fun getSupportingInstantiator(type: Class<*>): MultiTypeInstantiator? {
        for (instantiator in instantiators) {
            if (instantiator.supports(type)) return instantiator
        }
        return null
    }
}
