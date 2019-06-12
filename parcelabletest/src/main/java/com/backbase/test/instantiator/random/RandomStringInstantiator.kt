package com.backbase.test.instantiator.random

import java.nio.charset.Charset
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates a string via the provided [Random] instance.
 */
class RandomStringInstantiator(
    random: Random,
    private val maxLength: Int
) : RandomInstantiator<String>(random) {

    // Manual overload needed for Java visibility:
    constructor(random: Random) : this(random, 50)

    override fun instantiate(random: Random): String {
        val length = random.nextInt(maxLength)
        val byteArray = ByteArray(length)
        random.nextBytes(byteArray)
        return String(byteArray, Charset.defaultCharset())
    }

    override fun supportedClass(): Class<String> {
        return String::class.java
    }
}
