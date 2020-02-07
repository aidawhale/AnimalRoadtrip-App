package com.aidawhale.tfmarcore.client;

import android.util.Log;

import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestService {

    public static void getUserById(String userId, final OnUserPetitionResponse petitionResponse) {
        // Create a service to access API later
        AnimalRoadtripService serviceSerializer = AnimalRoadtripClient.createServiceSerializer(AnimalRoadtripService.class);

        // Fetch user by userId
        Call<User> userCall = serviceSerializer.getUserById(userId);

        // Execute the call asynchronously. Get a positive or negative callback.
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                try {
                    User user = response.body();
                    if(user != null) { // user found
                        Log.d("RestService.getUserById", " FOUND remoteUser " + user.userID);
                        petitionResponse.onPetitionResponse(user);
                    } else { // user not found on DB
                        Log.d("RestService.getUserById", "DIDN'T FIND remoteUser");
                        petitionResponse.onPetitionResponseNull();
                    }
                } catch (Exception e) {
                    Log.d("RestService.getUserById", "Error while getting user.");
                    e.printStackTrace();
                    petitionResponse.onPetitionError();
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Log.d("RestService.getUserById: onFailure", t.toString());
                petitionResponse.onPetitionFailure();
            }
        });

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            // Waiting too long for an answer...
            Log.d("RestService.getUserById", "Waiting too long...");
            petitionResponse.onPetitionWaiting();
        }
    }

    public static void getTodaysSurvey(String userid, final OnSurveyPetitionResponse petitionResponse) {
        // Create a service to access API later
        AnimalRoadtripService serviceSerializer = AnimalRoadtripClient.createServiceSerializer(AnimalRoadtripService.class);

        // Fetch survey by userId
        Call<Survey> surveyCall = serviceSerializer.getTodaysSurvey(userid);

        surveyCall.enqueue(new Callback<Survey>() {
            @Override
            public void onResponse(@NotNull Call<Survey> call, @NotNull Response<Survey> response) {
                try {
                    Survey survey = response.body();
                    if (survey != null) { // survey found
                        Log.d("RestService.getTodaysSurvey", " FOUND remoteSurvey");
                        petitionResponse.onPetitionResponse(survey);
                    } else { // survey not found
                        Log.d("RestService.getTodaysSurvey", "DIDN'T FIND remoteSurvey");
                        petitionResponse.onPetitionResponseNull();
                    }

                } catch (Exception e) {
                    Log.d("RestService.getTodaysSurvey", "Error while getting today's survey.");
                    e.printStackTrace();
                    petitionResponse.onPetitionError();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Survey> call, @NotNull Throwable t) {
                Log.d("RestService.getTodaysSurvey: onFailure", t.toString());
                petitionResponse.onPetitionFailure();
            }
        });

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            // Waiting too long for an answer...
            Log.d("RestService.getTodaysSurvey", "Waiting too long...");
            petitionResponse.onPetitionWaiting();
        }

    }

    public static void sendNewUser(User user) {
        AnimalRoadtripService serviceSerializer = AnimalRoadtripClient.createServiceSerializer(AnimalRoadtripService.class);

        Log.d("RestService.sendNewUser: SERIALIZE USER", " user " + user.userID + ", storage " + user.storagePermission);
        // Fetch user by userId
        Call<User> userCall = serviceSerializer.createUser(user);

        // Execute the call asynchronously. Get a positive or negative callback.
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                Log.d("RestService.sendNewUser: onResponse", "");
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Log.d("RestService.sendNewUser: onFailure", t.toString());
            }
        });
    }

    public static void sendSurvey(User user, Survey survey) {
        AnimalRoadtripService serviceSerializer = AnimalRoadtripClient.createServiceSerializer(AnimalRoadtripService.class);

        Log.d("RestService.sendSurvey: SERIALIZE SURVEY", " user " + survey.user + ", survey " + survey.date);

        // Fetch user by userId
        Call<Survey> surveyCall = serviceSerializer.createSurvey(survey);

        // Execute the call asynchronously. Get a positive or negative callback.
        surveyCall.enqueue(new Callback<Survey>() {
            @Override
            public void onResponse(@NotNull Call<Survey> call, @NotNull Response<Survey> response) {
                Log.d("RestService.sendNewUser: onResponse", "");
            }

            @Override
            public void onFailure(@NotNull Call<Survey> call, @NotNull Throwable t) {
                Log.d("RestService.sendNewUser: onFailure", t.toString());
            }
        });
    }
}
