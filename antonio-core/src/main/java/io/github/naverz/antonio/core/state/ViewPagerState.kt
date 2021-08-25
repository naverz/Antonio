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

import android.view.View
import androidx.annotation.RestrictTo
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.adapter.PagerAdapterDependency
import io.github.naverz.antonio.core.etc.MainThreadExecutor
import io.github.naverz.antonio.core.etc.ThreadChecker
import io.github.naverz.antonio.core.etc.ThreadCheckerImpl
import java.util.concurrent.Executor


open class ViewPagerState<ITEM : TypedModel> {
    var currentList = mutableListOf<ITEM>()

    @RestrictTo(RestrictTo.Scope.TESTS)
    var mainThreadExecutor: Executor = MainThreadExecutor()

    @RestrictTo(RestrictTo.Scope.TESTS)
    var threadChecker: ThreadChecker = ThreadCheckerImpl()
    var titles: List<CharSequence>? = null
        set(value) {
            pagerAdapterDependency?.titles = titles
            field = value
        }
    var isViewFromObject: (view: View, any: Any) -> Boolean = { view, any -> view == any }
        set(value) {
            pagerAdapterDependency?.isViewFromObject = value
            field = value
        }
    private var pagerAdapterDependency: PagerAdapterDependency<ITEM>? = null

    fun setAdapterDependency(adapterDependency: PagerAdapterDependency<ITEM>?) {
        if (adapterDependency == null) {
            pagerAdapterDependency?.isViewFromObject = { view, any -> view == any }
            pagerAdapterDependency?.itemList = mutableListOf()
            pagerAdapterDependency?.titles = null
        } else {
            adapterDependency.titles = titles
            adapterDependency.itemList = currentList
            adapterDependency.isViewFromObject = isViewFromObject
        }
        pagerAdapterDependency = adapterDependency
    }

    fun notifyDataSetChanged() {
        val runnable = Runnable { pagerAdapterDependency?.notifyDataSetChanged() }
        if (threadChecker.isMainThread()) runnable.run()
        else mainThreadExecutor.execute(runnable)
    }

}