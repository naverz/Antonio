package io.github.naverz.antonio.core.container

interface ViewContainer<T> {
    fun getViewType(modelClass: Class<*>): Int?
    fun add(modelClass: Class<*>, value: T): ViewContainer<T>
    fun get(viewType: Int): T?
    fun get(modelClass: Class<*>): T?
}