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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import io.github.naverz.antonio.R
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState
import io.github.naverz.antonio.sample.*
import kotlin.random.Random

private const val ICON = R.drawable.ic_launcher_background

class MainViewModel : ViewModel() {
    private val subItemTitles = listOf("All", "Hair", "Dress", "Eyes", "Bottom", "Top", "Face")
    private val headTitles = listOf("Recommend", "Fashion")
    private val selectedIds = MutableLiveData<Set<String>>(setOf())

    private val _goToFragmentHostActivity = MutableSharedFlow<Unit>()
    val goToFragmentHostActivity = _goToFragmentHostActivity.asSharedFlow()

    private val horizontalState =
        SubmittableRecyclerViewState<ContentSmallModel>(HashDiffItemCallback()).apply {
            submitList(makeDummySmallContents(50))
        }
    private val fashionPageState = RecyclerViewState<TypedModel>().apply {
        currentList.add(ContentHeaderModel("New items"))
        currentList.add(ViewHolderRecyclerViewHorizontalLinearModel(horizontalState))
        currentList.add(ContentHeaderModel("Hot Items"))
        currentList.addAll(makeDummySmall4ColumnContents())
    }

    private val subItemPageState = ViewPagerState<RankingContainerModel>().apply {
        titles = subItemTitles
        currentList.addAll(
            listOf(
                RankingContainerModel(makeDummyRankingModel()),
                RankingContainerModel(makeDummyRankingModel()),
                RankingContainerModel(makeDummyRankingModel()),
                RankingContainerModel(makeDummyRankingModel()),
                RankingContainerModel(makeDummyRankingModel()),
                RankingContainerModel(makeDummyRankingModel()),
                RankingContainerModel(makeDummyRankingModel())
            )
        )
    }
    private val recommendGridState = RecyclerViewState<ContentSmallModel>().apply {
        currentList.addAll(makeDummySmallContents(8))
    }
    private val recommendPageState = RecyclerViewState<TypedModel>().apply {
        currentList.add(ContentHeaderModel("Best items"))
        currentList.add(ViewPagerWithTabLayoutModel(subItemPageState))
        currentList.add(ContentHeaderModel("Grid items"))
        currentList.add(RecyclerViewGridWithRefresh(recommendGridState, ::onGridRefreshed))
    }


    val viewPagerState = ViewPagerState<TypedModel>().apply {
        titles = headTitles
        currentList.add(ViewHolderRecyclerViewVerticalLinearModel(recommendPageState))
        currentList.add(ViewHolderRecyclerViewVerticalLinearModel(fashionPageState))
    }


    private fun makeDummyRankingModel(): List<ContentRankingModel> {
        return (1..6).map { index ->
            ContentRankingModel(
                index,
                ICON,
                "$index name",
                "$index sub content"
            ) {
                viewModelScope.launch {
                    _goToFragmentHostActivity.emit(Unit)
                }
            }
        }
    }

    private fun makeDummySmall4ColumnContents(): List<SmallContainerModel> {
        val random = Random(System.currentTimeMillis())
        return (1..1000).map { index ->
            makeDummySmallContent(index.toString(), random.nextInt(100, 1000))
        }.chunked(4).map { SmallContainerModel(it) }
    }

    private fun makeDummySmallContents(
        count: Int = Random(System.currentTimeMillis()).nextInt(4, 12)
    ): List<ContentSmallModel> {
        val random = Random(System.currentTimeMillis())
        val max = random.nextInt(3, count)
        return (1..max).map { index ->
            makeDummySmallContent(index.toString(), random.nextInt(100, 1000))
        }
    }

    private fun makeDummySmallContent(id: String, price: Int): ContentSmallModel {
        return ContentSmallModel(
            id,
            ICON,
            price,
            { clickedId ->
                selectedIds.value = HashSet(selectedIds.value ?: setOf()).apply { add(clickedId) }
            },
            onLongClick@{
                val currentSelectedId = selectedIds.value ?: return@onLongClick false
                if (currentSelectedId.contains(it)) {
                    selectedIds.value = HashSet(currentSelectedId).apply { remove(it) }
                    true
                } else {
                    false
                }
            },
            selectedIds
        )
    }

    private fun onGridRefreshed() {
        recommendGridState.currentList.clear()
        recommendGridState.currentList.addAll(makeDummySmallContents(8))
        recommendGridState.notifyDataSetChanged()
    }

}