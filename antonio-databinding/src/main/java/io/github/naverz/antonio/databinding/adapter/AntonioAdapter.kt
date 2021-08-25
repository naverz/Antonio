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

package io.github.naverz.antonio.databinding.adapter

import android.view.InflateException
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.ViewHolderBuilder
import io.github.naverz.antonio.core.adapter.AntonioCoreAdapter
import io.github.naverz.antonio.core.holder.TypedViewHolder
import io.github.naverz.antonio.databinding.AutoBindingModel
import io.github.naverz.antonio.databinding.holder.AntonioAutoBindingViewHolder

open class AntonioAdapter<ITEM : TypedModel>(
    override val dependencyBuilderMap: Map<Int, ViewHolderBuilder>,
    open val additionalVariables: Map<Int, Any>? = null,
    open val lifecycleOwner: LifecycleOwner? = null,
) : AntonioCoreAdapter<ITEM>(dependencyBuilderMap) {
    private val autoBindingViewTypeMap = hashMapOf<Int, AutoBindingModel>()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypedViewHolder<ITEM> {
        val autoBindingModel = autoBindingViewTypeMap[viewType]
        if (autoBindingModel != null) {
            return try {
                AntonioAutoBindingViewHolder(
                    layoutId = autoBindingModel.viewType(),
                    parent = parent,
                    bindingVariableId = autoBindingModel.bindingVariableId(),
                    additionalVariables = additionalVariables,
                    lifecycleOwner = lifecycleOwner
                ) as TypedViewHolder<ITEM>
            } catch (e: InflateException) {
                throw InflateException(
                    "There is no related layout id with the view type you implemented, View type : ${autoBindingModel.viewType()}"
                )
            }
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        if (item is AutoBindingModel && !autoBindingViewTypeMap.containsKey(item.viewType())) {
            autoBindingViewTypeMap[item.viewType()] = item
        }
        return super.getItemViewType(position)
    }
}