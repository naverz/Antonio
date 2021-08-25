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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.fragment.AntonioFragment

abstract class AntonioBindingFragment<T : ViewDataBinding, ITEM : TypedModel> :
    AntonioFragment<ITEM>() {
    protected var binding: T? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            binding = DataBindingUtil.bind<T>(this)?.also { binding ->
                binding.lifecycleOwner = this@AntonioBindingFragment.viewLifecycleOwner
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}