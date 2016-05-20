package com.app.id.bus;

public class PublishItem<T> {
    public final int type;
    public final T object;

    public PublishItem(int type, T object) {
        this.type = type;
        this.object = object;
    }

    public static <T> PublishItem<T> of(int type, T object) {
        return new PublishItem<>(type, object);
    }
}
