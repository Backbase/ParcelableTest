package com.backbase.test.instantiator.random

import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a byte via the provided [Random] instance.
 */
class RandomByteInstantiator(random: Random) : RandomInstantiator<Byte>(random) {

    override val supportedType = Byte::class.java

    override fun instantiate(random: Random): Byte {
        val byteArray = ByteArray(1)
        random.nextBytes(byteArray)
        return byteArray[0]
    }
}
