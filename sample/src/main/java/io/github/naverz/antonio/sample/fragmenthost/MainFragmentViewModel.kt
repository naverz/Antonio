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

package io.github.naverz.antonio.sample.fragmenthost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.sample.ContentBuilder
import io.github.naverz.antonio.sample.antonio.ContentHeader
import io.github.naverz.antonio.sample.antonio.ViewHolderRecyclerViewHorizontal
import io.github.naverz.antonio.sample.antonio.ViewHolderRecyclerViewVertical
import io.github.naverz.antonio.sample.etc.HashDiffItemCallback
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {
    val headTitles = listOf("Recommend", "Fashion")

    private val _goToSecondFragment = MutableSharedFlow<Unit>()
    val goToSecondFragment = _goToSecondFragment.asSharedFlow()
    private val contentBuilder = ContentBuilder()

    private val horizontalState =
        SubmittableRecyclerViewState(HashDiffItemCallback()).apply {
            submitList(contentBuilder.makeDummySmallContents(50, onClick = ::onContentClicked))
        }
    private val fashionPageState = RecyclerViewState<AntonioModel>().apply {
        currentList.add(ContentHeader("New items"))
        currentList.add(ViewHolderRecyclerViewHorizontal(horizontalState))
        currentList.add(ContentHeader("Hot Items"))
        currentList.addAll(contentBuilder.makeDummyContainersForSmallContents(onClick = ::onContentClicked))
    }

    private val recommendPageState = RecyclerViewState<AntonioModel>().apply {
        currentList.add(ContentHeader("Easyyyy"))
        currentList.addAll(contentBuilder.makeDummyContainersForSmallContents(onClick = ::onContentClicked))
    }

    val viewPager2State = RecyclerViewState<AntonioModel>().apply {
        currentList.add(ViewHolderRecyclerViewVertical(recommendPageState))
        currentList.add(ViewHolderRecyclerViewVertical(fashionPageState))
    }

    private fun onContentClicked(id: String) {
        viewModelScope.launch { _goToSecondFragment.emit(Unit) }
    }

}