package io.github.naverz.antonio;

import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;

import io.github.naverz.antonio.core.AntonioModel;

public interface GenericAntonioFindable {
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @Nullable
    default Pair<Class<?>, Class<?>> findAntonioClass() {
        try {
            Class<?> cls = getClass();
            while (cls != Object.class && cls != null) {
                Class<?> result = findOnGeneric(cls, AntonioModel.class);
                if (result != null) {
                    return new Pair<>(cls.getSuperclass(), result);
                }
                cls = cls.getSuperclass();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @Nullable
    static Class<?> findOnGeneric(Class<?> target, Class<?> toFind) {
        try {
            Type[] types =
                    ((ParameterizedType) Objects.requireNonNull(target.getGenericSuperclass()))
                            .getActualTypeArguments();
            for (Type t : types) {
                if (isInParent((Class<?>) t, toFind)) {
                    return (Class<?>) t;
                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static Boolean isInParent(Class<?> target, Class<?> toFind) {
        HashSet<String> parentClassNames = new HashSet<>();
        Class<?> cls = target;
        while (cls != Object.class && cls != null) {
            parentClassNames.add(target.getName());
            recursiveFindInterfaceAndAdd(parentClassNames, cls.getInterfaces());
            cls = cls.getSuperclass();
        }
        return parentClassNames.contains(toFind.getName());
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static void recursiveFindInterfaceAndAdd(HashSet<String> to, Class<?>[] interfaces) {
        for (Class<?> anInterface : interfaces) {
            to.add(anInterface.getName());
            recursiveFindInterfaceAndAdd(to, anInterface.getInterfaces());
        }
    }
}
