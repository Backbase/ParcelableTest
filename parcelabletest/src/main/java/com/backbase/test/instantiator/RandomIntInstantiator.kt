package com.backbase.test.instantiator

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a int via the provided [Random] instance.
 */
class RandomIntInstantiator(random: Random) : RandomInstantiator<Int>(random) {

    override fun instantiate(random: Random) = random.nextInt()

    override fun supportedClass() = Int::class.java
}
