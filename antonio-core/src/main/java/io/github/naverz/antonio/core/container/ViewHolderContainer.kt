package io.github.naverz.antonio.core.container

import io.github.naverz.antonio.core.ViewHolderBuilder

class ViewHolderContainer : ViewContainer<ViewHolderBuilder> {

    private val viewHolderBuilderMap = hashMapOf<Int, ViewHolderBuilder>()

    override fun add(key: Int, value: ViewHolderBuilder) {
        viewHolderBuilderMap[key] = value
    }

    override fun addAll(from: Map<Int, ViewHolderBuilder>) {
        viewHolderBuilderMap.putAll(from)
    }

    override fun get(key: Int): ViewHolderBuilder? {
        return viewHolderBuilderMap[key]
    }
}