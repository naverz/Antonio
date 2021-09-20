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

package io.github.naverz.antonio.compiler;

import org.checkerframework.checker.nullness.qual.NonNull;

interface Error {
    String ERROR_TEXT_UNKNOWN = "An unknown error is found. Please, Issue up on Github (https://github.com/naverz/Antonio).";

    static String getErrorTextClassIsNotSupportedType(@NonNull String className) {
        return String.format("%s is not a supported class type. Antonio annotation supports only three class types [TypedViewHolder, PagerViewDependency, AntonioFragment]", className);
    }
}
