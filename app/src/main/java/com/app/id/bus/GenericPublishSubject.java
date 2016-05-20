package com.app.id.bus;

import rx.subjects.PublishSubject;

public abstract class GenericPublishSubject  {

    public static final int CONNECTIVITY_CHANGE_TYPE = 1;

    public static final PublishSubject<PublishItem> PUBLISH_SUBJECT = PublishSubject.create();

    private GenericPublishSubject() { }
}
