package com.backbase.test.instantiator

import com.backbase.test.parcelable.TestEnum
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

/**
 * Created by Backbase R&D B.V. on 18/02/2019.
 */
abstract class BasePrimitiveInstantiatorTest {

    protected abstract val instantiator: OldPrimitiveInstantiator

    protected open val testByte = TestPrimitives.TEST_BYTE

    // region Supported classes
    @Test
    fun `primitive boolean is supported`() = testSupportsClass(Boolean::class.javaPrimitiveType)

    @Test
    fun `class boolean is supported`() = testSupportsClass(Boolean::class.java)

    @Test
    fun `primitive byte is supported`() = testSupportsClass(Byte::class.javaPrimitiveType)

    @Test
    fun `class byte is supported`() = testSupportsClass(Byte::class.java)

    @Test
    fun `primitive short is supported`() = testSupportsClass(Short::class.javaPrimitiveType)

    @Test
    fun `class short is supported`() = testSupportsClass(Short::class.java)

    @Test
    fun `primitive int is supported`() = testSupportsClass(Int::class.javaPrimitiveType)

    @Test
    fun `class int is supported`() = testSupportsClass(Int::class.java)

    @Test
    fun `primitive long is supported`() = testSupportsClass(Long::class.javaPrimitiveType)

    @Test
    fun `class long is supported`() = testSupportsClass(Long::class.java)

    @Test
    fun `primitive float is supported`() = testSupportsClass(Float::class.javaPrimitiveType)

    @Test
    fun `class float is supported`() = testSupportsClass(Boolean::class.java)

    @Test
    fun `primitive double is supported`() = testSupportsClass(Double::class.javaPrimitiveType)

    @Test
    fun `class double is supported`() = testSupportsClass(Double::class.java)

    @Test
    fun `primitive char is supported`() = testSupportsClass(Char::class.javaPrimitiveType)

    @Test
    fun `class char is supported`() = testSupportsClass(Char::class.java)

    @Test
    fun `string is supported`() = testSupportsClass(String::class.java)

    @Test
    fun `enum is supported`() = testSupportsClass(TestEnum::class.java)

    @Test
    fun `arbitrary other class is not supported`() = testSupportsClass(BasePrimitiveInstantiatorTest::class.java, isSupported = false)

    private fun testSupportsClass(testClass: Class<out Any>?, isSupported: Boolean = true) {
        if (testClass == null) throw IllegalArgumentException("Null classes are not supported")

        if (isSupported)
            assertTrue(instantiator.supports(testClass))
        else
            assertFalse(instantiator.supports(testClass))
    }
    //endregion

    //region instantiate
    @Test
    fun `boolean is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_BOOLEAN)

    @Test
    fun `byte is instantiated`() = testInstantiatesClass(testByte)

    @Test
    fun `short is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_SHORT)

    @Test
    fun `int is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_INT)

    @Test
    fun `long is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_LONG)

    @Test
    fun `float is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_FLOAT)

    @Test
    fun `double is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_DOUBLE)

    @Test
    fun `char is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_CHAR)

    @Test
    fun `string is instantiated`() = testInstantiatesClass(TestPrimitives.TEST_STRING, isPrimitive = false)

    @Test
    fun `enum is provided`() = testInstantiatesClass(TestEnum.A, isPrimitive = false)

    @Test(expected = IllegalStateException::class)
    fun `instantiateSupportedType with unsupported type throws IllegalArgumentException`() {
        instantiator.instantiateSupportedType(BasePrimitiveInstantiatorTest::class.java)
    }

    private inline fun <reified T : Any> testInstantiatesClass(expectedValue: T, isPrimitive: Boolean = true) {
        val tClass = T::class.java
        if (!instantiator.supports(tClass))
            fail("Cannot instantiate ${tClass.simpleName}")
        assertEquals(expectedValue, instantiator.instantiateSupportedType(tClass))

        if (isPrimitive) {
            val tPrimitiveType = T::class.javaPrimitiveType
                    ?: throw IllegalArgumentException("isPrimitive is true, so ${tClass.simpleName} must be a primitive type")
            if (!instantiator.supports(tPrimitiveType))
                fail("Cannot instantiate ${tPrimitiveType.simpleName}")
            assertEquals(expectedValue, instantiator.instantiateSupportedType(tPrimitiveType))
        }
    }
    //endregion
}
