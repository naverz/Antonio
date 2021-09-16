package io.github.naverz.antonio.core.container

import io.github.naverz.antonio.core.PagerViewDependencyBuilder

class ViewPagerContainer : ViewContainer<PagerViewDependencyBuilder> {

    private val viewPagerBuilderMap = hashMapOf<Int, PagerViewDependencyBuilder>()

    override fun add(key: Int, value: PagerViewDependencyBuilder) {
        viewPagerBuilderMap[key] = value
    }

    override fun addAll(from: Map<Int, PagerViewDependencyBuilder>) {
        viewPagerBuilderMap.putAll(from)
    }

    override fun get(key: Int): PagerViewDependencyBuilder? {
        return viewPagerBuilderMap[key]
    }
}