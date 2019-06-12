package com.backbase.test.instantiator

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 28/01/2019.
 */
@RunWith(MockitoJUnitRunner::class)
abstract class RandomInstantiatorTest<T> : InstantiatorTest<T>() {

    @Mock private lateinit var mockRandom: Random

    final override fun newInstantiator() = newInstantiator(mockRandom)

    protected abstract fun newInstantiator(random: Random): RandomInstantiator<T>

    protected open fun mockRandom(mockRandom: Random) {
        // Hook
    }

    @Before
    fun setUpMockRandom() {
        mockRandom(mockRandom)
    }
}
