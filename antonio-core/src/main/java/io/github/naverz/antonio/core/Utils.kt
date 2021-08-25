package io.github.naverz.antonio.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.github.naverz.antonio.core.fragment.AntonioFragment
import io.github.naverz.antonio.core.holder.TypedViewHolder
import io.github.naverz.antonio.core.view.PagerViewDependency
import java.lang.ClassCastException

internal fun findLifecycleOwner(view: View): LifecycleOwner? {
    return findFragmentOrNull(view)?.viewLifecycleOwner ?: findActivity(view)
}

internal fun findFragmentOrNull(view: View): Fragment? {
    return try {
        view.findFragment()
    } catch (e: IllegalStateException) {
        // View isn't in a fragment
        null
    }
}

internal fun <ITEM : TypedModel> TypedViewHolder<ITEM>.throwClassCastExceptionForBinding(item: ITEM) {
    throw ClassCastException(
        "The model(${item}) type doesn't match with [${this::class.simpleName}]. "
            .plus("Check whether it is correct for the generic type of [${this::class.simpleName}<${this.getDeclaredLayoutIdModelClass()?.simpleName}>]. ")
            .plus("You might need to change to \"${item::class.simpleName}\", not \"${this.getDeclaredLayoutIdModelClass()?.simpleName}\"?")
    )
}

internal fun <ITEM : TypedModel> PagerViewDependency<ITEM>.throwClassCastExceptionForBinding(item: ITEM) {
    throw ClassCastException(
        "The model(${item}) type doesn't match with [${this::class.simpleName}]. "
            .plus("Check whether it is correct for the generic type of [${this::class.simpleName}<${this.getDeclaredLayoutIdModelClass()?.simpleName}>]. ")
            .plus("You might need to change to \"${item::class.simpleName}\", not \"${this.getDeclaredLayoutIdModelClass()?.simpleName}\"?")
    )
}

internal fun <ITEM : TypedModel> AntonioFragment<ITEM>.throwClassCastExceptionForBinding(item: ITEM) {
    throw ClassCastException(
        "The model(${item}) type doesn't match with [${this::class.simpleName}]. "
            .plus("Check whether it is correct for the generic type of [${this::class.simpleName}<${this.getDeclaredLayoutIdModelClass()?.simpleName}>]. ")
            .plus("You might need to change to \"${item::class.simpleName}\", not \"${this.getDeclaredLayoutIdModelClass()?.simpleName}\"?")
    )
}


/**
 * The source code from the MediaRouter in the [official support library](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/marshmallow-release/v7/mediarouter/src/android/support/v7/app/MediaRouteButton.java#262)
 *
 * @param view The view that will be the basis for finding an Activity
 * @return The parent activity for your view.
 */
internal inline fun <reified T : ComponentActivity> findActivity(view: View): T? {
    var context: Context? = view.context
    while (context is ContextWrapper) {
        if (context is T) {
            return context
        }
        if (context is Activity) {
            return null
        }
        context = context.baseContext
    }
    return null
}

internal fun LifecycleOwner.onDestroy(runnable: () -> Unit) {
    this.lifecycle.onDestroy(runnable)
}

internal fun Lifecycle.onDestroy(runnable: () -> Unit) {
    this.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun destroy() {
            runnable.invoke()
            this@onDestroy.removeObserver(this)
        }
    })
}