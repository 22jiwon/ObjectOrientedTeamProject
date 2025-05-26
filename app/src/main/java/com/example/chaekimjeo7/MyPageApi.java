package com.example.chaekimjeo7;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface MyPageApi {

    // ✅ 토큰 없이 호출하는 GET
    @GET("/api/mypage")
    Call<MyPageResponse> getMyPage();

    // ✅ 시간표 등록 - 이건 그대로
    @POST("/api/mypage/schedule")
    Call<Void> postSchedule(@Body String scheduleText);
}
