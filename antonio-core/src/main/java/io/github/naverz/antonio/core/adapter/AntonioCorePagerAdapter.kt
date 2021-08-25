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

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import io.github.naverz.antonio.core.PagerViewDependencyBuilder
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.throwClassCastExceptionForBinding
import io.github.naverz.antonio.core.view.PagerViewDependency
import java.lang.ClassCastException

abstract class AntonioCorePagerAdapter<ITEM : TypedModel>(
    open val dependencyBuilderMap: Map<Int, PagerViewDependencyBuilder>,
) : PagerAdapter(), PagerAdapterDependency<ITEM> {
    protected val positionToDependency = mutableMapOf<Int, PagerViewDependency<ITEM>>()
    override var itemList: MutableList<ITEM> = mutableListOf()
    override var titles: List<CharSequence>? = null
    override var isViewFromObject: (view: View, any: Any) -> Boolean = { view, any ->
        view == any
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return isViewFromObject.invoke(view, any)
    }

    @Suppress("UNCHECKED_CAST")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = itemList[position]
        var dependency = positionToDependency[position]
        if (dependency == null) {
            dependency = ((dependencyBuilderMap[item.viewType()]?.build()
                ?: throw IllegalStateException(
                    "There is no related view holder dependency with the layout id[${
                        container.context.resources.getResourceName(item.viewType())
                    }]"
                )) as PagerViewDependency<ITEM>).also { buildDependency ->
                positionToDependency[position] = buildDependency
            }
        }
        return try {
            dependency.instantiateItem(container, position, item)
        } catch (e: ClassCastException) {
            dependency.throwClassCastExceptionForBinding(item)
        }
    }


    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        val item = itemList[position]
        positionToDependency[position]?.destroyItem(container, position, item, any)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.getOrNull(position) ?: super.getPageTitle(position)
    }

    override fun getPageWidth(position: Int): Float {
        val item = itemList[position]
        return positionToDependency[position]?.getPageWidth(position, item)
            ?: super.getPageWidth(position)
    }
}
