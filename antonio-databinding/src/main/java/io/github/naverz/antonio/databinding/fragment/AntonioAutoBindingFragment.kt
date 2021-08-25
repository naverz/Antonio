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

package io.github.naverz.antonio.databinding.fragment

import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.github.naverz.antonio.core.TypedModel
import java.lang.IllegalStateException

abstract class AntonioAutoBindingFragment : AntonioBindingFragment<ViewDataBinding, TypedModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            return super.onCreateView(inflater, container, savedInstanceState)
        } catch (e: InflateException) {
            throw InflateException(
                "There is no related layout id with the view type you implemented, View type : ${layoutId()}]"
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        additionalVariables()?.let {
            for (entry in it) {
                binding?.setVariable(entry.key, entry.value)
            }
        }
        bindingVariableId()?.let {
            if (binding?.setVariable(it, requireModel()) == false) {
                //TODO Complete an exception message
                throw IllegalStateException()
            }
        }
        binding?.executePendingBindings()
    }

    open fun bindingVariableId(): Int? = null
    open fun additionalVariables(): Map<Int, Any>? = null
}