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

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.github.naverz.antonio.core.FragmentBuilder
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter
import io.github.naverz.antonio.core.container.FragmentContainer
import io.github.naverz.antonio.databinding.AutoBindingModel
import io.github.naverz.antonio.databinding.fragment.AntonioAutoBindingFragment

open class AntonioFragmentStateAdapter<ITEM : TypedModel>
    : AntonioCoreFragmentStateAdapter<ITEM> {
    val additionalVariables: Map<Int, Any>?
    val lifecycleOwner: LifecycleOwner?

    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragmentActivity, implementedItemID, fragmentContainer) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = fragmentActivity
    }

    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
        fragmentContainer: FragmentContainer,
        additionalVariables: Map<Int, Any>? = null
    ) : super(fragment, implementedItemID, fragmentContainer) {
        this.additionalVariables = additionalVariables
        this.lifecycleOwner = fragment.viewLifecycleOwner
    }

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

    override fun createFragment(position: Int): Fragment {
        val item = currentList[position]
        if (item !is AutoBindingModel) {
            return super.createFragment(position)
        }
        return InternalAutoBindingFragmentImpl(item, additionalVariables)
            .apply { setData(position, item) }
    }

    class InternalAutoBindingFragmentImpl(
        private val item: AutoBindingModel,
        private val additionalVariables: Map<Int, Any>?
    ) : AntonioAutoBindingFragment() {
        override fun layoutId(): Int = item.viewType()
        override fun bindingVariableId(): Int? = item.bindingVariableId()
        override fun additionalVariables(): Map<Int, Any>? = additionalVariables
    }
}
