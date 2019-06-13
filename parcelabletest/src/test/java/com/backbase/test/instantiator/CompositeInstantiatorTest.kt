package com.backbase.test.instantiator

import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.Arrays

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class CompositeInstantiatorTest {

    @Mock private lateinit var instantiator1: Instantiator<String>
    @Mock private lateinit var instantiator2: Instantiator<Int>

    private lateinit var instantiator: CompositeInstantiator

    @Before
    fun setUp() {
        whenever(instantiator1.supportedType).thenReturn(String::class.java)
        whenever(instantiator2.supportedType).thenReturn(Int::class.java)
        instantiator = CompositeInstantiator(instantiator1, instantiator2)
    }

    @Test
    fun `supports with any supporting Instantiator returns true`() {
        val testClass = Int::class.java

        assertTrue(instantiator.supports(testClass))
    }

    @Test
    fun `supports with no supporting Instantiator returns false`() {
        val testClass = Short::class.java

        assertFalse(instantiator.supports(testClass))
    }

    @Test
    fun `supports with list constructor and supporting Instantiator returns true`() {
        instantiator = CompositeInstantiator(Arrays.asList(instantiator1, instantiator2))

        val testClass = Int::class.java

        assertTrue(instantiator.supports(testClass))
    }

    @Test
    fun `instantiateSupportedType with 1 supporting Instantiator returns instance`() {
        val testClass = Int::class.java
        whenever(instantiator2.instantiate()).thenReturn(2)

        assertEquals(2, instantiator.instantiate(testClass))
    }

    @Test(expected = IllegalStateException::class)
    fun `instantiateSupportedType with no supporting Instantiator throws IllegalStateException`() {
        val testClass = Short::class.java

        instantiator.instantiate(testClass)
    }

    @Test
    fun `instantiateSupportedType with list constructor and 1 supporting Instantiator returns instance`() {
        instantiator = CompositeInstantiator(Arrays.asList(instantiator1, instantiator2))

        val testClass = Int::class.java
        whenever(instantiator2.instantiate()).thenReturn(2)

        assertEquals(2, instantiator.instantiate(testClass))
    }
}
