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

package io.github.naverz.antonio.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import io.github.naverz.antonio.core.FragmentBuilder
import io.github.naverz.antonio.core.TypedModel
import io.github.naverz.antonio.core.adapter.AntonioCoreFragmentStateAdapter

open class AntonioFragmentStateAdapter<ITEM : TypedModel> :
    AntonioCoreFragmentStateAdapter<ITEM> {
    constructor(
        fragmentActivity: FragmentActivity,
        implementedItemID: Boolean,
        dependencyBuilderMap: Map<Int, FragmentBuilder>
    ) : super(fragmentActivity, implementedItemID, dependencyBuilderMap)

    constructor(
        fragment: Fragment,
        implementedItemID: Boolean,
        dependencyBuilderMap: Map<Int, FragmentBuilder>
    ) : super(fragment, implementedItemID, dependencyBuilderMap)

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        implementedItemID: Boolean,
        dependencyBuilderMap: Map<Int, FragmentBuilder>
    ) : super(fragmentManager, lifecycle, implementedItemID, dependencyBuilderMap)
}