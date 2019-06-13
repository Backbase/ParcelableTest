package com.backbase.test.instantiator

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 */
abstract class InstantiatorTest<T> {

    protected abstract val supportedType: Class<T>
    protected abstract val expectedInstance: T

    private lateinit var instantiator: Instantiator<T>

    protected abstract fun newInstantiator(): Instantiator<T>

    @Before
    fun setUpInstantiator() {
        instantiator = newInstantiator()
    }

    @Test
    fun `supportedClass returns supportedType`() {
        Assert.assertEquals(supportedType, instantiator.supportedType)
    }

    @Test
    fun `instantiate returns Random-generated instance of that type`() {
        Assert.assertEquals(expectedInstance, instantiator.instantiate())
    }
}
