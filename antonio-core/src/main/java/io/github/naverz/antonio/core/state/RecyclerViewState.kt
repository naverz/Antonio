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
import io.github.naverz.antonio.AntonioSettings
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.adapter.AdapterDependency
import java.util.concurrent.Executor

open class RecyclerViewState<ITEM : AntonioModel> {

    var currentList = mutableListOf<ITEM>()
    private var hasStableIdLastState: Boolean? = null

    private var mainThreadExecutor: Executor = AntonioSettings.getExecutorBuilder().call()

    var adapterDependency: AdapterDependency<ITEM>? = null
        private set
    private var strategyForStore: RecyclerView.Adapter.StateRestorationPolicy? = null

    @RestrictTo(RestrictTo.Scope.TESTS)
    fun setMainThreadExecutor(executor: Executor) {
        mainThreadExecutor = executor
    }

    fun setStateRestorationPolicy(strategy: RecyclerView.Adapter.StateRestorationPolicy) {
        val runnable = Runnable {
            this.strategyForStore = strategy
            this.adapterDependency?.setStateRestorationPolicy(strategy)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun setHasStableIds(enable: Boolean) {
        val runnable = Runnable {
            hasStableIdLastState = enable
            adapterDependency?.setHasStableIds(enable)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun setAdapterDependency(adapterDependency: AdapterDependency<ITEM>?) {
        if (adapterDependency == null) {
            this.adapterDependency?.currentList = mutableListOf()
        } else {
            strategyForStore?.let { adapterDependency.setStateRestorationPolicy(it) }
            hasStableIdLastState?.let { adapterDependency.setHasStableIds(it) }
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
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemChanged(position: Int, payload: Any? = null) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemChanged(position, payload)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemInserted(position: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemInserted(position)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemMoved(fromPosition: Int, toPosition: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemMoved(fromPosition, toPosition)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any? = null) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRangeChanged(positionStart, itemCount, payloads)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRangeInserted(positionStart, itemCount)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRangeRemoved(positionStart, itemCount)
        }
        mainThreadExecutor.execute(runnable)
    }

    fun notifyItemRemoved(position: Int) {
        val runnable = Runnable {
            val diffAdapterDependency = adapterDependency ?: return@Runnable
            diffAdapterDependency.currentList = currentList
            diffAdapterDependency.notifyItemRemoved(position)
        }
        mainThreadExecutor.execute(runnable)
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
        mainThreadExecutor.execute(runnable)
    }
}