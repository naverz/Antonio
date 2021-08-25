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

package io.github.naverz.antonio.paging3;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import com.github.lakelab.antonio.paging3.core.AntonioCorePagedListAdapter;

import java.util.Map;

import io.github.naverz.antonio.core.TypedModel;
import io.github.naverz.antonio.core.ViewHolderBuilder;
import io.github.naverz.antonio.core.holder.TypedViewHolder;

public class AntonioPagedListAdapter<ITEM extends TypedModel, VH extends TypedViewHolder<ITEM>>
        extends AntonioCorePagedListAdapter<ITEM, VH> {
    public AntonioPagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap) {
        super(diffCallback, viewHolderBuilderMap);
    }

    public AntonioPagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap) {
        super(config, viewHolderBuilderMap);
    }
}
