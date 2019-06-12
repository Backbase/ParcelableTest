package com.backbase.test.instantiator

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
abstract class RandomInstantiator<T>(
    private val random: Random
) : Instantiator<T> {

    final override fun instantiate() = instantiate(random)

    abstract fun instantiate(random: Random): T
}
