package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * A collection of multiple [Instantiator]s that can functions as a single [MultiTypeInstantiator]. If multiple [Instantiator]s provided during
 * construction support the same type, only the first one will be used.
 */
open class CompositeInstantiator(
    vararg instantiators: Instantiator<*>
) : MultiTypeInstantiator {

    constructor(instantiators: List<Instantiator<*>>) : this(*instantiators.toTypedArray())

    private val instantiatorsByType: Map<Class<*>, Instantiator<*>>

    init {
        val instantiatorsByType: MutableMap<Class<*>, Instantiator<*>> = mutableMapOf()
        for (instantiator in instantiators) {
            val type = instantiator.supportedClass()
            if (!instantiatorsByType.contains(type))
                instantiatorsByType[type] = instantiator
        }
        this.instantiatorsByType = instantiatorsByType
    }

    final override fun supports(type: Class<*>) = instantiatorsByType.contains(type)

    final override fun <T> instantiate(type: Class<T>): T {
        val instantiator = instantiatorsByType[type] ?: error("Type $type is not supported by this ${javaClass.simpleName}")
        @Suppress("UNCHECKED_CAST") return instantiator.instantiate() as T
    }
}
