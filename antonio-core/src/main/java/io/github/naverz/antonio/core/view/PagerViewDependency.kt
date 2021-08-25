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

package io.github.naverz.antonio.core.view

import android.view.ViewGroup
import io.github.naverz.antonio.core.TypedModel
import java.lang.reflect.ParameterizedType

interface PagerViewDependency<ITEM : TypedModel> {
    fun instantiateItem(container: ViewGroup, position: Int, typedModel: ITEM): Any
    fun destroyItem(container: ViewGroup, position: Int, typedModel: ITEM, any: Any)
    fun getPageWidth(position: Int, typedModel: ITEM): Float

    @Suppress("UNCHECKED_CAST")
    fun getDeclaredLayoutIdModelClass(): Class<ITEM>? = try {
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<ITEM>)
    } catch (e: Exception) {
        null
    }
}

