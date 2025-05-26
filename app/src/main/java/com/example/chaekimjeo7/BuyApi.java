package com.example.chaekimjeo7;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BuyApi {
    @GET("/mypage/buys")
    Call<BuyResponse> getBuyHistory(); // 토큰 없이
}
