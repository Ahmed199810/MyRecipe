package com.ama.myrecipe.api;

import android.content.Intent;

import com.ama.myrecipe.models.Recipe;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RecipeApi {

    // GET ALL RECIPES
    @GET("recipes/get.php")
    Call<List<Recipe>> getRecipes();

    @FormUrlEncoded
    @POST("recipes/insert.php")
    Call<Recipe> insertRecipe(
            @Field("title") String title,
            @Field("publisher") String publisher,
            @Field("image_url") String image_url,
            @Field("social_rank")  float social_rank,
            @Field("body") String body
    );

    @POST("recipes/insert.php")
    Call<Recipe> insertRecipe(
            @Body Recipe recipe
    );

    @FormUrlEncoded
    @POST("recipes/delete.php")
    Call<Boolean> deleteRecipe(
            @Field("recipe_id") Integer id
    );

    @FormUrlEncoded
    @POST("recipes/update.php")
    Call<Recipe> updateRecipe(
            @Field("recipe_id") Integer id,
            @Field("title") String title,
            @Field("publisher") String publisher,
            @Field("image_url") String image_url,
            @Field("social_rank")  float social_rank,
            @Field("body") String body
    );


}