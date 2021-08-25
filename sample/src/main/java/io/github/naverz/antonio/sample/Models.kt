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

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import io.github.naverz.antonio.BR
import io.github.naverz.antonio.R
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState
import io.github.naverz.antonio.databinding.AutoBindingModel


data class RecyclerViewGridWithRefresh<ITEM : TypedModel>(
    val recyclerViewState: RecyclerViewState<ITEM>,
    val onRefreshRequested: (() -> Unit)? = null
) : AutoBindingModel {
    override fun viewType() = R.layout.view_holder_recycler_view_grid_with_refresh
    override fun bindingVariableId(): Int = BR.parent
}

data class ViewHolderRecyclerViewVerticalLinearModel(val recyclerViewState: RecyclerViewState<TypedModel>) :
    AutoBindingModel {
    override fun viewType(): Int = R.layout.view_holder_recycler_view_vertical_linear
    override fun bindingVariableId(): Int =BR.parent
}

data class ViewHolderRecyclerViewHorizontalLinearModel<ITEM : TypedModel>(
    val submittableRecyclerViewState: SubmittableRecyclerViewState<ITEM>
) : AutoBindingModel {
    override fun viewType(): Int = R.layout.view_holder_recycler_view_horizontal_linear
    override fun bindingVariableId(): Int = BR.parent
}


data class ContentHeaderModel(val title: String) : AutoBindingModel {
    override fun viewType(): Int = R.layout.view_holder_content_header
    override fun bindingVariableId(): Int = BR.model
}

data class RankingContainerModel(val rankingList: List<ContentRankingModel>) : AutoBindingModel {
    override fun viewType(): Int = R.layout.view_holder_vertical_linear_container_ranking
    override fun bindingVariableId(): Int = BR.model
}

data class SmallContainerModel(val contents: List<ContentSmallModel>) : AutoBindingModel {
    override fun viewType(): Int = R.layout.view_holder_horizontal_linear_container_small_content
    override fun bindingVariableId(): Int = BR.model
}

data class ContentRankingModel(
    val ranking: Int,
    @DrawableRes val iconRes: Int,
    val name: String,
    val subContent: String,
    val onClick: () -> Unit
)

data class ContentSmallModel(
    val id: String,
    @DrawableRes val iconRes: Int,
    val price: Int,
    val onClick: (id: String) -> Unit,
    val onLongClick: (id: String) -> Boolean,
    val selectedIds: LiveData<Set<String>>
) : AutoBindingModel {
    override fun viewType(): Int = R.layout.view_holder_content_small
    override fun bindingVariableId(): Int = BR.model
}

class ViewPagerWithTabLayoutModel<T : TypedModel>(
    val viewPagerState: ViewPagerState<T>
) : TypedModel {
    override fun viewType(): Int = R.layout.view_holder_view_pager_with_tab_layout
}
