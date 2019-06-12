package com.backbase.test.instantiator.random

import com.backbase.test.instantiator.EnumInstantiator
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
class RandomEnumInstantiator(
    private val random: Random
) : EnumInstantiator() {

    override fun <E : Enum<E>?> safelyInstantiate(enumType: Class<E>): E {
        val values = enumType.enumConstants
        return values[random.nextInt(values.size)]
    }

}
