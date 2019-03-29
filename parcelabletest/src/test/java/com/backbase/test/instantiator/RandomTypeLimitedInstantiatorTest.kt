package com.backbase.test.instantiator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 28/01/2019.
 */
@RunWith(MockitoJUnitRunner::class)
abstract class RandomTypeLimitedInstantiatorTest<I : RandomTypeLimitedInstantiator, T> {

    protected abstract val supportedType: Class<T>

    protected abstract val expectedRandomInstance: T
    protected abstract val expectedFallbackInstance: T

    protected abstract fun newInstantiator(): I
    protected abstract fun newInstantiator(random: Random): I

    protected open fun mockRandom(mockRandom: Random) {
        // Hook
    }

    @Mock private lateinit var mockRandom: Random

    private lateinit var instantiator: I

    @Before
    fun setUp() {
        instantiator = newInstantiator()
        mockRandom(mockRandom)
    }

    @Test
    fun `supports supportedType returns true`() {
        assertTrue(instantiator.supports(supportedType))
    }

    @Test
    fun `supports other classes returns false`() {
        assertFalse(instantiator.supports(RandomTypeLimitedInstantiatorTest::class.java))
    }

    @Test
    fun `instantiate with Random and supported type returns Random-generated instance of that type`() {
        instantiator = newInstantiator(mockRandom)
        assertEquals(expectedRandomInstance, instantiator.instantiate(supportedType))
    }

    @Test
    fun `instantiate with null Random and supported type returns fallback instance of that type`() {
        assertEquals(expectedFallbackInstance, instantiator.instantiate(supportedType))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `instantiate with unsupported type throws UnsupportedOperationException`() {
        instantiator.instantiate(RandomTypeLimitedInstantiatorTest::class.java)
    }
}
