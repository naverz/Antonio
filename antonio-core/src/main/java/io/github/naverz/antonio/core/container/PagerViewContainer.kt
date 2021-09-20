package io.github.naverz.antonio.core.container

import io.github.naverz.antonio.core.PagerViewDependencyBuilder

open class PagerViewContainer : ViewContainer<PagerViewDependencyBuilder> {
    private var viewTypeIndex = 0
    private val viewPagerBuilderMap = hashMapOf<Int, PagerViewDependencyBuilder>()
    private val classWithViewType = hashMapOf<Class<*>, Int>()

    override fun getViewType(modelClass: Class<*>): Int? {
        return classWithViewType[modelClass]
    }

    override fun get(viewType: Int): PagerViewDependencyBuilder? {
        return viewPagerBuilderMap[viewType]
    }

    override fun add(
        modelClass: Class<*>,
        value: PagerViewDependencyBuilder
    ): ViewContainer<PagerViewDependencyBuilder> {
        synchronized(this) {
            classWithViewType[modelClass] = viewTypeIndex
            viewPagerBuilderMap[viewTypeIndex] = value
            viewTypeIndex += 1
            return this
        }
    }

    override fun get(modelClass: Class<*>): PagerViewDependencyBuilder? {
        return classWithViewType[modelClass]?.let { viewType -> viewPagerBuilderMap[viewType] }
    }
}