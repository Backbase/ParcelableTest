package com.backbase.test.parcelable.reflection;

import android.os.Parcel;

import com.backbase.test.instantiator.MultiTypeInstantiator;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import androidx.annotation.NonNull;

/**
 * Created by Backbase R&D B.V. on 2019-06-13.
 */
final class ReflectiveInstantiator implements MultiTypeInstantiator {

    /**
     * This is a very slow implementation because it's difficult to generically tell if instantiation will
     * work via reflection without actually trying. May be possible to optimize in the future. For now it's
     * best to skip this method and just try {@link #instantiate(Class)}.
     */
    @Override
    public boolean supports(@NotNull Class<?> type) {
        if (type.isArray()) {
            // TODO Handle arrays
            return false;
        } else {
            try {
                instantiate(type);
                return true;
            } catch (Exception error) {
                return false;
            }
        }
    }

    @Override
    public <T> T instantiate(@NotNull Class<T> type) {
        try {
            final Constructor<T> chosenConstructor = chooseMostConvenientConstructor(type);

            final Class<?>[] chosenConstructorParameterTypes = chosenConstructor.getParameterTypes();
            final Object[] initArgs = new Object[chosenConstructorParameterTypes.length];
            for (int i = 0; i < chosenConstructorParameterTypes.length; ++i) {
                Class<?> parameterClass = chosenConstructorParameterTypes[i];
                initArgs[i] = instantiate(parameterClass);
            }
            final T object = chosenConstructor.newInstance(initArgs);
            populateUninitializedFields(object);
            return object;
        } catch (Exception exception) {
            // Signature does not allow for checked exceptions:
            throw new RuntimeInstantiationException("Could not reflectively instantiate " + type.getName(), exception);
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
                || fieldValue.equals('\u0000'); // uninitialized char
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
