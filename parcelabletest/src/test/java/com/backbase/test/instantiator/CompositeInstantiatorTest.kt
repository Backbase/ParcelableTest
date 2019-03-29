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

    @Mock private lateinit var instantiator1: Instantiator
    @Mock private lateinit var instantiator2: Instantiator

    private lateinit var instantiator: CompositeInstantiator

    @Before
    fun setUp() {
        instantiator = CompositeInstantiator(instantiator1, instantiator2)
    }

    @Test
    fun `supports with any supporting Instantiator returns true`() {
        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(false)
        whenever(instantiator2.supports(testClass)).thenReturn(true)

        assertTrue(instantiator.supports(testClass))
    }

    @Test
    fun `supports with no supporting Instantiator returns false`() {
        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(false)
        whenever(instantiator2.supports(testClass)).thenReturn(false)

        assertFalse(instantiator.supports(testClass))
    }

    @Test
    fun `supports with list constructor and supporting Instantiator returns true`() {
        instantiator = CompositeInstantiator(Arrays.asList(instantiator1, instantiator2))

        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(false)
        whenever(instantiator2.supports(testClass)).thenReturn(true)

        assertTrue(instantiator.supports(testClass))
    }

    @Test
    fun `instantiateSupportedType with multiple supporting Instantiators returns first instance`() {
        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(true)
        whenever(instantiator1.instantiate(testClass)).thenReturn("1")

        assertEquals("1", instantiator.instantiate(testClass))
    }

    @Test
    fun `instantiateSupportedType with 1 supporting Instantiator returns instance`() {
        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(false)
        whenever(instantiator2.supports(testClass)).thenReturn(true)
        whenever(instantiator2.instantiate(testClass)).thenReturn("2")

        assertEquals("2", instantiator.instantiate(testClass))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `instantiateSupportedType with no supporting Instantiator throws UnsupportedOperationException`() {
        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(false)
        whenever(instantiator2.supports(testClass)).thenReturn(false)

        instantiator.instantiate(testClass)
    }

    @Test
    fun `instantiateSupportedType with list constructor and 1 supporting Instantiator returns instance`() {
        instantiator = CompositeInstantiator(Arrays.asList(instantiator1, instantiator2))

        val testClass = String::class.java
        whenever(instantiator1.supports(testClass)).thenReturn(false)
        whenever(instantiator2.supports(testClass)).thenReturn(true)
        whenever(instantiator2.instantiate(testClass)).thenReturn("2")

        assertEquals("2", instantiator.instantiate(testClass))
    }

    @Test(expected = IllegalStateException::class)
    fun `instantiateSupportedType with unsupported type throws IllegalArgumentException`() {
        instantiator.instantiateSupportedType(CompositeInstantiatorTest::class.java)
    }
}
