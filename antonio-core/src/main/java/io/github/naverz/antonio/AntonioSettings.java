package io.github.naverz.antonio;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import io.github.naverz.antonio.core.container.FragmentContainer;
import io.github.naverz.antonio.core.container.ViewHolderContainer;
import io.github.naverz.antonio.core.container.PagerViewContainer;
import io.github.naverz.antonio.core.etc.MainThreadExecutor;

public class AntonioSettings {
    private AntonioSettings() {

    }

    private static Callable<Executor> executorBuilder = MainThreadExecutor::new;

    @NonNull
    public static Callable<Executor> getExecutorBuilder() {
        return executorBuilder;
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public static void setExecutorBuilder(@NonNull Callable<Executor> executorBuilder) {
        AntonioSettings.executorBuilder = executorBuilder;
    }

    public static final ViewHolderContainer viewHolderContainer = new ViewHolderContainer();
    public static final PagerViewContainer pagerViewContainer = new PagerViewContainer();
    public static final FragmentContainer fragmentContainer = new FragmentContainer();
}
