package io.github.naverz.antonio.core.container

import io.github.naverz.antonio.core.FragmentBuilder

open class FragmentContainer : ViewContainer<FragmentBuilder> {

    private var viewTypeIndex = 0
    private val fragmentBuilderMap = hashMapOf<Int, FragmentBuilder>()
    private val classWithViewType = hashMapOf<Class<*>, Int>()

    override fun getViewType(modelClass: Class<*>): Int? {
        return classWithViewType[modelClass]
    }

    override fun get(viewType: Int): FragmentBuilder? {
        return fragmentBuilderMap[viewType]
    }

    override fun add(modelClass: Class<*>, value: FragmentBuilder): ViewContainer<FragmentBuilder> {
        synchronized(this) {
            classWithViewType[modelClass] = viewTypeIndex
            fragmentBuilderMap[viewTypeIndex] = value
            viewTypeIndex += 1
            return this
        }
    }

    override fun get(modelClass: Class<*>): FragmentBuilder? {
        return classWithViewType[modelClass]?.let { viewType -> fragmentBuilderMap[viewType] }
    }
}