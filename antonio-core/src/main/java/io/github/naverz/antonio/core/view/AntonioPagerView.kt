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

import android.view.View
import android.view.ViewGroup
import io.github.naverz.antonio.core.AntonioModel

abstract class AntonioPagerView<ITEM : AntonioModel> : PagerViewDependency<ITEM> {

    override fun instantiateItem(
        container: ViewGroup, position: Int, viewType: Int, antonioModel: ITEM
    ): Any {
        return getView(container, position, viewType, antonioModel).apply {
            container.addView(this)
            onViewCreated(this, position, antonioModel)
        }
    }

    override fun destroyItem(
        container: ViewGroup, position: Int, antonioModel: ITEM, any: Any
    ) {
        container.removeView(any as View)
        onDestroyedView(position, antonioModel)
    }

    override fun getPageWidth(position: Int, antonioModel: ITEM): Float {
        return 1.0f
    }

    open fun onViewCreated(view: View, position: Int, antonioModel: ITEM) {

    }

    open fun onDestroyedView(position: Int, antonioModel: ITEM) {

    }

}