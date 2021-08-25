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

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.naverz.antonio.core.TypedModel

interface AdapterDependency<ITEM : TypedModel> {
    var currentList: MutableList<ITEM>
    fun setStateRestorationPolicy(strategy: RecyclerView.Adapter.StateRestorationPolicy)
    fun notifyDataSetChanged()
    fun notifyItemChanged(position: Int, payload: Any? = null)
    fun notifyItemInserted(position: Int)
    fun notifyItemMoved(fromPosition: Int, toPosition: Int)
    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any? = null)
    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int)
    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int)
    fun notifyItemRemoved(position: Int)
    fun submitDiffResult(diffResult: DiffUtil.DiffResult)
}
