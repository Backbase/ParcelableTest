package com.backbase.test.instantiator

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Finds the generic type parameter for direct implementers of [genericInterfaceClass]. Throws [NotImplementedError] if the receiver does not directly
 * implement the [genericInterfaceClass] (including if the generic interface is implemented by a superclass, i.e. not directly).
 */
internal fun Class<*>.findGenericInterfaceTypeArgument(genericInterfaceClass: Class<*>, errorMessage: () -> String) : Type {
    for (genericInterface in genericInterfaces) {
        if (genericInterface is ParameterizedType && genericInterface.rawType == genericInterfaceClass)
            return genericInterface.actualTypeArguments[0]
    }

    throw NotImplementedError(errorMessage.invoke())
}

/**
 * Finds the generic type parameter for the receiver's superclass. Throws [NotImplementedError] if the generic type can't be determined.
 */
internal fun Class<*>.findGenericSuperclassTypeArgument(errorMessage: () -> String) : Type {
    val genericSuperclass = genericSuperclass
    if (genericSuperclass is ParameterizedType)
        return genericSuperclass.actualTypeArguments[0]
    else
        throw NotImplementedError(errorMessage.invoke())
}
