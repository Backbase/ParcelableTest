package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Similar to [Instantiator], but supports instantiating multiple types. API is less safe than that of [Instantiator], as an invalid type can be
 * passed to [instantiate] by the consumer.
 */
// TODO: Is "internal" correct?
internal interface MultiTypeInstantiator {

    fun supports(type: Class<*>): Boolean

    fun <T> instantiate(type: Class<T>): T
}
