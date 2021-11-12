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
import io.github.naverz.antonio.AntonioSettings
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.Exceptions
import io.github.naverz.antonio.core.container.ContainerType
import io.github.naverz.antonio.core.container.FragmentContainer
import io.github.naverz.antonio.core.fragment.AntonioFragment

abstract class AntonioCoreFragmentStateAdapter<ITEM : AntonioModel>
    : FragmentStateAdapter, AdapterDependency<ITEM> {
    protected val implementedItemID: Boolean
    protected val fragmentContainer: FragmentContainer
    protected val fragmentManager: FragmentManager

    /**
     * @param fragmentActivity if the ViewPager2 lives directly in a FragmentActivity subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param fragmentContainer It's for the custom container. you don need to use the custom container, if you use the container in AntonioSettings.
     */
    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer
    ) : super(fragmentActivity) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = fragmentContainer
        fragmentManager = fragmentActivity.supportFragmentManager
    }

    /**
     * @param fragment if the ViewPager2 lives directly in a Fragment subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param fragmentContainer It's for the custom container. you don need to use the custom container, if you use the container in AntonioSettings.
     */
    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer
    ) : super(fragment) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = fragmentContainer
        fragmentManager = fragment.childFragmentManager
    }

    /**
     * @param fragmentManager of ViewPager2's host
     * @param lifecycle of ViewPager2's host
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param fragmentContainer It's for the custom container. you don need to use the custom container, if you use the container in AntonioSettings.
     */
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

    /**
     * @param fragmentActivity if the ViewPager2 lives directly in a FragmentActivity subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     */
    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
    ) : super(fragmentActivity) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = AntonioSettings.fragmentContainer
        fragmentManager = fragmentActivity.supportFragmentManager
    }

    /**
     * @param fragment if the ViewPager2 lives directly in a Fragment subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     */
    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
    ) : super(fragment) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = AntonioSettings.fragmentContainer
        fragmentManager = fragment.childFragmentManager
    }

    /**
     * @param fragmentManager of ViewPager2's host
     * @param lifecycle of ViewPager2's host
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     */
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        implementedItemID: Boolean,
    ) : super(fragmentManager, lifecycle) {
        this.implementedItemID = implementedItemID
        this.fragmentContainer = AntonioSettings.fragmentContainer
        this.fragmentManager = fragmentManager
    }

    override var currentList: List<ITEM> = mutableListOf()

    override fun submitDiffResult(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }


    override fun getItemId(position: Int): Long {
        return if (implementedItemID)
            currentList[position].modelId
                ?: throw IllegalStateException("If you set the implementedItemID flag true, You must implement itemId in your all of AntonioModel")
        else {
            super.getItemId(position)
        }
    }


    override fun containsItem(itemId: Long): Boolean {
        return if (implementedItemID) {
            currentList.any { it.modelId == itemId }
        } else {
            super.containsItem(itemId)
        }
    }

    override fun getItemCount(): Int = currentList.size

    @Suppress("UNCHECKED_CAST")
    override fun createFragment(position: Int): Fragment {
        val data = currentList[position]
        val dependency =
            fragmentContainer.get(data::class.java)?.build() ?: throw IllegalStateException(
                Exceptions.errorRegisterFirst(data::class.java, ContainerType.FRAGMENT)
            )

        return (dependency as AntonioFragment<ITEM>).apply {
            try {
                this.setData(position, data)
            } catch (e: ClassCastException) {
                throw IllegalStateException(Exceptions.errorClassCast(data, this), e)
            }
        }
    }
}