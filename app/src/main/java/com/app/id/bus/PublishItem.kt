package com.app.id.bus

data class PublishItem<T>(val type: Int, val `object`: T) {
    companion object {
        fun <T> of(type: Int, obj: T): PublishItem<T> = PublishItem(type, obj)
    }
}
