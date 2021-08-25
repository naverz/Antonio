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

import android.view.InflateException;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import com.github.lakelab.antonio.paging3.core.AntonioCorePagedListAdapter;

import java.util.HashMap;
import java.util.Map;

import io.github.naverz.antonio.core.TypedModel;
import io.github.naverz.antonio.core.ViewHolderBuilder;
import io.github.naverz.antonio.core.holder.TypedViewHolder;
import io.github.naverz.antonio.databinding.AutoBindingModel;
import io.github.naverz.antonio.databinding.holder.AntonioAutoBindingViewHolder;

public class AntonioPagedListAdapter<ITEM extends TypedModel, VH extends TypedViewHolder<ITEM>>
        extends AntonioCorePagedListAdapter<ITEM, VH> {

    private final Map<Integer, AutoBindingModel> autoBindingViewTypeMap = new HashMap<>();
    @Nullable
    private final Map<Integer, Object> additionalVariables;
    @Nullable
    private final LifecycleOwner lifecycleOwner;

    public AntonioPagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap) {
        super(diffCallback, viewHolderBuilderMap);
        this.additionalVariables = null;
        this.lifecycleOwner = null;
    }

    public AntonioPagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap) {
        super(config, viewHolderBuilderMap);
        this.additionalVariables = null;
        this.lifecycleOwner = null;
    }

    public AntonioPagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap,
                                   @Nullable Map<Integer, Object> additionalVariables) {
        super(diffCallback, viewHolderBuilderMap);
        this.additionalVariables = additionalVariables;
        this.lifecycleOwner = null;
    }

    public AntonioPagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap,
                                   @Nullable Map<Integer, Object> additionalVariables) {
        super(config, viewHolderBuilderMap);
        this.additionalVariables = additionalVariables;
        this.lifecycleOwner = null;
    }

    public AntonioPagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap,
                                   @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, viewHolderBuilderMap);
        this.additionalVariables = null;
        this.lifecycleOwner = lifecycleOwner;
    }

    public AntonioPagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap,
                                   @Nullable LifecycleOwner lifecycleOwner) {
        super(config, viewHolderBuilderMap);
        this.additionalVariables = null;
        this.lifecycleOwner = lifecycleOwner;
    }

    public AntonioPagedListAdapter(@NonNull DiffUtil.ItemCallback<ITEM> diffCallback,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap,
                                   @Nullable Map<Integer, Object> additionalVariables,
                                   @Nullable LifecycleOwner lifecycleOwner) {
        super(diffCallback, viewHolderBuilderMap);
        this.additionalVariables = additionalVariables;
        this.lifecycleOwner = lifecycleOwner;
    }

    public AntonioPagedListAdapter(@NonNull AsyncDifferConfig<ITEM> config,
                                   @NonNull Map<Integer, ViewHolderBuilder> viewHolderBuilderMap,
                                   @Nullable Map<Integer, Object> additionalVariables,
                                   @Nullable LifecycleOwner lifecycleOwner) {
        super(config, viewHolderBuilderMap);
        this.additionalVariables = additionalVariables;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH createdBinding = createViewBindingIfIsAutoBindingModel(parent, viewType);
        if (createdBinding != null) return createdBinding;
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        ITEM item = getItem(position);
        if (item instanceof AutoBindingModel && !autoBindingViewTypeMap.containsKey(item.viewType())) {
            autoBindingViewTypeMap.put(item.viewType(), (AutoBindingModel) item);
        }
        if (item == null) return super.getItemViewType(position);
        return item.viewType();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private VH createViewBindingIfIsAutoBindingModel(ViewGroup parent, int viewType) {
        AutoBindingModel model = autoBindingViewTypeMap.get(viewType);
        if (model == null) return null;
        try {
            return (VH) new AntonioAutoBindingViewHolder(
                    model.viewType(),
                    parent,
                    model.bindingVariableId(),
                    additionalVariables,
                    lifecycleOwner
            );
        } catch (InflateException e) {
            throw new InflateException(
                    String.format("There is no related layout id with the view type you implemented, View type : [%d]", model.viewType())
            );
        }
    }
}
