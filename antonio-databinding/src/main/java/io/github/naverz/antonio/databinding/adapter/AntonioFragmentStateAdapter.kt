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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter
import io.github.naverz.antonio.core.container.FragmentContainer
import io.github.naverz.antonio.databinding.AntonioBindingModel
import io.github.naverz.antonio.databinding.fragment.AntonioAutoBindingFragment

open class AntonioFragmentStateAdapter<ITEM : AntonioModel>
    : AntonioCoreFragmentStateAdapter<ITEM> {
    val additionalVariables: Map<Int, Any>?
    val lifecycleOwner: LifecycleOwner?

    /**
     * @param fragmentActivity if the ViewPager2 lives directly in a FragmentActivity subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param fragmentContainer It's for the custom container. you don need to use the custom container, if you use the container in AntonioSettings.
     * @param additionalVariables It's for the variables to be injected to XML when it's onViewCreated on the fragment.
     */
    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragmentActivity, implementedItemID, fragmentContainer) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = fragmentActivity
    }

    /**
     * @param fragment if the ViewPager2 lives directly in a Fragment subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param fragmentContainer It's for the custom container. you don need to use the custom container, if you use the container in AntonioSettings.
     * @param additionalVariables It's for the variables to be injected to XML when it's onViewCreated on the fragment.
     */
    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragment, implementedItemID, fragmentContainer) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = fragment.viewLifecycleOwner
    }

    /**
     * @param fragmentManager of ViewPager2's host
     * @param lifecycle of ViewPager2's host
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param fragmentContainer It's for the custom container. you don need to use the custom container, if you use the container in AntonioSettings.
     * @param additionalVariables It's for the variables to be injected to XML when it's onViewCreated on the fragment.
     */
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragmentManager, lifecycle, implementedItemID, fragmentContainer) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = null
    }

    /**
     * @param fragmentActivity if the ViewPager2 lives directly in a FragmentActivity subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param additionalVariables It's for the variables to be injected to XML when it's onViewCreated on the fragment.
     */
    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragmentActivity, implementedItemID) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = fragmentActivity
    }

    /**
     * @param fragment if the ViewPager2 lives directly in a Fragment subclass.
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param additionalVariables It's for the variables to be injected to XML when it's onViewCreated on the fragment.
     */
    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragment, implementedItemID) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = fragment.viewLifecycleOwner
    }

    /**
     * @param fragmentManager of ViewPager2's host
     * @param lifecycle of ViewPager2's host
     * @param implementedItemID If it's true, AntonioModel.modelId will be used when it's `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.getItemId` and `io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter.containsItem`
     * @param additionalVariables It's for the variables to be injected to XML when it's onViewCreated on the fragment.
     */
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        implementedItemID: Boolean,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragmentManager, lifecycle, implementedItemID) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = null
    }

    override fun createFragment(position: Int): Fragment {
        val item = currentList[position]
        if (item !is AntonioBindingModel) {
            return super.createFragment(position)
        }
        return InternalAutoBindingFragmentImpl(item, additionalVariables)
            .apply { setData(position, item) }
    }

    class InternalAutoBindingFragmentImpl(
        private val item: AntonioBindingModel,
        private val additionalVariables: Map<Int, Any>?
    ) : AntonioAutoBindingFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(item.layoutId(), container, false)
        }

        override fun bindingVariableId(): Int? = item.bindingVariableId()
        override fun additionalVariables(): Map<Int, Any>? = additionalVariables
    }
}
