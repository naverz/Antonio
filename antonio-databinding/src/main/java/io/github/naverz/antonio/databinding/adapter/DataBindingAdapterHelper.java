package io.github.naverz.antonio.databinding.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

import io.github.naverz.antonio.core.AntonioModel;
import io.github.naverz.antonio.core.holder.TypedViewHolder;
import io.github.naverz.antonio.databinding.AntonioBindingModel;
import io.github.naverz.antonio.databinding.holder.AntonioAutoBindingViewHolder;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class DataBindingAdapterHelper<
        ITEM extends AntonioModel, VH extends TypedViewHolder<ITEM>> {
    @NonNull
    private final Map<Integer, AntonioBindingModel> layoutIdWithBindingModel = new HashMap<>();
    @Nullable
    final Map<Integer, Object> additionalVariables;
    @Nullable
    final LifecycleOwner lifecycleOwner;

    public DataBindingAdapterHelper(
            @Nullable Map<Integer, Object> additionalVariables,
            @Nullable LifecycleOwner lifecycleOwner) {
        this.additionalVariables = additionalVariables;
        this.lifecycleOwner = lifecycleOwner;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @Nullable
    @SuppressWarnings("unchecked")
    public VH createViewBindingIfIsAutoBindingModel(
            @NonNull ViewGroup parent, int layoutId) {
        AntonioBindingModel model = layoutIdWithBindingModel.get(layoutId);
        if (model == null) return null;
        return (VH) new AntonioAutoBindingViewHolder(
                layoutId,
                parent,
                model.bindingVariableId(),
                additionalVariables,
                lifecycleOwner
        );
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @Nullable
    public Integer findLayoutId(@Nullable ITEM item) {
        if (!(item instanceof AntonioBindingModel)) {
            return null;
        }
        AntonioBindingModel model = (AntonioBindingModel) item;
        if (!layoutIdWithBindingModel.containsKey(model.layoutId())) {
            layoutIdWithBindingModel.put(model.layoutId(), (AntonioBindingModel) item);
        }
        return model.layoutId();
    }
}
