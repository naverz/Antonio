package io.github.naverz.antonio.core.container

import io.github.naverz.antonio.core.FragmentBuilder

class FragmentContainer : ViewContainer<FragmentBuilder> {

    private val fragmentBuilderMap = hashMapOf<Int, FragmentBuilder>()

    override fun add(key: Int, value: FragmentBuilder) {
        fragmentBuilderMap[key] = value
    }

    override fun addAll(from: Map<Int, FragmentBuilder>) {
        fragmentBuilderMap.putAll(from)
    }

    override fun get(key: Int): FragmentBuilder? {
        return fragmentBuilderMap[key]
    }
}