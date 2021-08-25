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
import io.github.naverz.antonio.core.TypedModel


open class AntonioViewHolder<ITEM : TypedModel>(
    open val layoutId: Int,
    protected open val parent: ViewGroup
) : TypedViewHolder<ITEM>(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
