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
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.findActivity
import io.github.naverz.antonio.findFragmentOrNull

abstract class AntonioPagerView<ITEM : AntonioModel> : PagerViewDependency<ITEM> {

    /**
     * Fragment which has this view.
     * It can be null if it's not in fragment or it's not instantiated.
     */
    protected var fragment: Fragment? = null
        private set

    /**
     * Activity which has this view.
     * It can be null, if the activity which has this view doesn't inherit [ComponentActivity] or it's not instantiated.
     */
    protected var activity: ComponentActivity? = null
        private set

    override fun instantiateItem(
        container: ViewGroup, position: Int, viewType: Int, antonioModel: ITEM
    ): Any {
        return getView(container, position, viewType, antonioModel).apply {
            activity = findActivity(container)
            fragment = findFragmentOrNull(container)
            container.addView(this)
            onViewCreated(this, position, antonioModel)
        }
    }

    override fun destroyItem(
        container: ViewGroup, position: Int, antonioModel: ITEM, any: Any
    ) {
        activity = null
        fragment = null
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