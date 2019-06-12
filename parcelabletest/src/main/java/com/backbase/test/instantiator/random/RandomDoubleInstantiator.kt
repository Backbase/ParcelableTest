package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a double via the provided [Random] instance.
 */
class RandomDoubleInstantiator(random: Random) : RandomInstantiator<Double>(random) {

    override fun instantiate(random: Random) = random.nextDouble()

    override fun supportedClass() = Double::class.java
}
