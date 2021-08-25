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

package io.github.naverz.antonio.databinding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.github.naverz.antonio.core.*
import io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter
import io.github.naverz.antonio.core.adapter.AntonioCorePagerAdapter
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState
import io.github.naverz.antonio.databinding.adapter.AntonioAdapter
import io.github.naverz.antonio.databinding.adapter.AntonioFragmentStateAdapter
import io.github.naverz.antonio.databinding.adapter.AntonioListAdapter
import io.github.naverz.antonio.databinding.adapter.AntonioPagerAdapter

//region Make adapter for the recycler view
fun <T : TypedModel> RecyclerViewState<T>.makeAdapter(
    lifecycleOwner: LifecycleOwner,
    dependencyBuilderMap: Map<Int, ViewHolderBuilder> = AntonioSettings.globalViewHolderDependencyBuilderMap,
    additionalVariables: Map<Int, Any>? = null
): RecyclerView.Adapter<*> {
    val adapter = AntonioAdapter<T>(
        dependencyBuilderMap = dependencyBuilderMap,
        additionalVariables = additionalVariables,
        lifecycleOwner = lifecycleOwner
    )
    setAdapterDependency(adapter)
    lifecycleOwner.onDestroy {
        setAdapterDependency(null)
    }
    return adapter
}

fun <T : TypedModel> SubmittableRecyclerViewState<T>.makeAdapter(
    lifecycleOwner: LifecycleOwner,
    dependencyBuilderMap: Map<Int, ViewHolderBuilder> = AntonioSettings.globalViewHolderDependencyBuilderMap,
    additionalVariables: Map<Int, Any>? = null
): RecyclerView.Adapter<*> {
    val adapter = AntonioListAdapter(
        dependencyBuilderMap = dependencyBuilderMap,
        diffItemCallback = itemCallback,
        additionalVariables = additionalVariables,
        lifecycleOwner = lifecycleOwner
    )
    setAdapterDependency(adapter)
    lifecycleOwner.onDestroy {
        setAdapterDependency(null)
    }
    return adapter
}
//endregion

//region setState for the recycler view
fun <T : TypedModel> RecyclerView.setState(
    viewState: RecyclerViewState<T>, adapter: AntonioAdapter<T>
) {
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    lifecycleOwner.onDestroy {
        this.adapter = null
        viewState.setAdapterDependency(null)
    }
    viewState.setAdapterDependency(adapter)
    this.adapter = adapter
}

fun <T : TypedModel> RecyclerView.setState(
    viewState: SubmittableRecyclerViewState<T>, adapter: AntonioListAdapter<T>
) {
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    lifecycleOwner.onDestroy {
        this.adapter = null
        viewState.setAdapterDependency(null)
    }
    viewState.setAdapterDependency(adapter)
    this.adapter = adapter
}

fun <T : TypedModel> RecyclerView.setState(
    viewState: RecyclerViewState<T>?,
    dependencyBuilderMap: Map<Int, ViewHolderBuilder> = AntonioSettings.globalViewHolderDependencyBuilderMap,
    additionalVariables: Map<Int, Any>? = null,
    hasStableId: Boolean = false
) {
    if (viewState == null) {
        adapter = null
        return
    }
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    this.setState(
        viewState,
        AntonioAdapter<T>(
            dependencyBuilderMap = dependencyBuilderMap,
            additionalVariables = additionalVariables,
            lifecycleOwner = lifecycleOwner
        ).apply { setHasStableIds(hasStableId) }
    )
}

fun <T : TypedModel> RecyclerView.setState(
    viewState: SubmittableRecyclerViewState<T>?,
    dependencyBuilderMap: Map<Int, ViewHolderBuilder> = AntonioSettings.globalViewHolderDependencyBuilderMap,
    additionalVariables: Map<Int, Any>? = null,
    hasStableId: Boolean = false
) {
    if (viewState == null) {
        adapter = null
        return
    }
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    val adapter = AntonioListAdapter(
        dependencyBuilderMap = dependencyBuilderMap,
        diffItemCallback = viewState.itemCallback,
        additionalVariables = additionalVariables,
        lifecycleOwner = lifecycleOwner
    ).apply { setHasStableIds(hasStableId) }
    this.setState(viewState, adapter)
}
//endregion

