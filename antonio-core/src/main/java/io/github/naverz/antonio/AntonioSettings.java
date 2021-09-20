package io.github.naverz.antonio;

import io.github.naverz.antonio.core.container.FragmentContainer;
import io.github.naverz.antonio.core.container.ViewHolderContainer;
import io.github.naverz.antonio.core.container.PagerViewContainer;

public class AntonioSettings {
    private AntonioSettings() {

    }

    public static ViewHolderContainer viewHolderContainer = new ViewHolderContainer();
    public static PagerViewContainer pagerViewContainer = new PagerViewContainer();
    public static FragmentContainer fragmentContainer = new FragmentContainer();
}
