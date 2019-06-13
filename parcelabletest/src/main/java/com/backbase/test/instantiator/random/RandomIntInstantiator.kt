package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a int via the provided [Random] instance.
 */
class RandomIntInstantiator(random: Random) : RandomInstantiator<Int>(random) {

    override val supportedType = Int::class.java

    override fun instantiate(random: Random) = random.nextInt()
}
