/**
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

package io.github.naverz.antonio.sample

import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.github.naverz.antonio.R
import io.github.naverz.antonio.core.AntonioSettings
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.ViewHolderBuilder
import io.github.naverz.antonio.core.holder.AntonioViewHolder
import io.github.naverz.antonio.databinding.setState

fun loadGlobalViewDependencyBuilders() {
    AntonioSettings.viewHolderContainer.addAll(mapOf(
        R.layout.view_holder_view_pager_with_tab_layout
                to ViewHolderBuilder { _, parent ->
            ViewHolderPagerWithTabLayout(parent)
        }
    ))
}

class ViewHolderPagerWithTabLayout(parent: ViewGroup) :
    AntonioViewHolder<ViewPagerWithTabLayoutModel<TypedModel>>(
        R.layout.view_holder_view_pager_with_tab_layout, parent
    ) {

    private val viewPager: ViewPager = itemView.findViewById(R.id.viewPager)

    init {
        val tabLayout: TabLayout = itemView.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBindViewHolder(
        data: ViewPagerWithTabLayoutModel<TypedModel>,
        position: Int,
        payloads: List<Any>?
    ) {
        viewPager.setState(data.viewPagerState)
    }
}