package com.backbase.test.instantiator.random

import com.backbase.test.instantiator.Instantiator
import com.backbase.test.findGenericSuperclassTypeArgument
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
abstract class RandomInstantiator<T>(
    private val random: Random
) : Instantiator<T> {

    /**
     * The type that this instantiator can instantiate. By default, uses Java generic superclass reflection. Must be overridden if the type is not
     * concrete in direct implementers of this class.
     */
    override val supportedType get(): Class<T> {
        val actualType = javaClass.findGenericSuperclassTypeArgument { "supportedType must be overridden for $javaClass" }
        @Suppress("UNCHECKED_CAST")
        return actualType as Class<T>
    }

    final override fun instantiate() = instantiate(random)

    abstract fun instantiate(random: Random): T
}
