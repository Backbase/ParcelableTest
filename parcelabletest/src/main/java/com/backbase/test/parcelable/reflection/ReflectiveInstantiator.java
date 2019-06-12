package com.backbase.test.parcelable.reflection;

import android.os.Parcel;

import com.backbase.test.instantiator.CompositeInstantiator;
import com.backbase.test.instantiator.Instantiator;
import com.backbase.test.instantiator.MultiTypeInstantiator;
import com.backbase.test.instantiator.PrimitiveInstantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 07/12/2018.
 *
 * Attempts to instantiate objects using the provided {@link MultiTypeInstantiator}s first, then falls back to using reflection if the provided
 * {@link MultiTypeInstantiator}s do not support the desired type.
 * <p>
 * Any part of this may throw a security exception depending on your JRE's security manager. For these cases, it is best to either provide custom
 * {@link Instantiator}s or just not use this class at all.
 */
final class ReflectiveInstantiator implements MultiTypeInstantiator {

    @NonNull private final MultiTypeInstantiator preferredInstantiator;
    @NonNull private final PrimitiveInstantiator primitiveInstantiator;

    /**
     * @param primitiveInstantiator a delegate that handles instantiating any primitive, string, and enum types.
     */
    ReflectiveInstantiator(@NonNull PrimitiveInstantiator primitiveInstantiator) {
        this(primitiveInstantiator, new CompositeInstantiator());
    }

    /**
     * Construct an instance with the given {@link PrimitiveInstantiator} and any number of preferred {@link Instantiator}s to try before falling back
     * to reflection.
     */
    ReflectiveInstantiator(@NonNull PrimitiveInstantiator primitiveInstantiator, @NonNull Instantiator... preferredInstantiators) {
        this(primitiveInstantiator, new CompositeInstantiator(preferredInstantiators));
    }

    /**
     * @param primitiveInstantiator a delegate that handles instantiating any primitive, string, and enum types.
     * @param preferredInstantiator a delegate used to instantiate any supported type before reflection is attempted.
     *                              Useful for types that cause reflection issues.
     */
    ReflectiveInstantiator(@NonNull PrimitiveInstantiator primitiveInstantiator, @NonNull MultiTypeInstantiator preferredInstantiator) {
        this.preferredInstantiator = preferredInstantiator;
        this.primitiveInstantiator = primitiveInstantiator;
    }

    /**
     * This is a very slow implementation because it's difficult to generically tell if instantiation will
     * work via reflection without actually trying. May be possible to optimize in the future. For now it's
     * best to skip this method and just try {@link #instantiate(Class)}.
     */
    @Override
    public boolean supports(@NonNull Class<?> objectClass) {
        if (objectClass.isArray()) {
            // TODO Handle arrays
            return false;
        } else {
            try {
                instantiate(objectClass);
                return true;
            } catch (Exception error) {
                return false;
            }
        }
    }

    /**
     * Constructs an {@link O} with all of its member fields populated. First attempts to use one of the additional {@link Instantiator}s provided in
     * the constructor. If none of those supports the given type, attempts to use the {@link PrimitiveInstantiator} provided in the constructor. If
     * the given type is still not supported, this method uses reflection to instantiate the object and recursion to populate any member fields.
     *
     * @param objectClass The class to instantiate
     * @return an object of type {@link O} with all of its member fields populated.
     */
    @Override
    public <O> O instantiate(@NonNull Class<O> objectClass) {
        if (preferredInstantiator.supports(objectClass)) {
            return preferredInstantiator.instantiate(objectClass);
        } else if (primitiveInstantiator.supports(objectClass)) {
            return primitiveInstantiator.instantiate(objectClass);
        } else {
            return reflectivelyInstantiate(objectClass);
        }
    }

    private <O> O reflectivelyInstantiate(@NonNull Class<O> objectClass) {
        try {
            final Constructor<O> chosenConstructor = chooseMostConvenientConstructor(objectClass);

            final Class<?>[] chosenConstructorParameterTypes = chosenConstructor.getParameterTypes();
            final Object[] initArgs = new Object[chosenConstructorParameterTypes.length];
            for (int i = 0; i < chosenConstructorParameterTypes.length; ++i) {
                Class<?> parameterClass = chosenConstructorParameterTypes[i];
                initArgs[i] = instantiate(parameterClass);
            }
            final O object = chosenConstructor.newInstance(initArgs);
            populateUninitializedFields(object);
            return object;
        } catch (Exception exception) {
            // Signature does not allow for checked exceptions:
            throw new RuntimeInstantiationException("Could not reflectively instantiate " + objectClass.getName(), exception);
        }
    }

