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

package io.github.naverz.antonio.core.state

import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.etc.MainThreadExecutor
import io.github.naverz.antonio.core.etc.ThreadChecker
import io.github.naverz.antonio.core.etc.ThreadCheckerImpl
import io.github.naverz.antonio.core.adapter.ListAdapterDependency
import java.util.concurrent.Executor


open class SubmittableRecyclerViewState<ITEM : AntonioModel>(val itemCallback: DiffUtil.ItemCallback<ITEM>) {

    private var listForStore: List<ITEM>? = null
    private var strategyForStore: RecyclerView.Adapter.StateRestorationPolicy? = null
    private var hasStableIdLastState: Boolean? = null

    @RestrictTo(RestrictTo.Scope.TESTS)
    var mainThreadExecutor: Executor = MainThreadExecutor()

    @RestrictTo(RestrictTo.Scope.TESTS)
    var threadChecker: ThreadChecker = ThreadCheckerImpl()

    private var listAdapterDependency: ListAdapterDependency<ITEM>? = null

    fun setStateRestorationPolicy(strategy: RecyclerView.Adapter.StateRestorationPolicy) {
        val runnable = Runnable {
            this.strategyForStore = strategy
            this.listAdapterDependency?.setStateRestorationPolicy(strategy)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun setAdapterDependency(listAdapterDependency: ListAdapterDependency<ITEM>?) {
        if (listAdapterDependency == null) {
            this.listAdapterDependency?.submitList(null)
        } else {
            strategyForStore?.let { listAdapterDependency.setStateRestorationPolicy(it) }
            hasStableIdLastState?.let { listAdapterDependency.setHasStableIds(it) }
            listForStore?.let { listAdapterDependency.submitList(it) }
        }
        this.listAdapterDependency = listAdapterDependency
    }

    fun getCurrentItems(): List<ITEM> {
        return listForStore ?: listOf()
    }

    fun setHasStableIds(enable: Boolean) {
        val runnable = Runnable {
            hasStableIdLastState = enable
            listAdapterDependency?.setHasStableIds(enable)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyDataSetChanged() {
        val runnable = Runnable {
            listAdapterDependency?.notifyDataSetChanged()
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemChanged(position: Int, payload: Any? = null) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemChanged(position, payload)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemInserted(position: Int) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemInserted(position)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemMoved(fromPosition: Int, toPosition: Int) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemMoved(fromPosition, toPosition)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any? = null) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemRangeChanged(positionStart, itemCount, payloads)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemRangeInserted(positionStart, itemCount)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemRangeRemoved(positionStart, itemCount)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRemoved(position: Int) {
        val runnable = Runnable {
            listAdapterDependency?.notifyItemRemoved(position)
        }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

    fun submitList(list: List<ITEM>?, commitCallback: Runnable? = null) {
        listForStore = list
        val runnable = Runnable { listAdapterDependency?.submitList(list, commitCallback) }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }
}