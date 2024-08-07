package io.github.naverz.antonio

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

@RestrictTo(RestrictTo.Scope.LIBRARY)
fun findLifecycleOwner(view: View): LifecycleOwner? {
    return findFragmentOrNull(view)?.viewLifecycleOwner ?: findActivity(view)
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
fun findFragmentOrNull(view: View): Fragment? {
    return try {
        view.findFragment()
    } catch (e: IllegalStateException) {
        // View isn't in a fragment
        null
    }
}

/**
 * The source code from the MediaRouter in the [official support library](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/marshmallow-release/v7/mediarouter/src/android/support/v7/app/MediaRouteButton.java#262)
 *
 * @param view The view that will be the basis for finding an Activity
 * @return The parent activity for your view.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
inline fun <reified T : ComponentActivity> findActivity(view: View): T? {
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

@RestrictTo(RestrictTo.Scope.LIBRARY)
fun LifecycleOwner.onDestroy(runnable: () -> Unit) {
    this.lifecycle.onDestroy(runnable)
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
fun Lifecycle.onDestroy(runnable: () -> Unit) {
    val lifecycle = this
    this.addObserver(object : DefaultLifecycleObserver{
        override fun onDestroy(owner: LifecycleOwner) {
            runnable.invoke()
            lifecycle.removeObserver(this)
        }
    })
}