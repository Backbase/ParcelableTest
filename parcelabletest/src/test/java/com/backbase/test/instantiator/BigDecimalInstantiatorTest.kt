package com.backbase.test.instantiator

import com.nhaarman.mockitokotlin2.whenever
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class BigDecimalInstantiatorTest : RandomTypeLimitedInstantiatorTest<BigDecimalInstantiator, BigDecimal>() {

    override val supportedType: Class<BigDecimal> = BigDecimal::class.java

    override val expectedRandomInstance = BigDecimal(TestPrimitives.TEST_INT)
    override val expectedFallbackInstance = BigDecimal(1)

    override fun newInstantiator() = BigDecimalInstantiator()
    override fun newInstantiator(random: Random) = BigDecimalInstantiator(random)

    override fun mockRandom(mockRandom: Random) {
        whenever(mockRandom.nextInt()).thenReturn(TestPrimitives.TEST_INT)
    }
}
