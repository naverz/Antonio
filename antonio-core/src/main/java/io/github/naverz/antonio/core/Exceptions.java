/*
 * Antonio
 * Copyright (c) 2021-present NAVER Z Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.naverz.antonio.core;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import io.github.naverz.antonio.GenericAntonioFindable;
import io.github.naverz.antonio.core.container.ContainerType;
import io.github.naverz.antonio.core.container.FragmentContainer;
import io.github.naverz.antonio.core.container.PagerViewContainer;
import io.github.naverz.antonio.core.container.ViewHolderContainer;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class Exceptions {
    private Exceptions() {
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static final String ERROR_TEXT_UNKNOWN = "An unknown error is found. Please, Issue up on Github (https://github.com/naverz/Antonio).";

    public static String errorIllegalBinding(@Nullable String layoutIdStr, @Nullable AntonioModel model) {
        if (layoutIdStr == null || model == null) {
            return "There is no variable for the model in layout xml, Did you declare the variable in your layout xml?";
        }
        return String.format("There is no variable for [%s] in [%s.xml]. Did you declare the variable in your layout xml?", model.getClass().getSimpleName(), layoutIdStr);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static String errorClassCast(Object model, GenericAntonioFindable findable) {
        final Class<?> modelClass = model.getClass();
        Pair<Class<?>, Class<?>> viewClassWithAntonioGeneric = findable.findAntonioClass();
        final Class<?> viewClass = viewClassWithAntonioGeneric != null ? viewClassWithAntonioGeneric.first : findable.getClass();
        final Class<?> antonioClass = viewClassWithAntonioGeneric != null ? viewClassWithAntonioGeneric.second : Object.class;
        return String.format("The model type [%s] doesn't match with [%s]. " +
                        "Check whether it is correct for the generic type of [%s<%s>]. " +
                        "You might need to check which one you intent between [%s] and [%s] for the model to be bound.",
                modelClass.getName(), viewClass.getName(), viewClass.getSimpleName(), antonioClass.getSimpleName(), modelClass.getSimpleName(), antonioClass.getSimpleName());
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static String errorRegisterFirst(@NonNull Class<?> clazz, @NonNull ContainerType type) {
        return String.format("There is no [%s] in [%s]. Please, Add [%s] to [%s]",
                clazz.getName(), getContainerName(type), clazz.getName(), getContainerName(type));
    }

    @NonNull
    private static String getContainerName(@NonNull ContainerType type) {
        final String containerType;
        switch (type) {
            case FRAGMENT:
                containerType = FragmentContainer.class.getName();
                break;
            case PAGER_VIEW:
                containerType = PagerViewContainer.class.getName();
                break;
            default:
                containerType = ViewHolderContainer.class.getName();
                break;
        }
        return containerType;
    }
}
