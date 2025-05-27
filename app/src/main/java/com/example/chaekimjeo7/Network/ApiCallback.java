package com.example.chaekimjeo7.Network;

public interface ApiCallback<T> {
    void onSuccess(T response);
    void onFailure(String errorMessage);
}
