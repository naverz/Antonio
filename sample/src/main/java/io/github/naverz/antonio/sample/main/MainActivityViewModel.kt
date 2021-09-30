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

package io.github.naverz.antonio.sample.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState
import io.github.naverz.antonio.sample.ContentBuilder
import io.github.naverz.antonio.sample.antonio.*
import io.github.naverz.antonio.sample.etc.HashDiffItemCallback
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val subItemTitles = listOf("All", "Hair", "Dress", "Eyes", "Bottom", "Top", "Face")
    private val headTitles = listOf("Recommend", "Fashion")
    private val selectedIds = MutableLiveData<Set<String>>(setOf())
    private val contentBuilder = ContentBuilder()
    private val _goToFragmentHostActivity = MutableSharedFlow<Unit>()
    val goToFragmentHostActivity = _goToFragmentHostActivity.asSharedFlow()

    private val horizontalState =
        SubmittableRecyclerViewState<SmallContent>(HashDiffItemCallback()).apply {
            submitList(
                contentBuilder.makeDummySmallContents(
                    count = 50,
                    onClick = ::onContentClicked,
                    onLongClick = ::onContentLongClicked,
                    selectedIds = selectedIds
                )
            )
        }

    private val flexHorizontalState =
        SubmittableRecyclerViewState<FlexWidthContent>(HashDiffItemCallback()).apply {
            submitList(contentBuilder.makeFlexWidthContents())
        }

    private val fashionPageState = RecyclerViewState<AntonioModel>().apply {
        currentList.add(ContentHeader("New items"))
        currentList.add(ViewHolderRecyclerViewHorizontal(horizontalState))
        currentList.add(ContentHeader("Flex Width Items"))
        currentList.add(ViewHolderRecyclerViewFlexHorizontal(flexHorizontalState))
        currentList.add(ContentHeader("Hot Items"))
        currentList.addAll(
            contentBuilder.makeDummyContainersForSmallContents(
                onClick = ::onContentClicked,
                onLongClick = ::onContentLongClicked,
                selectedIds = selectedIds
            )
        )
    }

    private val subItemPageState = ViewPagerState<RankingContainer>().apply {
        titles = subItemTitles
        currentList.addAll(
            listOf(
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity)),
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity)),
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity)),
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity)),
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity)),
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity)),
                RankingContainer(contentBuilder.makeDummyRankingContainers(::goToFragmentHostActivity))
            )
        )
    }
    private val recommendGridState = RecyclerViewState<SmallContent>().apply {
        currentList.addAll(
            contentBuilder.makeDummySmallContents(
                count = 8,
                onClick = ::onContentClicked,
                onLongClick = ::onContentLongClicked,
                selectedIds = selectedIds
            )
        )
    }
    private val recommendPageState = RecyclerViewState<AntonioModel>().apply {
        currentList.add(ContentHeader("Best items"))
        currentList.add(ViewPagerWithTabLayout(subItemPageState))
        currentList.add(ContentHeader("Grid items"))
        currentList.add(RecyclerViewGridWithRefresh(recommendGridState, ::onGridRefreshed))
    }


    val viewPagerState = ViewPagerState<AntonioModel>().apply {
        titles = headTitles
        currentList.add(ViewHolderRecyclerViewVertical(recommendPageState))
        currentList.add(ViewHolderRecyclerViewVertical(fashionPageState))
    }

    private fun goToFragmentHostActivity() {
        viewModelScope.launch {
            _goToFragmentHostActivity.emit(Unit)
        }
    }

    private fun onContentClicked(id: String) {
        viewModelScope.launch {
            selectedIds.value = HashSet(selectedIds.value ?: setOf()).apply { add(id) }
        }
    }

    private fun onContentLongClicked(id: String): Boolean {
        val currentSelectedId = selectedIds.value ?: return false
        return if (currentSelectedId.contains(id)) {
            selectedIds.value = HashSet(currentSelectedId).apply { remove(id) }
            true
        } else {
            false
        }
    }

    private fun onGridRefreshed() {
        recommendGridState.currentList.clear()
        recommendGridState.currentList.addAll(
            contentBuilder.makeDummySmallContents(
                count = 8,
                onClick = ::onContentClicked,
                onLongClick = ::onContentLongClicked,
                selectedIds = selectedIds
            )
        )
        recommendGridState.notifyDataSetChanged()
    }

}