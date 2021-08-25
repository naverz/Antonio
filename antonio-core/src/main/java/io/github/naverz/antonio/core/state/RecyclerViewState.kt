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
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.etc.MainThreadExecutor
import io.github.naverz.antonio.core.etc.ThreadChecker
import io.github.naverz.antonio.core.etc.ThreadCheckerImpl
import io.github.naverz.antonio.core.adapter.AdapterDependency
import java.util.concurrent.Executor

open class RecyclerViewState<ITEM : TypedModel> {

    var currentList = mutableListOf<ITEM>()

    @RestrictTo(RestrictTo.Scope.TESTS)
    var mainThreadExecutor: Executor = MainThreadExecutor()

    @RestrictTo(RestrictTo.Scope.TESTS)
    var threadChecker: ThreadChecker = ThreadCheckerImpl()
    private var adapterDependency: AdapterDependency<ITEM>? = null
    private var strategyForStore: RecyclerView.Adapter.StateRestorationPolicy? = null


    fun setStateRestorationPolicy(strategy: RecyclerView.Adapter.StateRestorationPolicy) {
        val runnable = Runnable {
            this.strategyForStore = strategy
            this.adapterDependency?.setStateRestorationPolicy(strategy)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun setAdapterDependency(adapterDependency: AdapterDependency<ITEM>?) {
        if (adapterDependency == null) {
            this.adapterDependency?.currentList = mutableListOf()
        } else {
            strategyForStore?.let { adapterDependency.setStateRestorationPolicy(it) }
            adapterDependency.currentList = currentList
        }
        this.adapterDependency = adapterDependency
    }

    fun notifyDataSetChanged() {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyDataSetChanged()
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemChanged(position: Int, payload: Any? = null) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemChanged(position, payload)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemInserted(position: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemInserted(position)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemMoved(fromPosition: Int, toPosition: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemMoved(fromPosition, toPosition)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any? = null) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRangeChanged(positionStart, itemCount, payloads)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRangeInserted(positionStart, itemCount)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRangeRemoved(positionStart, itemCount)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }

    fun notifyItemRemoved(position: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRemoved(position)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }


    /**
     * @param diffResult Difference for notifying
     *
     * YOU MUST CHANGE THE CURRENT LIST BEFORE SUBMIT DIFF
     */
    fun submitDiff(diffResult: DiffUtil.DiffResult) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.submitDiffResult(diffResult)
        }
        if (threadChecker.isMainThread()) {
            runnable.run()
        } else {
            mainThreadExecutor.execute(runnable)
        }
    }
}