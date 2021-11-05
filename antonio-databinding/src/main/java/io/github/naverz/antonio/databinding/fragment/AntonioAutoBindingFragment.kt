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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.Exceptions
import io.github.naverz.antonio.databinding.AntonioBindingModel

class AntonioAutoBindingFragment :
    AntonioBindingFragment<ViewDataBinding, AntonioModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            (requireModel() as AntonioBindingModel).layoutId(), container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = viewLifecycleOwner
        val model = requireModel() as AntonioBindingModel
        model.bindingVariableId()?.let { bindingVariableId ->
            if (binding?.setVariable(bindingVariableId, model) == false) {
                val layoutIdStr =
                    view.context.resources.getResourceName(model.layoutId())
                throw IllegalStateException(Exceptions.errorIllegalBinding(layoutIdStr, model))
            }
            binding?.executePendingBindings()
        }
    }

}