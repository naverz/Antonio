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
package io.github.naverz.antonio.databinding.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.Exceptions
import io.github.naverz.antonio.databinding.AntonioBindingModel
import io.github.naverz.antonio.findLifecycleOwner

open class AntonioAutoBindingPagerView(
    private val bindingVariableId: Int?,
    private val additionalVariables: Map<Int, Any>? = null,
    private val lifecycleOwner: LifecycleOwner? = null,
) : AntonioBindingPagerView<ViewDataBinding, AntonioModel>() {

    private var mLifeCycleOwner: LifecycleOwner? = null
    override fun getView(
        container: ViewGroup,
        position: Int,
        viewType: Int,
        antonioModel: AntonioModel
    ): View {
        return LayoutInflater.from(container.context).inflate(viewType, container, false)
    }

    override fun onViewCreated(view: View, position: Int, antonioModel: AntonioModel) {
        super.onViewCreated(view, position, antonioModel)
        mLifeCycleOwner = this.lifecycleOwner ?: findLifecycleOwner(view)
        additionalVariables?.let {
            for (entry in additionalVariables) {
                binding?.setVariable(entry.key, entry.value)
            }
        }
        binding?.lifecycleOwner = mLifeCycleOwner
        if (bindingVariableId != null) {
            if (binding?.setVariable(bindingVariableId, antonioModel) == false) {
                val layoutIdStr =
                    view.context.resources.getResourceName((antonioModel as AntonioBindingModel).layoutId())
                throw IllegalStateException(
                    Exceptions.errorIllegalBinding(layoutIdStr, antonioModel)
                )
            }
        }
        if (antonioModel is AntonioBindingModel && antonioModel.requireExecutePendingBindings())
            binding?.executePendingBindings()
    }

    override fun onDestroyedView(position: Int, antonioModel: AntonioModel) {
        super.onDestroyedView(position, antonioModel)
        binding?.lifecycleOwner = null
    }
}