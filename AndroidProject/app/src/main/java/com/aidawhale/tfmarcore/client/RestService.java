package com.aidawhale.tfmarcore.client;

import android.util.Log;

import com.aidawhale.tfmarcore.room.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestService {

    public static User user;

    public static void getUserById(String userId, final OnUserPetitionResponse response) {

        // Create a service to access API later
        AnimalRoadtripService serviceSerializer = AnimalRoadtripClient.createServiceSerializer(AnimalRoadtripService.class);
//        AnimalRoadtripService serviceDeserializer = AnimalRoadtripClient.createServiceDeserializer(AnimalRoadtripService.class);
//        AnimalRoadtripService service = AnimalRoadtripClient.createService(AnimalRoadtripService.class);

        // Fetch user by userId
        Call<User> userCall = serviceSerializer.getUserById(userId);

        // Execute the call asynchronously. Get a positive or negative callback.
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    user = response.body();
                    Log.d("RestService.getUserById: onResponse", "Got a response: " + user.userID);
//                    response.onUserPetitionResponse(user);
                } catch (Exception e) {
                    Log.d("RestService.getUserById: onResponse", "Error while getting user.");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                user = null;
                Log.d("RestService.getUserById: onFailure", t.toString());
            }
        });

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            Log.d("RestService.getUserById: onResponse", "Waiting too long for an answer...");
        }
    }

    public static void addNewUser(User user) {

    }
}