    private void populateUninitializedFields(@NonNull Object object) {
        final Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            final int fieldModifiers = field.getModifiers();
            if (!Modifier.isStatic(fieldModifiers) && !Modifier.isFinal(fieldModifiers)) {
                populateUninitializedField(object, field);
            }
        }
    }

    private void populateUninitializedField(@NonNull Object object, @NonNull Field field) {
        field.setAccessible(true);
        try {
            if (isApparentlyUninitialized(field.get(object))) {
                field.set(object, instantiate(field.getType()));
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeInstantiationException(ex);
        }
    }

    @NonNull
    private static <O> Constructor<O> chooseMostConvenientConstructor(@NonNull Class<O> objectClass)
            throws NoSuchMethodException {
        final Constructor<?>[] publicConstructors = objectClass.getConstructors();
        Constructor<O> chosenConstructor = null;
        for (Constructor<?> constructor : publicConstructors) {
            try {
                //noinspection unchecked (Just ignore this constructor if it is the wrong type)
                final Constructor<O> objectConstructor = (Constructor<O>) constructor;
                final Class<?>[] constructorParameterTypes = objectConstructor.getParameterTypes();
                if (constructorParameterTypes.length == 0) {
                    // Prefer empty constructor:
                    chosenConstructor = objectConstructor;
                    break;
                } else if (chosenConstructor == null) {
                    // Else use the first non-empty constructor:
                    chosenConstructor = objectConstructor;
                }
            } catch (ClassCastException ignored) {
                // Ignored exception
            }
        }

        if (chosenConstructor == null) {
            chosenConstructor = chooseMostConvenientNonPublicConstructor(objectClass);
        }

        if (!Modifier.isPublic(objectClass.getModifiers())) {
            // Make constructor accessible if class is not public but constructor is:
            chosenConstructor.setAccessible(true);
        }
        return chosenConstructor;
    }

    @NonNull
    private static <O> Constructor<O> chooseMostConvenientNonPublicConstructor(@NonNull Class<O> objectClass)
            throws NoSuchMethodException {
        final Constructor<?>[] constructors = objectClass.getDeclaredConstructors();
        Constructor<O> chosenConstructor = null;
        for (Constructor<?> constructor : constructors) {
            try {
                if (getVisibility(constructor) == Visibility.PUBLIC) {
                    // This method only returns non-public constructors
                    continue;
                }

                //noinspection unchecked (Just ignore this constructor if it is the wrong type)
                final Constructor<O> objectConstructor = (Constructor<O>) constructor;
                final Class<?>[] constructorParameterTypes = objectConstructor.getParameterTypes();

                if (includesParcelClass(constructorParameterTypes)) {
                    // Don't use the Parcel constructor:
                    continue;
                }

                if (constructorParameterTypes.length == 0) {
                    // Prefer empty constructor:
                    chosenConstructor = objectConstructor;
                    break;
                } else if (chosenConstructor == null) {
                    chosenConstructor = objectConstructor;
                } else if (getVisibility(objectConstructor).ordinal() > getVisibility(chosenConstructor).ordinal()) {
                    // Use highest-visibility constructor available
                    chosenConstructor = objectConstructor;
                }
            } catch (ClassCastException ignored) {
                // Ignored exception
            }
        }

        if (chosenConstructor == null) {
            throw new NoSuchMethodException(objectClass.getSimpleName() + " does not a have any constructors");
        } else {
            chosenConstructor.setAccessible(true);
            return chosenConstructor;
        }
    }

    private static boolean isApparentlyUninitialized(Object fieldValue) {
        return fieldValue == null // uninitialized object
                || fieldValue.equals(false)  // uninitialized boolean
                || fieldValue.equals((byte) 0) // uninitialized byte
                || fieldValue.equals((short) 0) // uninitialized short
                || fieldValue.equals(0) // uninitialized int
                || fieldValue.equals(0L) // uninitialized long
                || fieldValue.equals(0f) // uninitialized float
                || fieldValue.equals(0d) // uninitialized double
                || fieldValue.equals('\u0000'); // uninitlized char
    }

    @NonNull
    private static Visibility getVisibility(@NonNull Constructor constructor) {
        if (Modifier.isPublic(constructor.getModifiers())) {
            return Visibility.PUBLIC;
        } else if (Modifier.isProtected(constructor.getModifiers())) {
            return Visibility.PROTECTED;
        } else if (Modifier.isPrivate(constructor.getModifiers())) {
            return Visibility.PRIVATE;
        } else {
            return Visibility.PACKAGE_PRIVATE;
        }
    }

    private static boolean includesParcelClass(@NonNull Class<?>[] types) {
        for (Class type : types) {
            if (type == Parcel.class) {
                return true;
            }
        }
        return false;
    }

    private enum Visibility {
        PRIVATE,
        PACKAGE_PRIVATE,
        PROTECTED,
        PUBLIC
    }

    static final class RuntimeInstantiationException extends RuntimeException {

        RuntimeInstantiationException(Exception cause) {
            super(cause);
        }

        RuntimeInstantiationException(String message, Exception cause) {
            super(message, cause);
        }
    }
}
