package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a char via the provided [Random] instance.
 */
class RandomCharInstantiator(random: Random) : RandomInstantiator<Char>(random) {

    override fun instantiate(random: Random): Char {
        val byteArray = ByteArray(1)
        random.nextBytes(byteArray)
        return byteArray[0].toChar()
    }
}
