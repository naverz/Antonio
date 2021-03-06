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

package io.github.naverz.antonio.databinding.view

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.view.AntonioPagerView

abstract class AntonioBindingPagerView<T : ViewDataBinding, ITEM : AntonioModel> :
    AntonioPagerView<ITEM>() {
    protected var binding: T? = null

    override fun onViewCreated(view: View, position: Int, antonioModel: ITEM) {
        super.onViewCreated(view, position, antonioModel)
        binding = DataBindingUtil.bind(view)
    }

    override fun onDestroyedView(position: Int, antonioModel: ITEM) {
        super.onDestroyedView(position, antonioModel)
        binding = null
    }

}