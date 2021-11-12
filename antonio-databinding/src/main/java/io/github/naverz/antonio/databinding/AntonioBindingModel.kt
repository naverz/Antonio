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

package io.github.naverz.antonio.databinding

import androidx.annotation.LayoutRes
import io.github.naverz.antonio.core.AntonioModel

interface AntonioBindingModel : AntonioModel {
    @LayoutRes
    fun layoutId(): Int
    fun bindingVariableId(): Int?

    /**
     * Evaluates the pending bindings, updating any Views that have expressions bound to modified variables when it's binding.
     *
     * **It can makes your list won't be as smooth as without calling [executePendingBindings](https://developer.android.com/reference/android/databinding/ViewDataBinding#executependingbindings)`**
     *
     * @return If you want to execute pending bindings for every position, return true
     */
    fun requireExecutePendingBindings(): Boolean = false
}