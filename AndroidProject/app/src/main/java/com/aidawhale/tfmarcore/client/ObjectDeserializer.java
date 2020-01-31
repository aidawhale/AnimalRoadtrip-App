package com.aidawhale.tfmarcore.client;

import android.util.Log;

import com.aidawhale.tfmarcore.room.Game;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class ObjectDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        Log.d("ObjectDeserializer:", "Obj " + obj.toString());

        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();

        for (Map.Entry<String, JsonElement> e : entries) {
            switch (e.getKey()) {
                case "json_user":
                    Log.d("ObjectDeserializer:", "Obj json_user");
                    return context.deserialize(json, User.class);
                case "json_game":
                    Log.d("ObjectDeserializer:", "Obj json_game");
                    return context.deserialize(json, Game.class);
                case "json_survey":
                    Log.d("ObjectDeserializer:", "Obj json_survey");
                    return context.deserialize(json, Survey.class);
                default:
                    // do nothing
            }
        }

        Log.d("ObjectDeserializer:", "Error deserializing obj " + obj.toString());
        return null;
    }
}
