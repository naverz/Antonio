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

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.naverz.antonio.core.FragmentBuilder
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.container.FragmentContainer
import io.github.naverz.antonio.core.throwClassCastExceptionForBinding
import io.github.naverz.antonio.core.fragment.AntonioFragment

abstract class AntonioCoreFragmentStateAdapter<ITEM : TypedModel>
    : FragmentStateAdapter, AdapterDependency<ITEM> {
    protected val implementedItemID: Boolean
    protected val fragmentContainer: FragmentContainer
    protected val fragmentManager: FragmentManager

    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer
    ) : super(fragmentActivity) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = fragmentContainer
        fragmentManager = fragmentActivity.supportFragmentManager
    }

    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer
    ) : super(fragment) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = fragmentContainer
        fragmentManager = fragment.childFragmentManager
    }

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer
    ) : super(fragmentManager, lifecycle) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = fragmentContainer
        this.fragmentManager = fragmentManager
    }

    override var currentList: MutableList<ITEM> = mutableListOf()

    override fun submitDiffResult(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }


    override fun getItemId(position: Int): Long {
        return if (implementedItemID)
            currentList[position].itemId
                ?: throw IllegalStateException("If you set the implementedItemID flag true, You must implement the itemId in your all of LayoutIdModels")
        else {
            super.getItemId(position)
        }
    }


    override fun containsItem(itemId: Long): Boolean {
        return if (implementedItemID) {
            currentList.any { it.itemId == itemId }
        } else {
            super.containsItem(itemId)
        }
    }

    override fun getItemCount(): Int = 2

    @Suppress("UNCHECKED_CAST")
    override fun createFragment(position: Int): Fragment {
        val data = currentList[position]
        val dependency =
            fragmentContainer.get(data.viewType())?.build() ?: throw IllegalStateException(
                "There is no related view holder dependency with the layout id[${currentList[position].viewType()}]"
            )

        return (dependency as AntonioFragment<ITEM>).apply {
            try {
                this.setData(position, data)
            } catch (e: ClassCastException) {
                this.throwClassCastExceptionForBinding(data)
            }
        }
    }
}