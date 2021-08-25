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
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.holder.AntonioViewHolder

abstract class AntonioBindingViewHolder<T : ViewDataBinding, ITEM : TypedModel>(
    override val layoutId: Int,
    override val parent: ViewGroup
) : AntonioViewHolder<ITEM>(layoutId, parent) {
    protected val binding: T = DataBindingUtil.bind(itemView)!!

    override fun onBindViewHolder(data: ITEM, position: Int, payloads: List<Any>?) {
    }

    override fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    }

    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
    }

    override fun onViewRecycled() {
    }
}