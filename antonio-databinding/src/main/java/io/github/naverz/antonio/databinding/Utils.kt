package io.github.naverz.antonio.databinding

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
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.holder.AntonioViewHolder
import io.github.naverz.antonio.core.holder.TypedViewHolder
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
    if (this is AntonioViewHolder) {
        throw ClassCastException(
            "The model type doesn't match with [${this::class.simpleName} (${
                this.itemView.context.resources.getResourceName(this.layoutId)
            })]. ".plus("Check whether it is correct for the generic type of [${this::class.simpleName}<${this.getDeclaredLayoutIdModelClass()?.simpleName}>]. ")
                .plus("You might need to change to \"${item::class.simpleName}\", not \"${this.getDeclaredLayoutIdModelClass()?.simpleName}\"?")
        )
    } else {
        throw ClassCastException(
            "The model type doesn't match with [${this::class.simpleName}]. "
                .plus("Check whether it is correct for the generic type of [${this::class.simpleName}<${this.getDeclaredLayoutIdModelClass()?.simpleName}>]. ")
                .plus("You might need to change to \"${item::class.simpleName}\", not \"${this.getDeclaredLayoutIdModelClass()?.simpleName}\"?")
        )
    }
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

suspend fun <ITEM : Any> List<ITEM>.getDiff(
    newList: MutableList<ITEM>,
    itemCallback: DiffUtil.ItemCallback<ITEM>,
): DiffUtil.DiffResult {
    return withContext(Dispatchers.IO) {
        return@withContext DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = this@getDiff.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return itemCallback.areItemsTheSame(
                    this@getDiff[oldItemPosition], newList[newItemPosition]
                )
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return itemCallback.areContentsTheSame(
                    this@getDiff[oldItemPosition], newList[newItemPosition]
                )
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return itemCallback.getChangePayload(
                    this@getDiff[oldItemPosition], newList[newItemPosition]
                )
            }
        })
    }
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