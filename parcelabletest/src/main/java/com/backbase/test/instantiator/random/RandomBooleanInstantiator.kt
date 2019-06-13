package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a boolean via the provided [Random] instance.
 */
class RandomBooleanInstantiator(random: Random) : RandomInstantiator<Boolean>(random) {

    override fun instantiate(random: Random) = random.nextBoolean()
}
