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

package io.github.naverz.antonio.core.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.findActivity
import io.github.naverz.antonio.findFragmentOrNull


open class AntonioViewHolder<ITEM : AntonioModel>(val layoutId: Int, val parent: ViewGroup) :
    TypedViewHolder<ITEM>(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {

    /**
     * Fragment which has this view.
     * It can be null if it's not in fragment.
     */
    protected val fragment: Fragment? = findFragmentOrNull(parent)

    /**
     * Activity which has this view.
     * It can be null, if the activity which has this view doesn't inherit [ComponentActivity].
     */
    protected val activity: ComponentActivity? = findActivity(parent)
}
