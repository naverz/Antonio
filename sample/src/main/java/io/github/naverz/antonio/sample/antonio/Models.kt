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

package io.github.naverz.antonio.sample.antonio

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import io.github.naverz.antonio.BR
import io.github.naverz.antonio.R
import io.github.naverz.antonio.annotations.MappedWithViewDependency
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState
import io.github.naverz.antonio.databinding.AntonioBindingModel


data class RecyclerViewGridWithRefresh<ITEM : AntonioModel>(
    val recyclerViewState: RecyclerViewState<ITEM>,
    val onRefreshRequested: (() -> Unit)? = null
) : AntonioBindingModel {
    override fun layoutId() = R.layout.view_holder_recycler_view_grid_with_refresh
    override fun bindingVariableId(): Int = BR.parent
}

data class ViewHolderRecyclerViewVertical(val recyclerViewState: RecyclerViewState<AntonioModel>) :
    AntonioBindingModel {
    override fun layoutId(): Int = R.layout.view_holder_recycler_view_vertical_linear
    override fun bindingVariableId(): Int = BR.parent
}

data class ViewHolderRecyclerViewHorizontal<ITEM : AntonioModel>(
    val submittableRecyclerViewState: SubmittableRecyclerViewState<ITEM>
) : AntonioBindingModel {
    override fun layoutId(): Int = R.layout.view_holder_recycler_view_horizontal_linear
    override fun bindingVariableId(): Int = BR.parent
}


data class ContentHeader(val title: String) : AntonioBindingModel {
    override fun layoutId(): Int = R.layout.view_holder_content_header
    override fun bindingVariableId(): Int = BR.model
}

data class RankingContainer(val rankingList: List<ContentRanking>) : AntonioBindingModel {
    data class ContentRanking(
        val ranking: Int,
        @DrawableRes val iconRes: Int,
        val name: String,
        val subContent: String,
        val onClick: () -> Unit
    )

    override fun layoutId(): Int = R.layout.view_holder_vertical_linear_container_ranking
    override fun bindingVariableId(): Int = BR.model
}

data class ContainerForSmallContents(val contents: List<SmallContent>) : AntonioBindingModel {
    override fun layoutId(): Int = R.layout.view_holder_horizontal_linear_container_small_content
    override fun bindingVariableId(): Int = BR.model
}

data class SmallContent(
    val id: String,
    @DrawableRes val iconRes: Int,
    val price: Int,
    val onClick: (id: String) -> Unit,
    val onLongClick: (id: String) -> Boolean,
    val selectedIds: LiveData<Set<String>>?
) : AntonioBindingModel {
    override fun layoutId(): Int = R.layout.view_holder_content_small
    override fun bindingVariableId(): Int = BR.model
}

@MappedWithViewDependency(viewClass = ViewHolderPagerWithTabLayout::class)
class ViewPagerWithTabLayout<T : AntonioModel>(val viewPagerState: ViewPagerState<T>) :
    AntonioModel
