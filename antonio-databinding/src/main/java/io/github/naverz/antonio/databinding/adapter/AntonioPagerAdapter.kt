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
import io.github.naverz.antonio.core.PagerViewDependencyBuilder
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.adapter.AntonioCorePagerAdapter
import io.github.naverz.antonio.core.container.ViewPagerContainer
import io.github.naverz.antonio.core.view.PagerViewDependency
import io.github.naverz.antonio.databinding.AutoBindingModel
import io.github.naverz.antonio.databinding.view.AntonioAutoBindingPagerView

open class AntonioPagerAdapter<ITEM : TypedModel>(
    override val viewPagerContainer: ViewPagerContainer,
    open val additionalVariables: Map<Int, Any>? = null,
    open val lifecycleOwner: LifecycleOwner? = null,
) : AntonioCorePagerAdapter<ITEM>(viewPagerContainer) {

    @Suppress("UNCHECKED_CAST")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = itemList[position]
        if (item !is AutoBindingModel)
            return super.instantiateItem(container, position)

        var dependency = positionToDependency[position]
        if (dependency == null) {
            dependency = (AntonioAutoBindingPagerView(
                item.bindingVariableId(),
                additionalVariables,
                lifecycleOwner
            ) as PagerViewDependency<ITEM>).apply {
                positionToDependency[position] = this
            }
        }
        return dependency.instantiateItem(container, position, item)
    }
}