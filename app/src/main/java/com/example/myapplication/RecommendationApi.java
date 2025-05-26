package com.example.myapplication;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface RecommendationApi {
    @GET("/api/recommendation/by-schedule")
    Call<List<Recommendation>> getBySchedule(@Query("userId") int userId);

    @GET("/api/recommendation/by-professor")
    Call<List<Recommendation>> getByProfessor(@Query("userId") int userId);

    @GET("/api/recommendation/by-subject")
    Call<List<Recommendation>> getBySubject(@Query("userId") int userId);
}
