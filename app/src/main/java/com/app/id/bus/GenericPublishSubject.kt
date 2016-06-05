package com.app.id.bus

import rx.subjects.PublishSubject

object GenericPublishSubject {
    val CONNECTIVITY_CHANGE_TYPE = 1
    val PUBLISH_SUBJECT: PublishSubject<PublishItem<Any>> = PublishSubject.create<PublishItem<Any>>()
}
