package com.app.id.view

interface AbstractView<T> {
    fun bind(t: T)
    fun get(): T
}