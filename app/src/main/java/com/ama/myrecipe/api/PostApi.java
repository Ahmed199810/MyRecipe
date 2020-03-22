package com.ama.myrecipe.api;

import com.ama.myrecipe.models.Comment;
import com.ama.myrecipe.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") int id,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(
            @FieldMap Map<String, String> parameters
    );

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(
            @Path("id") int postId
    );

    @POST("posts")
    Call<Post> createPost(
            @Body Post post
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @FieldMap Map<String, String> fields
    );

    @PUT("posts/{id}") // changes the whole post
    Call<Post> putPost(
            @Path("id") int id,
            @Body Post post
    );

    @PATCH("posts/{id}") // changes only the sent parameters or ignores the null values
    Call<Post> patchPost(
            @Path("id") int id,
            @Body Post post
    );

    @DELETE("posts/{id}")
    Call<Void> deletePost(
            @Path("id") int id
    );

}