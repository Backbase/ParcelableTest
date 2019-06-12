package com.backbase.test.instantiator

import com.nhaarman.mockitokotlin2.whenever
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class RandomDateInstantiatorTest : RandomInstantiatorTest<Date>() {

    override val supportedType: Class<Date> = Date::class.java

    override val expectedInstance = Date(TestPrimitives.TEST_LONG)

    override fun newInstantiator(random: Random) = RandomDateInstantiator(random)

    override fun mockRandom(mockRandom: Random) {
        whenever(mockRandom.nextLong()).thenReturn(TestPrimitives.TEST_LONG)
    }
}
