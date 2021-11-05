package io.github.naverz.antonio.databinding

import androidx.annotation.RestrictTo
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import io.github.naverz.antonio.AntonioSettings
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.container.PagerViewContainer
import io.github.naverz.antonio.core.container.ViewHolderContainer
import io.github.naverz.antonio.core.state.RecyclerViewState
import io.github.naverz.antonio.core.state.SubmittableRecyclerViewState
import io.github.naverz.antonio.core.state.ViewPagerState

@BindingAdapter(
    value = ["antonio_recycler_view_state", "antonio_view_holder_container", "antonio_additional_variables", "antonio_has_stable_id"],
    requireAll = false
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
fun <T : AntonioModel> RecyclerView.setStateForBinding(
    viewState: RecyclerViewState<T>?,
    viewHolderContainer: ViewHolderContainer?,
    additionalVariables: Map<Int, Any>?,
    hasStableId: Boolean
) {
    setState(
        viewState,
        viewHolderContainer ?: AntonioSettings.viewHolderContainer,
        additionalVariables,
        hasStableId
    )
}

@BindingAdapter(
    value = ["antonio_submittable_recycler_view_state", "antonio_view_holder_container", "antonio_additional_variables", "antonio_has_stable_id"],
    requireAll = false
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
fun <T : AntonioModel> RecyclerView.setStateForBinding(
    viewState: SubmittableRecyclerViewState<T>?,
    viewHolderContainer: ViewHolderContainer?,
    additionalVariables: Map<Int, Any>?,
    hasStableId: Boolean
) {
    setState(
        viewState,
        viewHolderContainer ?: AntonioSettings.viewHolderContainer,
        additionalVariables,
        hasStableId
    )
}

@BindingAdapter(
    value = ["antonio_view_pager_state", "antonio_pager_view_container", "antonio_additional_variables"],
    requireAll = false
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
fun <T : AntonioModel> ViewPager.setStateForBinding(
    viewState: ViewPagerState<T>?,
    pagerViewContainer: PagerViewContainer?,
    additionalVariables: Map<Int, Any>?
) {
    setState(
        viewState,
        pagerViewContainer ?: AntonioSettings.pagerViewContainer,
        additionalVariables
    )
}

@BindingAdapter(
    value = ["antonio_recycler_view_state", "antonio_view_holder_container", "antonio_additional_variables", "antonio_has_stable_id"],
    requireAll = false
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
fun <T : AntonioModel> ViewPager2.setStateForBinding(
    viewState: RecyclerViewState<T>?,
    viewHolderContainer: ViewHolderContainer?,
    additionalVariables: Map<Int, Any>?,
    hasStableId: Boolean
) {
    setState(
        viewState,
        viewHolderContainer ?: AntonioSettings.viewHolderContainer,
        hasStableId,
        additionalVariables
    )
}

@BindingAdapter(
    value = ["antonio_submittable_recycler_view_state", "antonio_view_holder_container", "antonio_additional_variables", "antonio_has_stable_id"],
    requireAll = false
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
fun <T : AntonioModel> ViewPager2.setStateForBinding(
    viewState: SubmittableRecyclerViewState<T>?,
    viewHolderContainer: ViewHolderContainer?,
    additionalVariables: Map<Int, Any>?,
    hasStableId: Boolean
) {
    setState(
        viewState,
        viewHolderContainer ?: AntonioSettings.viewHolderContainer,
        hasStableId,
        additionalVariables
    )
}