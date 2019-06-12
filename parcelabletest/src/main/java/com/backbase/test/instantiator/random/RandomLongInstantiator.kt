package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a long via the provided [Random] instance.
 */
class RandomLongInstantiator(random: Random) : RandomInstantiator<Long>(random) {

    override fun instantiate(random: Random) = random.nextLong()

    override fun supportedClass() = Long::class.java
}
