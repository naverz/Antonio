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

package io.github.naverz.antonio.databinding.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.Exceptions
import io.github.naverz.antonio.databinding.AntonioBindingModel

abstract class AntonioAutoBindingFragment :
    AntonioBindingFragment<ViewDataBinding, AntonioModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        additionalVariables()?.let {
            for (entry in it) {
                binding?.setVariable(entry.key, entry.value)
            }
        }
        bindingVariableId()?.let {
            val model = requireModel()
            if (binding?.setVariable(it, model) == false) {
                val layoutIdStr =
                    view.context.resources.getResourceName((model as AntonioBindingModel).layoutId())
                throw IllegalStateException(Exceptions.errorIllegalBinding(layoutIdStr, model))
            }
        }
        binding?.executePendingBindings()
    }

    open fun bindingVariableId(): Int? = null
    open fun additionalVariables(): Map<Int, Any>? = null
}