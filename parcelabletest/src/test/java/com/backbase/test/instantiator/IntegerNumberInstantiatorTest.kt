package com.backbase.test.instantiator

import com.nhaarman.mockitokotlin2.whenever
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class IntegerNumberInstantiatorTest : RandomTypeLimitedInstantiatorTest<IntegerNumberInstantiator, Number>() {

    override val supportedType: Class<Number> = Number::class.java

    override val expectedRandomInstance = TestPrimitives.TEST_INT
    override val expectedFallbackInstance = 1

    override fun newInstantiator() = IntegerNumberInstantiator()
    override fun newInstantiator(random: Random) = IntegerNumberInstantiator(random)

    override fun mockRandom(mockRandom: Random) {
        whenever(mockRandom.nextInt()).thenReturn(TestPrimitives.TEST_INT)
    }
}
