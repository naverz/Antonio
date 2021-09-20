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
import io.github.naverz.antonio.AntonioSettings
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.Exceptions
import io.github.naverz.antonio.core.container.ContainerType
import io.github.naverz.antonio.core.container.PagerViewContainer
import io.github.naverz.antonio.core.view.PagerViewDependency

abstract class AntonioCorePagerAdapter<ITEM : AntonioModel>(
    open val pagerViewContainer: PagerViewContainer = AntonioSettings.pagerViewContainer,
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
            val buildDependency = ((pagerViewContainer.get(item::class.java)?.build()
                ?: throw IllegalStateException(
                    Exceptions.errorRegisterFirst(item::class.java, ContainerType.PAGER_VIEW)
                )) as PagerViewDependency<ITEM>)
            dependency = buildDependency
            positionToDependency[position] = buildDependency
        }
        val viewType = pagerViewContainer.getViewType(item::class.java)
            ?: throw IllegalStateException(Exceptions.ERROR_TEXT_UNKNOWN)
        return try {
            dependency.instantiateItem(container, position, viewType, item)
        } catch (e: ClassCastException) {
            throw IllegalStateException(
                Exceptions.errorClassCast(item, dependency), e
            )
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
