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

package io.github.naverz.antonio.core.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.naverz.antonio.GenericAntonioFindable
import io.github.naverz.antonio.core.AntonioModel

open class TypedViewHolder<ITEM : AntonioModel>(itemView: View) :
    RecyclerView.ViewHolder(itemView), GenericAntonioFindable {

    open fun onBindViewHolder(data: ITEM, position: Int, payloads: List<Any>?) {
    }

    open fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    }

    open fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
    }

    open fun onViewRecycled() {
    }

}