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

package io.github.naverz.antonio.databinding.holder

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.databinding.BR
import io.github.naverz.antonio.databinding.findLifecycleOwner
import java.lang.IllegalStateException

open class AntonioAutoBindingViewHolder(
    override val layoutId: Int,
    override val parent: ViewGroup,
    private val bindingVariableId: Int?,
    private val additionalVariables: Map<Int, Any>? = null,
    private val lifecycleOwner: LifecycleOwner? = null,
) : AntonioBindingViewHolder<ViewDataBinding, TypedModel>(layoutId, parent) {
    @Suppress("LeakingThis")
    private val mLifeCycleOwner: LifecycleOwner? =
        this.lifecycleOwner ?: findLifecycleOwner(parent)

    init {
        binding.setVariable(BR.itemView, itemView)
        binding.setVariable(BR.lifecycleOwner, mLifeCycleOwner)
        additionalVariables?.let {
            for (entry in additionalVariables) {
                binding.setVariable(entry.key, entry.value)
            }
        }
    }

    override fun onBindViewHolder(data: TypedModel, position: Int, payloads: List<Any>?) {
        bindingVariableId?.let {
            if (!binding.setVariable(it, data)) {
                //TODO Complete an exception message
                throw IllegalStateException()
            }
        }
        binding.setVariable(BR.absoluteAdapterPosition, absoluteAdapterPosition)
        binding.setVariable(BR.bindingAdapterPosition, bindingAdapterPosition)
        binding.setVariable(BR.layoutPosition, layoutPosition)
        binding.setVariable(BR.itemPosition, position)
        binding.executePendingBindings()
    }

    override fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        binding.lifecycleOwner = mLifeCycleOwner
    }

    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        binding.lifecycleOwner = null
    }


    override fun onViewRecycled() {
    }

}