//region Make adapter for the view pager
fun <T : TypedModel> ViewPagerState<T>.makeAdapter(
    lifecycleOwner: LifecycleOwner,
    dependencyBuilderMap: Map<Int, PagerViewDependencyBuilder> = AntonioSettings.globalViewPagerDependencyBuilderMap,
    additionalVariables: Map<Int, Any>? = null
): PagerAdapter {
    val adapter = AntonioPagerAdapter<T>(
        dependencyBuilderMap = dependencyBuilderMap,
        additionalVariables = additionalVariables
    )
    setAdapterDependency(adapter)
    lifecycleOwner.onDestroy {
        setAdapterDependency(null)
    }
    return adapter
}
//endregion

//region setState for the view pager
fun <T : TypedModel> ViewPager.setState(
    viewState: ViewPagerState<T>?,
    dependencyBuilderMap: Map<Int, PagerViewDependencyBuilder> = AntonioSettings.globalViewPagerDependencyBuilderMap,
    additionalVariables: Map<Int, Any>? = null
) {
    if (viewState == null) {
        adapter = null
        return
    }
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    this.setState(
        viewState, AntonioPagerAdapter(dependencyBuilderMap, additionalVariables, lifecycleOwner)
    )
}

fun <T : TypedModel> ViewPager.setState(
    viewState: ViewPagerState<T>,
    adapter: AntonioCorePagerAdapter<T>
) {
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    lifecycleOwner.onDestroy {
        this.adapter = null
        viewState.setAdapterDependency(null)
    }
    viewState.setAdapterDependency(adapter)
    this.adapter = adapter
}
//endregion

//region Make adapter for the view pager 2
fun <T : TypedModel> RecyclerViewState<T>.makeAdapter(
    fragment: Fragment,
    dependencyBuilderMap: Map<Int, FragmentBuilder> = AntonioSettings.globalFragmentBuilderMap,
    implementedItemID: Boolean = false,
    additionalVariables: Map<Int, Any>? = null
): FragmentStateAdapter {
    val adapter = AntonioFragmentStateAdapter<T>(
        fragment = fragment,
        implementedItemID = implementedItemID,
        dependencyBuilderMap = dependencyBuilderMap,
        additionalVariables = additionalVariables
    )
    setAdapterDependency(adapter)
    fragment.viewLifecycleOwner.onDestroy {
        setAdapterDependency(null)
    }
    return adapter
}

fun <T : TypedModel> RecyclerViewState<T>.makeAdapter(
    activity: FragmentActivity,
    dependencyBuilderMap: Map<Int, FragmentBuilder> = AntonioSettings.globalFragmentBuilderMap,
    implementedItemID: Boolean = false,
    additionalVariables: Map<Int, Any>? = null
): FragmentStateAdapter {
    val adapter =
        AntonioFragmentStateAdapter<T>(
            fragmentActivity = activity,
            implementedItemID = implementedItemID,
            dependencyBuilderMap = dependencyBuilderMap,
            additionalVariables = additionalVariables
        )
    setAdapterDependency(adapter)
    activity.onDestroy {
        setAdapterDependency(null)
    }
    return adapter
}

fun <T : TypedModel> RecyclerViewState<T>.makeAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    dependencyBuilderMap: Map<Int, FragmentBuilder> = AntonioSettings.globalFragmentBuilderMap,
    implementedItemID: Boolean = false,
    additionalVariables: Map<Int, Any>? = null
): FragmentStateAdapter {
    val adapter =
        AntonioFragmentStateAdapter<T>(
            fragmentManager = fragmentManager,
            lifecycle = lifecycle,
            implementedItemID = implementedItemID,
            dependencyBuilderMap = dependencyBuilderMap,
            additionalVariables = additionalVariables
        )
    setAdapterDependency(adapter)
    lifecycle.onDestroy {
        setAdapterDependency(null)
    }
    return adapter
}
//endregion

