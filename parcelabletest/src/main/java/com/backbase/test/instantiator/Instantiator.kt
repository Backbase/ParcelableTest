package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * An item that supports instantiating objects of a parametrized type.
 */
interface Instantiator<T> {

    /**
     * The type that this instantiator can instantiate. By default, uses Java generic interface reflection. Must be overridden if the type is not
     * concrete in direct implementers of this interface.
     */
    @JvmDefault val supportedType get(): Class<T> {
        val actualType = javaClass.findGenericInterfaceTypeArgument(Instantiator::class.java) { "supportedType must be implemented for $javaClass" }
        @Suppress("UNCHECKED_CAST")
        return actualType as Class<T>
    }

    fun instantiate(): T
}
