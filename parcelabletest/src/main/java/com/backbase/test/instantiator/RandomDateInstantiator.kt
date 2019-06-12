package com.backbase.test.instantiator

import java.util.Date
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
class RandomDateInstantiator(random: Random) : RandomInstantiator<Date>(random) {

    override fun instantiate(random: Random) = Date(random.nextLong())

    override fun supportedClass() = Date::class.java
}
