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
import androidx.recyclerview.widget.DiffUtil;

import io.github.naverz.antonio.core.paging3.AntonioCorePagingDataAdapter;

import io.github.naverz.antonio.AntonioSettings;
import io.github.naverz.antonio.core.AntonioModel;
import io.github.naverz.antonio.core.container.ViewHolderContainer;
import io.github.naverz.antonio.core.holder.TypedViewHolder;
import kotlinx.coroutines.CoroutineDispatcher;

public class AntonioPagingDataAdapter<ITEM extends AntonioModel>
        extends AntonioCorePagingDataAdapter<ITEM, TypedViewHolder<ITEM>> {
    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback) {
        super(diffCallback, AntonioSettings.viewHolderContainer);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher) {
        super(diffCallback, mainDispatcher, workerDispatcher, AntonioSettings.viewHolderContainer);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher) {
        super(diffCallback, mainDispatcher, AntonioSettings.viewHolderContainer);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback, viewHolderContainer);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback, mainDispatcher, workerDispatcher, viewHolderContainer);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback, mainDispatcher, viewHolderContainer);
    }
}