//region setState for the view pager 2
fun <T : TypedModel> ViewPager2.setStateWithFragmentBuilder(
    viewState: RecyclerViewState<T>?,
    dependencyBuilderMap: Map<Int, FragmentBuilder> = AntonioSettings.globalFragmentBuilderMap,
    implementedItemID: Boolean = false,
    additionalVariables: Map<Int, Any>? = null
) {
    if (viewState == null) {
        adapter = null
        return
    }
    val findFragment = findFragmentOrNull(this)
    val adapter = (if (findFragment != null) {
        AntonioFragmentStateAdapter<T>(
            fragment = findFragment,
            implementedItemID = implementedItemID,
            dependencyBuilderMap = dependencyBuilderMap,
            additionalVariables = additionalVariables
        )
    } else {
        val activity = findActivity<FragmentActivity>(this)
            ?: throw IllegalStateException(
                "Set argument after attaching on the activity or fragment." +
                        " It's essential to prevent memory leak  "
            )
        AntonioFragmentStateAdapter(
            fragmentActivity = activity,
            implementedItemID = implementedItemID,
            dependencyBuilderMap = dependencyBuilderMap,
            additionalVariables = additionalVariables
        )
    })
    this.setStateWithFragmentBuilder(viewState, adapter)
}

fun <T : TypedModel> ViewPager2.setStateWithViewHolderBuilder(
    viewState: RecyclerViewState<T>?,
    dependencyBuilderMap: Map<Int, ViewHolderBuilder> = AntonioSettings.globalViewHolderDependencyBuilderMap,
    hasStableId: Boolean = false,
    additionalVariables: Map<Int, Any>? = null
) {
    if (viewState == null) {
        adapter = null
        return
    }
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    val adapter = AntonioAdapter<T>(
        dependencyBuilderMap = dependencyBuilderMap,
        additionalVariables = additionalVariables,
        lifecycleOwner = lifecycleOwner
    ).apply {
        setHasStableIds(hasStableId)
    }
    setStateWithViewHolderBuilder(viewState, adapter)
}

fun <T : TypedModel> ViewPager2.setStateWithViewHolderBuilder(
    viewState: SubmittableRecyclerViewState<T>?,
    dependencyBuilderMap: Map<Int, ViewHolderBuilder> = AntonioSettings.globalViewHolderDependencyBuilderMap,
    hasStableId: Boolean = false,
    additionalVariables: Map<Int, Any>? = null
) {
    if (viewState == null) {
        adapter = null
        return
    }
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    val adapter = AntonioListAdapter(
        dependencyBuilderMap = dependencyBuilderMap,
        diffItemCallback = viewState.itemCallback,
        additionalVariables = additionalVariables,
        lifecycleOwner = lifecycleOwner
    ).apply {
        setHasStableIds(hasStableId)
    }
    setStateWithViewHolderBuilder(viewState, adapter)
}


fun <T : TypedModel> ViewPager2.setStateWithFragmentBuilder(
    viewState: RecyclerViewState<T>,
    adapter: AntonioCoreFragmentStateAdapter<T>,
) {
    fun clearAll() {
        this.adapter = null
        viewState.setAdapterDependency(null)
    }

    val findFragment = findFragmentOrNull(this)
    if (findFragment != null) {
        findFragment.viewLifecycleOwner.onDestroy(::clearAll)
    } else {
        val activity = findActivity<FragmentActivity>(this)
            ?: throw IllegalStateException(
                "Set argument after attaching on the activity or fragment." +
                        " It's essential to prevent memory leak  "
            )
        activity.onDestroy(::clearAll)
    }
    viewState.setAdapterDependency(adapter)
    this.adapter = adapter
}

fun <T : TypedModel> ViewPager2.setStateWithViewHolderBuilder(
    viewState: RecyclerViewState<T>, adapter: AntonioAdapter<T>
) {
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    lifecycleOwner.onDestroy {
        this.adapter = null
        viewState.setAdapterDependency(null)
    }
    viewState.setAdapterDependency(adapter)
    this.adapter = adapter
}

fun <T : TypedModel> ViewPager2.setStateWithViewHolderBuilder(
    viewState: SubmittableRecyclerViewState<T>, adapter: AntonioListAdapter<T>
) {
    val lifecycleOwner = findLifecycleOwner(this) ?: throw IllegalStateException(
        "Set argument after attaching on the activity or fragment." +
                " It's essential to prevent memory leak  "
    )
    lifecycleOwner.onDestroy {
        this.adapter = null
        viewState.setAdapterDependency(null)
    }
    viewState.setAdapterDependency(adapter)
    this.adapter = adapter
}
//endregion