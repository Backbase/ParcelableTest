package com.backbase.test.instantiator

/**
 * Created by Backbase R&D B.V. on 2019-06-12.
 * Uses the constructor parameter to instantiate [Number] instances.
 */
class NumberInstantiator(
    private val instantiator: Instantiator<out Number>
) : Instantiator<Number> {

    override fun instantiate() = instantiator.instantiate()

    override fun supportedType() = Number::class.java
}
