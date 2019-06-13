package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a float via the provided [Random] instance.
 */
class RandomFloatInstantiator(random: Random) : RandomInstantiator<Float>(random) {

    override fun instantiate(random: Random) = random.nextFloat()

    override fun supportedType() = Float::class.java
}
