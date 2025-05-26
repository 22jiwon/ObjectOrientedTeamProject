package com.example.myapplication;

import com.example.myapplication.ReviewResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReviewApi {
    @GET("/api/reviews")
    Call<ReviewResponse> getReviews(
            @Query("productId") int productId,
            @Query("sellerId") int sellerId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("sortBy") String sortBy
    );
}
