package com.backbase.test.parcelable.reflection;

import android.os.Parcelable;

import com.backbase.test.instantiator.CompositeInstantiator;
import com.backbase.test.instantiator.CompositeMultiTypeInstantiator;
import com.backbase.test.instantiator.Instantiator;
import com.backbase.test.instantiator.MultiTypeInstantiator;
import com.backbase.test.instantiator.NumberInstantiator;
import com.backbase.test.instantiator.PrimitiveInstantiator;
import com.backbase.test.instantiator.random.RandomBigDecimalInstantiator;
import com.backbase.test.instantiator.random.RandomDateInstantiator;
import com.backbase.test.instantiator.random.RandomIntInstantiator;
import com.backbase.test.parcelable.ParcelableTest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Random;

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

    private static final Instantiator<?>[] DEFAULT_PREFERRED_INSTANTIATORS = {
            new RandomDateInstantiator(RANDOM),
            new RandomBigDecimalInstantiator(RANDOM),
            new NumberInstantiator(new RandomIntInstantiator(RANDOM))
    };

    private static PrimitiveInstantiator defaultPrimitiveInstantiator() {
        return PrimitiveInstantiator.ofRandom(RANDOM);
    }

    private RobustReflectiveInstantiator instantiator;

    public ReflectiveParcelableTest() {
        this(defaultPrimitiveInstantiator());
    }

    public ReflectiveParcelableTest(@NonNull Instantiator<?>... preferredInstantiators) {
        this(defaultPrimitiveInstantiator(), preferredInstantiators);
    }

    public ReflectiveParcelableTest(@NonNull PrimitiveInstantiator primitiveInstantiator, @NonNull Instantiator<?>... preferredInstantiators) {
        this(primitiveInstantiator, new CompositeInstantiator(preferredInstantiators));
    }

    public ReflectiveParcelableTest(@NonNull MultiTypeInstantiator... preferredInstantiators) {
        this(defaultPrimitiveInstantiator(), preferredInstantiators);
    }

    public ReflectiveParcelableTest(@NonNull PrimitiveInstantiator primitiveInstantiator, @NonNull MultiTypeInstantiator... preferredInstantiators) {
        this(primitiveInstantiator, new CompositeMultiTypeInstantiator(preferredInstantiators));
    }

    public ReflectiveParcelableTest(@NonNull PrimitiveInstantiator primitiveInstantiator) {
        this(primitiveInstantiator, new CompositeInstantiator(DEFAULT_PREFERRED_INSTANTIATORS));
    }

    public ReflectiveParcelableTest(@NonNull PrimitiveInstantiator primitiveInstantiator, @NonNull MultiTypeInstantiator preferredInstantiator) {
        this(primitiveInstantiator, new ReflectiveInstantiator(), preferredInstantiator);
    }

    ReflectiveParcelableTest(@NonNull PrimitiveInstantiator primitiveInstantiator, @NonNull ReflectiveInstantiator reflectiveInstantiator,
            @NonNull MultiTypeInstantiator preferredInstantiator) {
        instantiator = new RobustReflectiveInstantiator(preferredInstantiator, primitiveInstantiator, reflectiveInstantiator);
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

    /**
     * Resolves the class of {@link P} using reflection. If there is more than 1 level of inheritance before {@link P} is concrete, this method should
     * be overridden.
     */
    protected Class<P> getItemClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            //noinspection unchecked Method should be overridden if ClassCastException is thrown.
            return (Class<P>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            throw new UnsupportedOperationException("Superclass type could not be determined. Please override this method.");
        }
    }

    public static final class RuntimeReflectionException extends RuntimeException {
        private RuntimeReflectionException(Throwable cause) {
            super(cause);
        }
    }
}
