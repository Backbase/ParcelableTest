package com.backbase.test.instantiator.random

import java.math.BigDecimal
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
class RandomBigDecimalInstantiator(random: Random) : RandomInstantiator<BigDecimal>(random) {

    override val supportedType = BigDecimal::class.java

    override fun instantiate(random: Random) = BigDecimal(random.nextInt())
}
