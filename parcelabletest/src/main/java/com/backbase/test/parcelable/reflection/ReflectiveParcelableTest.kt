package com.backbase.test.parcelable.reflection

import android.os.Parcelable
import com.backbase.test.findGenericSuperclassTypeArgument
import com.backbase.test.instantiator.CompositeInstantiator
import com.backbase.test.instantiator.CompositeMultiTypeInstantiator
import com.backbase.test.instantiator.Instantiator
import com.backbase.test.instantiator.MultiTypeInstantiator
import com.backbase.test.instantiator.NumberInstantiator
import com.backbase.test.instantiator.PrimitiveInstantiator
import com.backbase.test.instantiator.random.RandomBigDecimalInstantiator
import com.backbase.test.instantiator.random.RandomDateInstantiator
import com.backbase.test.instantiator.random.RandomIntInstantiator
import com.backbase.test.parcelable.ParcelableTest
import org.junit.Assert.assertTrue
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.util.Random

/**
 * Created by Backbase R&D B.V. on 2019-06-13.
 */
abstract class ReflectiveParcelableTest<P : Parcelable> internal constructor(
    private var instantiator: RobustReflectiveInstantiator
) : ParcelableTest<P>() {

    //region Constructors
    /**
     * Construct a [ReflectiveParcelableTest] instance with the default preferred instantiators, the default [PrimitiveInstantiator], and the default
     * reflective instantiator.
     */
    constructor() : this(defaultPrimitiveInstantiator)

    /**
     * Construct a [ReflectiveParcelableTest] instance with the provided [preferredInstantiators], the default preferred instantiators,
     * the default [PrimitiveInstantiator], and the default reflective instantiator.
     */
    constructor(vararg preferredInstantiators: Instantiator<*>) : this(defaultPrimitiveInstantiator, *preferredInstantiators)

    /**
     * Construct a [ReflectiveParcelableTest] instance with the provided [preferredInstantiators], the default preferred instantiators,
     * the provided [primitiveInstantiator], and the default reflective instantiator.
     */
    constructor(primitiveInstantiator: PrimitiveInstantiator, vararg preferredInstantiators: Instantiator<*>) :
            this(primitiveInstantiator, CompositeInstantiator(*preferredInstantiators))

    /**
     * Construct a [ReflectiveParcelableTest] instance with the provided [preferredInstantiators], the default preferred instantiators,
     * the default [PrimitiveInstantiator], and the default reflective instantiator.
     */
    constructor(vararg preferredInstantiators: MultiTypeInstantiator) : this(defaultPrimitiveInstantiator, *preferredInstantiators)

    /**
     * Construct a [ReflectiveParcelableTest] instance with the provided [preferredInstantiators], the default preferred instantiators,
     * the provided [primitiveInstantiator], and the default reflective instantiator.
     */
    constructor(primitiveInstantiator: PrimitiveInstantiator, vararg preferredInstantiators: MultiTypeInstantiator) :
            this(primitiveInstantiator, CompositeMultiTypeInstantiator(*preferredInstantiators))

    /**
     * Construct a [ReflectiveParcelableTest] instance with the default preferred instantiators, the provided [primitiveInstantiator], and the default
     * reflective instantiator.
     */
    constructor(primitiveInstantiator: PrimitiveInstantiator) : this(primitiveInstantiator, CompositeInstantiator(*DEFAULT_PREFERRED_INSTANTIATORS))

    /**
     * Construct a [ReflectiveParcelableTest] instance with the provided [preferredInstantiator], the default preferred instantiators,
     * the provided [primitiveInstantiator], and the default reflective instantiator.
     */
    constructor(primitiveInstantiator: PrimitiveInstantiator, preferredInstantiator: MultiTypeInstantiator) :
            this(primitiveInstantiator, ReflectiveInstantiator(), preferredInstantiator)

    /**
     * Construct a [ReflectiveParcelableTest] instance with the provided [preferredInstantiator], the default preferred instantiators,
     * the provided [primitiveInstantiator], and the provided [reflectiveInstantiator].
     */
    internal constructor(
        primitiveInstantiator: PrimitiveInstantiator,
        reflectiveInstantiator: ReflectiveInstantiator,
        preferredInstantiator: MultiTypeInstantiator
    ) : this(RobustReflectiveInstantiator(preferredInstantiator, primitiveInstantiator, reflectiveInstantiator))
    //endregion

    /**
     * Use reflection to create a new instance of [P].
     */
    final override fun newItem(): P {
        return instantiator.instantiate(getItemClass())
    }

    /**
     * Use reflection to retrieve [P]'s CREATOR instance.
     */
    final override fun itemCreator(): Parcelable.Creator<P> {
        val creatorField = getItemClass().getField(CREATOR_FIELD_NAME)
        assertTrue(creatorField.type.isAssignableFrom(Parcelable.Creator::class.java))
        assertTrue(Modifier.isPublic(creatorField.modifiers))

        if (!Modifier.isPublic(getItemClass().modifiers)) {
            // Class is not public but creator is; make it accessible:
            creatorField.isAccessible = true
        }

        @Suppress("UNCHECKED_CAST") // Exception should be thrown if the Creator is the wrong type
        return creatorField.get(null) as Parcelable.Creator<P>
    }

    /**
     * Resolve the class of [P] using reflection. If there is more than 1 level of inheritance before [P] is concrete, this method must be overridden.
     */
    protected fun getItemClass(): Class<P> {
        val genericSuperclass = javaClass.genericSuperclass
        if (genericSuperclass is ParameterizedType && genericSuperclass.rawType == ReflectiveParcelableTest::class.java) {
            val actualType = javaClass.findGenericSuperclassTypeArgument { "Could not determine generic Parcelable type for $javaClass" }
            @Suppress("UNCHECKED_CAST")
            return actualType as Class<P>
        } else {
            throw NotImplementedError("getItemClass must be overridden by non-direct subclasses of ${ReflectiveParcelableTest::class.java}")
        }
    }

    private companion object {
        private const val CREATOR_FIELD_NAME = "CREATOR"

        private val RANDOM = Random()

        private val DEFAULT_PREFERRED_INSTANTIATORS = arrayOf<Instantiator<*>>(
            RandomDateInstantiator(RANDOM),
            RandomBigDecimalInstantiator(RANDOM),
            NumberInstantiator(RandomIntInstantiator(RANDOM))
        )

        @JvmStatic private val defaultPrimitiveInstantiator get() = PrimitiveInstantiator.ofRandom(RANDOM)
    }
}
