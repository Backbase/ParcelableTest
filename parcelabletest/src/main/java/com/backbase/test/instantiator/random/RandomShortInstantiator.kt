package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a short via the provided [Random] instance.
 */
class RandomShortInstantiator(random: Random) : RandomInstantiator<Short>(random) {

    override fun instantiate(random: Random): Short {
        return random.nextInt(Short.MAX_VALUE.toInt()).toShort()
    }

    override fun supportedType(): Class<Short> {
        return Short::class.java
    }
}
