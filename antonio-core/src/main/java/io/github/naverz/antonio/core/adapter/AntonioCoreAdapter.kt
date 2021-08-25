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

package io.github.naverz.antonio.core.adapter

import android.content.res.Resources.NotFoundException
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.ViewHolderBuilder
import io.github.naverz.antonio.core.throwClassCastExceptionForBinding
import io.github.naverz.antonio.core.holder.TypedViewHolder

abstract class AntonioCoreAdapter<ITEM : TypedModel>(
    open val dependencyBuilderMap: Map<Int, ViewHolderBuilder>
) : AdapterDependency<ITEM>, RecyclerView.Adapter<TypedViewHolder<ITEM>>() {

    override var currentList: MutableList<ITEM> = mutableListOf()
    override fun submitDiffResult(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType()
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].itemId ?: super.getItemId(position)
    }

    override fun onViewRecycled(holder: TypedViewHolder<ITEM>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    override fun onViewAttachedToWindow(holder: TypedViewHolder<ITEM>) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: TypedViewHolder<ITEM>) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow(holder)
    }


    override fun onBindViewHolder(
        holder: TypedViewHolder<ITEM>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        try {
            holder.onBindViewHolder(currentList[position], position, payloads)
        } catch (e: ClassCastException) {
            holder.throwClassCastExceptionForBinding(currentList[position])
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypedViewHolder<ITEM> {
        val dependency = dependencyBuilderMap[viewType] ?: throw IllegalStateException(
            "There is no related view holder dependency with the view type[${
                try {
                    parent.context.resources.getResourceName(viewType)
                } catch (e: NotFoundException) {
                    viewType.toString()
                }
            }] in your dependencyBuilderMap [${dependencyBuilderMap}]"
        )
        val uncheckedHolder = dependency.build(viewType, parent)
        return (uncheckedHolder as TypedViewHolder<ITEM>)
    }

    override fun onBindViewHolder(holder: TypedViewHolder<ITEM>, position: Int) {
        try {
            holder.onBindViewHolder(currentList[position], position, null)
        } catch (e: ClassCastException) {
            holder.throwClassCastExceptionForBinding(currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}




