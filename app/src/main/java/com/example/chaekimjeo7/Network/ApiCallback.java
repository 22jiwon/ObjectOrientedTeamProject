package com.example.chaekimjeo7.Network;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
