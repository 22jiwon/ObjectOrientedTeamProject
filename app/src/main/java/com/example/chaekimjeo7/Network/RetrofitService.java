package com.example.chaekimjeo7.Network;

import com.example.chaekimjeo7.Model.BuyResponse;
import com.example.chaekimjeo7.Model.MyPageResponse;
import com.example.chaekimjeo7.Model.Recommendation;
import com.example.chaekimjeo7.Model.ReviewResponse;
import com.example.chaekimjeo7.Model.LoginResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("/api/warning")//로그인
    Call<LoginResponse> login(@Body Map<String, String> loginData);

    @GET("/api/mypage") //마이페이지
    Call<MyPageResponse> getMyPage();

    @GET("/mypage/buys") //마이페이지-구매 내역
    Call<BuyResponse> getBuyHistory();

    @POST("/api/mypage/schedule") // 마이페이지-시간표 등록
    Call<Void> postSchedule(@Body String scheduleText);

    @GET("/api/recommendation/by-schedule") //교재 추천 페이지
    Call<List<Recommendation>> getBySchedule(@Query("userId") int userId);

    @GET("/api/reviews") //리뷰 페이지
    Call<ReviewResponse> getReviews(
            @Query("productId") int productId,
            @Query("sellerId") int sellerId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("sortBy") String sortBy);

}