package com.evetochkin.atlan;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import com.evetochkin.atlan.api.AtlanApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtlanApp extends Application {
    private static final String ATLAN_API_BASE_URL = "http://jsonplaceholder.typicode.com/";
    public static final int LOADER_USERS = 1;
    private AtlanApi atlanApi;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .setLenient()
                .create();
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG
                        ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE))

                .build();

        Retrofit retrofit = configureRetrofit(ATLAN_API_BASE_URL, gson, client);
        atlanApi = retrofit.create(AtlanApi.class);
    }

    private Retrofit configureRetrofit(String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public AtlanApi atlanApi() {
        return atlanApi;
    }
}
