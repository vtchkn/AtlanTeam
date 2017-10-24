package com.evetochkin.atlan.api;

import com.evetochkin.atlan.model.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AtlanApi {

    @GET("users/{id}")
    Call<User> users(@Path("id") int user);

    @GET("posts")
    Call<Post> posts(int post);

    @GET("comments")
    Call<Comment> comments(int comment);

    @GET("photos")
    Call<Photo> photos(int photo);

    @GET("todos")
    Call<Todo> todos(int todo);

}
