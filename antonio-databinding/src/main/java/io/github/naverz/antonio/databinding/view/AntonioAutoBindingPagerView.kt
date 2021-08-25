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

import android.view.InflateException
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.databinding.findLifecycleOwner
import java.lang.IllegalStateException

open class AntonioAutoBindingPagerView(
    private val bindingVariableId: Int?,
    private val additionalVariables: Map<Int, Any>? = null,
    private val lifecycleOwner: LifecycleOwner? = null,
) : AntonioBindingPagerView<ViewDataBinding, TypedModel>() {

    private var mLifeCycleOwner: LifecycleOwner? = null

    override fun instantiateItem(container: ViewGroup, position: Int, typedModel: TypedModel): Any {
        try {
            return super.instantiateItem(container, position, typedModel)
        } catch (e: InflateException) {
            throw InflateException(
                "There is no related layout id with the view type you implemented, View type : ${typedModel.viewType()}]"
            )
        }
    }

    override fun onViewCreated(view: View, position: Int, typedModel: TypedModel) {
        super.onViewCreated(view, position, typedModel)
        mLifeCycleOwner = this.lifecycleOwner ?: findLifecycleOwner(view)
        additionalVariables?.let {
            for (entry in additionalVariables) {
                binding?.setVariable(entry.key, entry.value)
            }
        }
        binding?.lifecycleOwner = mLifeCycleOwner
        if (bindingVariableId != null) {
            if (binding?.setVariable(bindingVariableId, typedModel) == false) {
                //TODO Complete an exception message
                throw IllegalStateException()
            }
        }
        binding?.executePendingBindings()
    }

    override fun onDestroyedView(position: Int, typedModel: TypedModel) {
        super.onDestroyedView(position, typedModel)
        binding?.lifecycleOwner = null
    }
}