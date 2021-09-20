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

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState
import io.github.naverz.antonio.databinding.setState


@BindingAdapter("setStateWithAutoBinding")
fun <T : AntonioModel> RecyclerView.setRecyclerState(submittableRecyclerViewState: SubmittableRecyclerViewState<T>?) {
    this.setState(submittableRecyclerViewState)
}


@BindingAdapter("setStateWithAutoBinding")
fun <T : AntonioModel> RecyclerView.setRecyclerState(recyclerViewState: RecyclerViewState<T>?) {
    this.setState(recyclerViewState)
}

@BindingAdapter("setState")
fun <T : AntonioModel> ViewPager.setPagerState(pagerState: ViewPagerState<T>?) {
    this.setState(pagerState)
}


@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(visible: Boolean?) {
    visible ?: return
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
