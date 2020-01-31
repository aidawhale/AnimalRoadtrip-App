package com.aidawhale.tfmarcore.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalRoadtripClient {

    // More info at:
        // https://futurestud.io/tutorials/retrofit-2-creating-a-sustainable-android-client
        // https://futurestud.io/tutorials/retrofit-2-log-requests-and-responses
    // Why is everyhting static?
        // we want to use the same objects (OkHttpClient, Retrofit, …) throughout the app
        // to just open one socket connection that handles all the request and responses
        // including caching and many more

    private static JsonSerializer serializer = new ObjectSerializer();
    private static JsonDeserializer deserializer = new ObjectDeserializer();

    private static Gson gsonSerializer = new GsonBuilder()
            .registerTypeAdapter(Object.class, serializer)
            .create();
    private static Gson gsonDeserializer = new GsonBuilder()
            .registerTypeAdapter(Object.class, deserializer)
            .create();

    private static Retrofit.Builder builderSerializer = new Retrofit.Builder()
            .baseUrl(ApiRoutes.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonSerializer));
    private static Retrofit.Builder builderDeserializer = new Retrofit.Builder()
            .baseUrl(ApiRoutes.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDeserializer));
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiRoutes.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit retrofitSerializer = builderSerializer.build();
    private static Retrofit retrofitDeserializer = builderDeserializer.build();
    private static Retrofit retrofit = builder
            .client(httpClient.build())
            .build();

    AnimalRoadtripClient client = retrofit.create(AnimalRoadtripClient.class);

    public static <S> S createServiceSerializer(Class<S> serviceClass) {
        // Check if the logging interceptor is already present
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builderSerializer.client(httpClient.build());
            retrofit = builderSerializer.build();
        }
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceDeserializer(Class<S> serviceClass) {
        // Check if the logging interceptor is already present
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builderDeserializer.client(httpClient.build());
            retrofit = builderDeserializer.build();
        }
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        // Check if the logging interceptor is already present
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
