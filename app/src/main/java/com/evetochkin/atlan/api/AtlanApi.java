package com.evetochkin.atlan.api;

import com.evetochkin.atlan.model.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AtlanApi {

    @GET("users/{id}")
    Call<User> users(@Path("id") int user);

    @GET("posts/{id}")
    Call<Post> posts(@Path("id") int post);

    @GET("comments/{id}")
    Call<Comment> comments(@Path("id") int comment);

    @GET("photos/{id}")
    Call<Photo> photos(@Path("id") int photo);

    @GET("todos/{id}")
    Call<Todo> todos(@Path("id") int todo);

}
