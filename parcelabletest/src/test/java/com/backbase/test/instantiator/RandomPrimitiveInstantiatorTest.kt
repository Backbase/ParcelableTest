package com.backbase.test.instantiator

import com.backbase.test.parcelable.TestEnum
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.junit.MockitoJUnitRunner
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class RandomPrimitiveInstantiatorTest : BasePrimitiveInstantiatorTest() {

    private val expectedStringMaxLength = 50

    override val testByte = TestPrimitives.TEST_CHAR.toByte()

    @Mock private lateinit var mockRandom: Random

    override lateinit var instantiator: PrimitiveInstantiator

    @Before
    fun setUp() {
        instantiator = PrimitiveInstantiator(mockRandom)

        whenever(mockRandom.nextBoolean()).thenReturn(TestPrimitives.TEST_BOOLEAN)
        whenever(mockRandom.nextInt(Short.MAX_VALUE.toInt())).thenReturn(TestPrimitives.TEST_SHORT.toInt())
        whenever(mockRandom.nextInt()).thenReturn(TestPrimitives.TEST_INT)
        whenever(mockRandom.nextLong()).thenReturn(TestPrimitives.TEST_LONG)
        whenever(mockRandom.nextFloat()).thenReturn(TestPrimitives.TEST_FLOAT)
        whenever(mockRandom.nextDouble()).thenReturn(TestPrimitives.TEST_DOUBLE)
        whenever(mockRandom.nextInt(TestEnum.values().size)).thenReturn(0)

        // Bytes, chars, and strings all use nextBytes:
        whenever(mockRandom.nextInt(expectedStringMaxLength)).thenReturn(TestPrimitives.TEST_STRING.length)
        whenever(mockRandom.nextBytes(any())).thenAnswer {
            val byteArray: ByteArray = it.getArgument(0)
            if (byteArray.size == 1)
                byteArray[0] = testByte
            else for (i in byteArray.indices) {
                byteArray[i] = TestPrimitives.TEST_STRING[i].toByte()
            }
            null
        }
    }
}
