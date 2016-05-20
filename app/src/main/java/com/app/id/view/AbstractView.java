package com.app.id.view;

public interface AbstractView<T> {
    void bind(T t);
    T get();
}
