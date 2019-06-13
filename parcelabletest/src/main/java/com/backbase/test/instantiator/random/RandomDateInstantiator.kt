package com.backbase.test.instantiator.random

import java.util.Date
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
class RandomDateInstantiator(random: Random) : RandomInstantiator<Date>(random) {

    override val supportedType = Date::class.java

    override fun instantiate(random: Random) = Date(random.nextLong())
}
