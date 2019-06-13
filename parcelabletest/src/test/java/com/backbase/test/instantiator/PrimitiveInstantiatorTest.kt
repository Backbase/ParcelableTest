package com.backbase.test.instantiator

import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class PrimitiveInstantiatorTest : BasePrimitiveInstantiatorTest() {

    @Mock lateinit var mockBooleanInstantiator: Instantiator<Boolean>
    @Mock lateinit var mockByteInstantiator: Instantiator<Byte>
    @Mock lateinit var mockShortInstantiator: Instantiator<Short>
    @Mock lateinit var mockIntInstantiator: Instantiator<Int>
    @Mock lateinit var mockLongInstantiator: Instantiator<Long>
    @Mock lateinit var mockFloatInstantiator: Instantiator<Float>
    @Mock lateinit var mockDoubleInstantiator: Instantiator<Double>
    @Mock lateinit var mockCharInstantiator: Instantiator<Char>
    @Mock lateinit var mockStringInstantiator: Instantiator<String>

    override lateinit var instantiator: PrimitiveInstantiator

    @Before
    fun setUp() {
        whenever(mockBooleanInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_BOOLEAN)
        whenever(mockByteInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_BYTE)
        whenever(mockShortInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_SHORT)
        whenever(mockIntInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_INT)
        whenever(mockLongInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_LONG)
        whenever(mockFloatInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_FLOAT)
        whenever(mockDoubleInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_DOUBLE)
        whenever(mockCharInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_CHAR)
        whenever(mockStringInstantiator.instantiate()).thenReturn(TestPrimitives.TEST_STRING)

        instantiator = PrimitiveInstantiator(
            mockBooleanInstantiator,
            mockByteInstantiator,
            mockShortInstantiator,
            mockIntInstantiator,
            mockLongInstantiator,
            mockFloatInstantiator,
            mockDoubleInstantiator,
            mockCharInstantiator,
            mockStringInstantiator
        )
    }
}
