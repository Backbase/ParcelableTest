package com.backbase.test.instantiator

import java.util.AbstractCollection
import java.util.AbstractList
import java.util.ArrayList

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Instantiates an [ArrayList] given any superclass of [ArrayList]. Currently does not support populating the list.
 */
class EmptyArrayListInstantiator : ConstrainedMultiTypeInstantiator {

    override val supportedTypes: Collection<Class<*>> = setOf(
        Iterable::class.java,
        Collection::class.java,
        List::class.java,
        AbstractCollection::class.java,
        AbstractList::class.java
    )

    override fun <T> instantiate(type: Class<T>): T {
        if (!supports(type)) throw IllegalArgumentException("${javaClass.simpleName} does not support type $type")

        @Suppress("UNCHECKED_CAST")
        return ArrayList<Any>() as T
    }
}
