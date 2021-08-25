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

package com.github.lakelab.antonio.paging3.core;

import android.content.res.Resources;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.github.naverz.antonio.core.TypedModel;
import io.github.naverz.antonio.core.ViewHolderBuilder;
import io.github.naverz.antonio.core.holder.AntonioViewHolder;
import io.github.naverz.antonio.core.holder.TypedViewHolder;

public class AntonioCorePagedListAdapter<ITEM extends TypedModel, VH extends TypedViewHolder<ITEM>>
        extends PagedListAdapter<ITEM, VH> {
    protected static <ITEM extends TypedModel> void throwClassCastExceptionForBinding(TypedViewHolder<ITEM> viewHolder, ITEM item) {
        StringBuilder builder = new StringBuilder();
        builder.append("The model type doesn't match with [")
                .append(viewHolder.getClass().getSimpleName());
        Class<ITEM> viewHolderGeneralClass = viewHolder.getDeclaredLayoutIdModelClass();
        if (viewHolder instanceof AntonioViewHolder) {
            builder.append("(")
                    .append(viewHolder.itemView.getContext().getResources().getResourceName(((AntonioViewHolder<ITEM>) viewHolder).getLayoutId()))
                    .append(")");
        }
        builder.append("]. Check whether it is correct for the generic type of [")
                .append(viewHolder.getClass().getSimpleName()).append("<")
                .append(viewHolderGeneralClass != null ? viewHolderGeneralClass.getSimpleName() : "null")
                .append(">]. ").append("You might need to change to \"")
                .append(item.getClass().getSimpleName())
                .append("\", not")
                .append(viewHolderGeneralClass != null ? viewHolderGeneralClass.getSimpleName() : "null");
        throw new ClassCastException(builder.toString());
    }

    protected final Map<Integer, ViewHolderBuilder> viewHolderBuilderMap;

    public AntonioCorePagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback, @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap) {
        super(diffCallback);
        this.viewHolderBuilderMap = viewHolderBuilderMap;
    }

    public AntonioCorePagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config, @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap) {
        super(config);
        this.viewHolderBuilderMap = viewHolderBuilderMap;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderBuilder builder = viewHolderBuilderMap.get(viewType);
        if (builder == null) {
            String viewTypeStr;
            try {
                viewTypeStr = parent.getContext().getResources().getResourceName(viewType);
            } catch (Resources.NotFoundException e) {
                viewTypeStr = viewType + "";
            }

            throw new IllegalStateException("There is no related view holder dependency with the view type[" + viewTypeStr + "] in your dependencyBuilderMap [" + Arrays.toString(viewHolderBuilderMap.entrySet().toArray()) + "]");
        }
        return (VH) builder.build(viewType, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ITEM item = getItem(position);
        if (item == null) return;
        try {
            holder.onBindViewHolder(item, position, null);
        } catch (ClassCastException e) {
            throwClassCastExceptionForBinding(holder, item);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        ITEM item = getItem(position);
        if (item == null) return;
        try {
            holder.onBindViewHolder(item, position, payloads);
        } catch (ClassCastException e) {
            throwClassCastExceptionForBinding(holder, item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ITEM item = getItem(position);
        if (item == null) return super.getItemViewType(position);
        return item.viewType();
    }

    @Override
    public long getItemId(int position) {
        ITEM item = getItem(position);
        if (item == null || item.getItemId() == null) return super.getItemViewType(position);
        return item.getItemId();
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
