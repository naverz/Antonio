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

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import io.github.naverz.antonio.AntonioSettings
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.adapter.AntonioCoreAdapter
import io.github.naverz.antonio.core.container.ViewHolderContainer
import io.github.naverz.antonio.core.holder.TypedViewHolder

open class AntonioAdapter<ITEM : AntonioModel>(
    viewHolderContainer: ViewHolderContainer = AntonioSettings.viewHolderContainer,
    open val additionalVariables: Map<Int, Any>? = null,
    open val lifecycleOwner: LifecycleOwner? = null,
) : AntonioCoreAdapter<ITEM>(viewHolderContainer) {
    private val helper by lazy {
        DataBindingAdapterHelper<ITEM, TypedViewHolder<ITEM>>(additionalVariables, lifecycleOwner)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypedViewHolder<ITEM> {
        val holder = helper.createViewBindingIfIsAutoBindingModel(parent, viewType)
        return holder ?: super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return helper.findLayoutId(item) ?: super.getItemViewType(position)
    }
}