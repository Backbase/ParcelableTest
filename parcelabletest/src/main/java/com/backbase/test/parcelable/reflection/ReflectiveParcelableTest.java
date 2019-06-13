package com.backbase.test.parcelable.reflection;

import android.os.Parcelable;

import com.backbase.test.instantiator.CompositeInstantiator;
import com.backbase.test.instantiator.Instantiator;
import com.backbase.test.instantiator.NumberInstantiator;
import com.backbase.test.instantiator.PrimitiveInstantiator;
import com.backbase.test.instantiator.random.RandomBigDecimalInstantiator;
import com.backbase.test.instantiator.random.RandomDateInstantiator;
import com.backbase.test.instantiator.random.RandomIntInstantiator;
import com.backbase.test.parcelable.ParcelableTest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import static org.junit.Assert.assertTrue;

/**
 * Created by Backbase R&D B.V. on 07/12/2018.
 *
 * Tests an arbitrary {@link Parcelable} implementation without requiring any work in the implementation test class. Constructs an instance of
 * {@link P} via reflection, populates all of its fields via reflection, and then checks that item's equality with a copy that has been parceled and
 * un-parceled.
 * <p>
 * Any part of this may throw a security exception depending on your JRE's security manager. For these cases, it is best to either provide custom
 * {@link Instantiator}s or just use {@link ParcelableTest} without reflection.
 */
public abstract class ReflectiveParcelableTest<P extends Parcelable> extends ParcelableTest<P> {

    private static final String CREATOR_FIELD_NAME = "CREATOR";

    private static final Random RANDOM = new Random();

    private RobustReflectiveInstantiator instantiator;

    @CallSuper
    @Override
    public void setUp() {
        super.setUp();
        final List<Instantiator<?>> preferredInstantiators = new ArrayList<>(getPreferredInstantiators());
        preferredInstantiators.addAll(defaultPreferredInstantiators());
        instantiator = new RobustReflectiveInstantiator(new CompositeInstantiator(preferredInstantiators), getPrimitiveInstantiator(),
                new ReflectiveInstantiator());
    }

    @NonNull
    @Override
    protected final P newItem() {
        return instantiator.instantiate(getItemClass());
    }

    @Override
    @NonNull
    protected final Parcelable.Creator<P> itemCreator() {
        try {
            final Field creatorField = getItemClass().getField(CREATOR_FIELD_NAME);
            assertTrue(creatorField.getType().isAssignableFrom(Parcelable.Creator.class));
            assertTrue(Modifier.isPublic(creatorField.getModifiers()));

            if (!Modifier.isPublic(getItemClass().getModifiers())) {
                // Class is not public but creator is; make it accessible:
                creatorField.setAccessible(true);
            }

            //noinspection unchecked: Exception should be thrown if the Creator is the wrong type
            return (Parcelable.Creator<P>) creatorField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException exception) {
            throw new RuntimeReflectionException(exception);
        }
    }

    protected PrimitiveInstantiator getPrimitiveInstantiator() {
        return PrimitiveInstantiator.ofRandom(RANDOM);
    }

    /**
     * Override to add additional {@link Instantiator}s. If a given member
     * variable is supported by one of this list's {@link Instantiator}s,
     * the first supporting {@link Instantiator} in the list will be used.
     * @return the list of additional {@link Instantiator}s
     */
    protected List<Instantiator<?>> getPreferredInstantiators() {
        return Collections.emptyList();
    }

    private Class<P> getItemClass() {
        //noinspection unchecked: The type argument does correctly resolve to Class<P>
        return ((Class<P>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private static List<Instantiator<?>> defaultPreferredInstantiators() {
        return Arrays.asList(
                new RandomDateInstantiator(RANDOM),
                new RandomBigDecimalInstantiator(RANDOM),
                new NumberInstantiator(new RandomIntInstantiator(RANDOM)));
    }

    public static final class RuntimeReflectionException extends RuntimeException {
        private RuntimeReflectionException(Throwable cause) {
            super(cause);
        }
    }
}
