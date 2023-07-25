package com.xinxu.user.service;

public interface InternalUpdateAware<K> {
    void refresh(K key);

    void refreshAll();

    void send(K key);
}
