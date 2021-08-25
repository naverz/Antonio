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

package io.github.naverz.antonio.core.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.naverz.antonio.core.TypedModel
import java.io.Serializable
import java.lang.reflect.ParameterizedType

abstract class AntonioFragment<ITEM : TypedModel> : Fragment() {
    companion object {
        private const val POSITION_BUNDLE_KEY = "POSITION_BUNDLE_KEY"
        private const val MODEL_BUNDLE_KEY = "MODEL_BUNDLE_KEY"
    }

    protected var position: Int? = null
        private set

    protected var model: ITEM? = null
        private set

    protected fun requirePosition() =
        position
            ?: throw NullPointerException("Position is null. This fragment hasn't set data yet.")


    protected fun requireModel() = model
        ?: throw NullPointerException(
            "Model is null. " +
                    "You might need to implement Parcelable or Serializable for your LayoutIdModel " +
                    "to saving your LayoutIdModel's state when it's needed. " +
                    "For example, when the screen is changed."
        )

    abstract fun layoutId(): Int

    @Suppress("UNCHECKED_CAST")
    fun getDeclaredLayoutIdModelClass(): Class<ITEM>? = try {
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<ITEM>)
    } catch (e: Exception) {
        null
    }

    fun setData(position: Int, model: ITEM) {
        this.position = position
        this.model = model
        arguments = Bundle().apply {
            putInt(POSITION_BUNDLE_KEY, position)
            when (model) {
                is Parcelable -> {
                    putParcelable(MODEL_BUNDLE_KEY, model)
                }
                is Serializable -> {
                    putSerializable(MODEL_BUNDLE_KEY, model)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState != null) {
            this.position = requireArguments().getInt(POSITION_BUNDLE_KEY)
            this.model = requireArguments().getParcelable(MODEL_BUNDLE_KEY)
                ?: (requireArguments().getSerializable(MODEL_BUNDLE_KEY) as? ITEM)
        }
        return inflater.inflate(layoutId(), container, false)
    }
}