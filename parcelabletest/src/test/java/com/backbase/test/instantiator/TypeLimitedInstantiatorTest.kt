package com.backbase.test.instantiator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Backbase R&D B.V. on 28/01/2019.
 */
class TypeLimitedInstantiatorTest {

    private lateinit var instantiator: TypeLimitedInstantiator

    @Before
    fun setUp() {
        instantiator = TestInstantiator()
    }

    @Test
    fun `instantiate with supported type returns instance of that type`() {
        assertEquals(TestPrimitives.TEST_STRING, instantiator.instantiate(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `instantiate with unsupported type throws UnsupportedOperationException`() {
        instantiator.instantiate(Int::class.java)
    }

    private class TestInstantiator : TypeLimitedInstantiator() {

        @Suppress("UNCHECKED_CAST")
        override fun <O : Any?> instantiateSupportedType(objectClass: Class<O>) = TestPrimitives.TEST_STRING as O

        override fun supports(objectClass: Class<*>) = objectClass == String::class.java
    }
}
