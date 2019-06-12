package com.backbase.test.instantiator

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class NumberInstantiatorTest : InstantiatorTest<Number>() {

    override val supportedType: Class<Number> = Number::class.java

    override val expectedInstance = TestPrimitives.TEST_INT

    @Mock private val mockIntInstantiator: Instantiator<Int> = mock()

    override fun newInstantiator() = NumberInstantiator(mockIntInstantiator)

    @Before
    fun mockIntInstantiator() {
        whenever(mockIntInstantiator.instantiate()).thenReturn(expectedInstance)
    }
}
