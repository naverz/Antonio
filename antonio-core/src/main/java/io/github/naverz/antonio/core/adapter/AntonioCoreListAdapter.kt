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

import android.content.res.Resources
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.github.naverz.antonio.AntonioSettings
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.Exceptions
import io.github.naverz.antonio.core.container.ContainerType
import io.github.naverz.antonio.core.container.ViewHolderContainer
import io.github.naverz.antonio.core.holder.TypedViewHolder


abstract class AntonioCoreListAdapter<ITEM : AntonioModel>(
    diffItemCallback: DiffUtil.ItemCallback<ITEM>,
    open val viewHolderContainer: ViewHolderContainer = AntonioSettings.viewHolderContainer
) : ListAdapterDependency<ITEM>, ListAdapter<ITEM, TypedViewHolder<ITEM>>(diffItemCallback) {

    override fun getItemViewType(position: Int): Int {
        return viewHolderContainer.getViewType(currentList[position]::class.java)
            ?: throw IllegalStateException(
                Exceptions.errorRegisterFirst(
                    currentList[position]::class.java, ContainerType.VIEW_HOLDER
                )
            )
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].modelId ?: super.getItemId(position)
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
            throw IllegalStateException(
                Exceptions.errorClassCast(currentList[position], holder), e
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypedViewHolder<ITEM> {
        val dependency = viewHolderContainer.get(viewType) ?: throw IllegalStateException(
            "There is no related view holder dependency with the view type[${
                try {
                    parent.context.resources.getResourceName(viewType)
                } catch (e: Resources.NotFoundException) {
                    viewType.toString()
                }
            }] in your viewHolderContainer [${viewHolderContainer}]"
        )
        val uncheckedHolder = dependency.build(parent)
        return (uncheckedHolder as TypedViewHolder<ITEM>)
    }

    override fun onBindViewHolder(holder: TypedViewHolder<ITEM>, position: Int) {
        try {
            holder.onBindViewHolder(currentList[position], position, null)
        } catch (e: ClassCastException) {
            throw IllegalStateException(
                Exceptions.errorClassCast(currentList[position], holder), e
            )
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}


