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

package io.github.naverz.antonio.core.paging2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import io.github.naverz.antonio.core.AntonioModel;
import io.github.naverz.antonio.core.Exceptions;
import io.github.naverz.antonio.core.ViewHolderBuilder;
import io.github.naverz.antonio.core.container.ContainerType;
import io.github.naverz.antonio.core.container.ViewHolderContainer;
import io.github.naverz.antonio.core.holder.TypedViewHolder;

public class AntonioCorePagedListAdapter<ITEM extends AntonioModel, VH extends TypedViewHolder<ITEM>>
        extends PagedListAdapter<ITEM, VH> {
    protected final ViewHolderContainer viewHolderContainer;

    public AntonioCorePagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                       @NonNull ViewHolderContainer viewHolderContainer) {
        super(diffCallback);
        this.viewHolderContainer = viewHolderContainer;
    }

    public AntonioCorePagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config,
                                       @NonNull ViewHolderContainer viewHolderContainer) {
        super(config);
        this.viewHolderContainer = viewHolderContainer;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderBuilder builder = viewHolderContainer.get(viewType);
        if (builder == null) {
            throw new IllegalStateException(Exceptions.ERROR_TEXT_UNKNOWN);
        }
        return (VH) builder.build(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ITEM item = getItem(position);
        if (item == null) return;
        try {
            holder.onBindViewHolder(item, position, null);
        } catch (ClassCastException e) {
            throw new RuntimeException(Exceptions.errorClassCast(item, holder), e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        ITEM item = getItem(position);
        if (item == null) return;
        try {
            holder.onBindViewHolder(item, position, payloads);
        } catch (ClassCastException e) {
            throw new RuntimeException(Exceptions.errorClassCast(item, holder), e);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ITEM item = getItem(position);
        if (item == null) return super.getItemViewType(position);
        Integer viewType = viewHolderContainer.getViewType(item.getClass());
        if (viewType == null)
            throw new IllegalStateException(Exceptions.errorRegisterFirst(item.getClass(), ContainerType.VIEW_HOLDER));
        return viewType;
    }

    @Override
    public long getItemId(int position) {
        ITEM item = getItem(position);
        if (item == null || item.getModelId() == null) return super.getItemViewType(position);
        return item.getModelId();
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        holder.onViewRecycled();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        holder.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        holder.onViewDetachedFromWindow(holder);
    }
}
