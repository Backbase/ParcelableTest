package com.backbase.test.instantiator

import org.junit.Before

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
class PrimitiveInstantiatorTest : BasePrimitiveInstantiatorTest() {

    override lateinit var instantiator: PrimitiveInstantiator

    @Before
    fun setUp() {
        instantiator = TestInstantiator()
    }

    private class TestInstantiator : PrimitiveInstantiator() {

        override fun instantiateBoolean() = TestPrimitives.TEST_BOOLEAN

        override fun instantiateByte() = TestPrimitives.TEST_BYTE
        override fun instantiateShort() = TestPrimitives.TEST_SHORT
        override fun instantiateInteger() = TestPrimitives.TEST_INT
        override fun instantiateLong() = TestPrimitives.TEST_LONG

        override fun instantiateFloat() = TestPrimitives.TEST_FLOAT
        override fun instantiateDouble() = TestPrimitives.TEST_DOUBLE

        override fun instantiateCharacter() = TestPrimitives.TEST_CHAR
        override fun instantiateString() = TestPrimitives.TEST_STRING

        override fun <E : Enum<E>?> provideEnum(enumType: Class<E>): E = enumType.enumConstants[0]
    }
}
