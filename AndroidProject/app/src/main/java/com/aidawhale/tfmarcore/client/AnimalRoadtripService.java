package com.aidawhale.tfmarcore.client;

import com.aidawhale.tfmarcore.room.Game;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnimalRoadtripService {

    @GET("api/user/{userId}")
    Call<User> getUserById(
            @Path("userId") String userId
    );

    @POST("api/user/new/")
    Call<User> createUser(
            @Body User user
    );

    @POST("api/user/{userId}/survey/new/")
    Call<User> addSurvey(
            @Path("userId") String userId,
            @Body Survey survey
    );

    @POST("api/user/{userId}/game/new/")
    Call<User> addGame(
            @Path("userId") String userId,
            @Body Game game
    );
}
