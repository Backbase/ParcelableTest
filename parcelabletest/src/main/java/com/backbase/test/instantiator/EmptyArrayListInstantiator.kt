package com.backbase.test.instantiator

import java.util.AbstractCollection
import java.util.AbstractList
import java.util.ArrayList

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates an [ArrayList] given any superclass of [ArrayList]. Currently does not support populating the list.
 */
class EmptyArrayListInstantiator : MultiTypeInstantiator {

    override fun supports(type: Class<*>) = isArrayListAbstractSuperclass(type)

    override fun <T> instantiate(type: Class<T>): T {
        if (!supports(type)) throw IllegalArgumentException("Type $type is not supported by ${javaClass.simpleName}")

        @Suppress("UNCHECKED_CAST")
        return ArrayList<Any>() as T
    }

    private fun isArrayListAbstractSuperclass(objectClass: Class<*>): Boolean {
        return objectClass == Iterable::class.java
                || objectClass == Collection::class.java
                || objectClass == List::class.java
                || objectClass == AbstractCollection::class.java
                || objectClass == AbstractList::class.java
    }
}
