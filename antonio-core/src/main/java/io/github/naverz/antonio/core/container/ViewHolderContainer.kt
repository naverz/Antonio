package io.github.naverz.antonio.core.container

import io.github.naverz.antonio.core.ViewHolderBuilder

open class ViewHolderContainer : ViewContainer<ViewHolderBuilder> {

    private var viewTypeIndex = 0
    private val viewHolderBuilderMap = hashMapOf<Int, ViewHolderBuilder>()
    private val classWithViewType = hashMapOf<Class<*>, Int>()

    override fun getViewType(modelClass: Class<*>): Int? {
        return classWithViewType[modelClass]
    }

    override fun add(modelClass: Class<*>, value: ViewHolderBuilder): ViewHolderContainer {
        synchronized(this) {
            classWithViewType[modelClass] = viewTypeIndex
            viewHolderBuilderMap[viewTypeIndex] = value
            viewTypeIndex += 1
            return this
        }
    }

    override fun get(viewType: Int): ViewHolderBuilder? {
        return viewHolderBuilderMap[viewType]
    }

    override fun get(modelClass: Class<*>): ViewHolderBuilder? {
        return classWithViewType[modelClass]?.let { viewType -> viewHolderBuilderMap[viewType] }
    }
}