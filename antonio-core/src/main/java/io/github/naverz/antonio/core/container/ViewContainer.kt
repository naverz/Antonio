package io.github.naverz.antonio.core.container

interface ViewContainer<T> {

    fun add(key: Int, value: T)
    fun addAll(from: Map<Int, T>)
    fun get(key:Int): T?
}