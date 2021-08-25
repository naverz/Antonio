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

package io.github.naverz.antonio.core.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.naverz.antonio.core.TypedModel

abstract class AntonioPagerView<ITEM : TypedModel> : PagerViewDependency<ITEM> {

    override fun instantiateItem(
        container: ViewGroup, position: Int, typedModel: ITEM
    ): Any {
        return LayoutInflater.from(container.context)
            .inflate(typedModel.viewType(), container, false).apply {
                container.addView(this)
                onViewCreated(this, position, typedModel)
            }
    }

    override fun destroyItem(
        container: ViewGroup, position: Int, typedModel: ITEM, any: Any
    ) {
        container.removeView(any as View)
        onDestroyedView(position, typedModel)
    }

    override fun getPageWidth(position: Int, typedModel: ITEM): Float {
        return 1.0f
    }

    open fun onViewCreated(view: View, position: Int, typedModel: ITEM) {

    }

    open fun onDestroyedView(position: Int, typedModel: ITEM) {

    }

}