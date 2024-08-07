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

package io.github.naverz.antonio.databinding.paging3;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Map;

import io.github.naverz.antonio.core.paging3.AntonioCorePagingDataAdapter;
import io.github.naverz.antonio.AntonioSettings;
import io.github.naverz.antonio.core.AntonioModel;
import io.github.naverz.antonio.core.container.ViewHolderContainer;
import io.github.naverz.antonio.core.holder.TypedViewHolder;
import io.github.naverz.antonio.databinding.adapter.DataBindingAdapterHelper;
import kotlinx.coroutines.CoroutineDispatcher;

public class AntonioPagingDataAdapter<ITEM extends AntonioModel>
        extends AntonioCorePagingDataAdapter<ITEM, TypedViewHolder<ITEM>> {

    private final DataBindingAdapterHelper<ITEM, TypedViewHolder<ITEM>> helper;

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback) {
        super(diffCallback, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, null);

    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher) {
        super(diffCallback, mainDispatcher, workerDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, null);

    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher) {
        super(diffCallback, mainDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, null);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, null);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, mainDispatcher, workerDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, null);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, workerDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, mainDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, null);

    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @Nullable Map<Integer, Object> additionalVariables,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @Nullable Map<Integer, Object> additionalVariables,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, workerDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @Nullable Map<Integer, Object> additionalVariables,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, AntonioSettings.viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, null);

    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback, mainDispatcher, workerDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, null);

    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback, mainDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, null);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, null);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, mainDispatcher, workerDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, null);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, workerDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(null, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, mainDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, null);

    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable Map<Integer, Object> additionalVariables,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull CoroutineDispatcher workerDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable Map<Integer, Object> additionalVariables,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, workerDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, lifecycleOwner);
    }

    public AntonioPagingDataAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                    @NonNull CoroutineDispatcher mainDispatcher,
                                    @NonNull ViewHolderContainer viewHolderContainer,
                                    @Nullable Map<Integer, Object> additionalVariables,
                                    @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, mainDispatcher, viewHolderContainer);
        helper = new DataBindingAdapterHelper<>(additionalVariables, lifecycleOwner);
    }

    @NonNull
    @Override
    public TypedViewHolder<ITEM> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TypedViewHolder<ITEM> createdBinding = helper.createViewBindingIfIsAutoBindingModel(parent, viewType);
        if (createdBinding != null) return createdBinding;
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        ITEM item = getItem(position);
        Integer layoutId = helper.findLayoutId(item);
        if (layoutId == null) {
            return super.getItemViewType(position);
        }
        return layoutId;
    }
}
