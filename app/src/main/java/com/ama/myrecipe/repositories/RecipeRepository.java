package com.ama.myrecipe.repositories;

import com.ama.myrecipe.api.RecipeApi;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.util.Constants;
import com.ama.myrecipe.util.RecipeCallBack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepository {
    private static RecipeApi recipeApi;
    private MutableLiveData<List<Recipe>> recipes;
    private static RecipeRepository instance;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

    public static RecipeRepository getInstance(){
        if (instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    public RecipeRepository(){
        isUpdating.setValue(true);
        recipes = new MutableLiveData<List<Recipe>>();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        recipeApi = retrofit.create(RecipeApi.class);


        Call<List<Recipe>> call = recipeApi.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                recipes.setValue(response.body());
                isUpdating.postValue(false);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                isUpdating.postValue(false);
            }
        });

    }

    public void publishRecipe(final Recipe recipe, @NonNull final RecipeCallBack recipeCallBack){
        isUpdating.setValue(true);
        Call<Recipe> call = recipeApi.insertRecipe(
                recipe.getTitle(),
                recipe.getPublisher(),
                recipe.getImage_url(),
                recipe.getSocial_rank(),
                recipe.getDesc()
        );
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (!response.isSuccessful()){
                    return;
                }
                recipeCallBack.onComplete(response.body());
                isUpdating.postValue(false);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                isUpdating.postValue(false);
            }
        });

    }

    public MutableLiveData<List<Recipe>> getRecipes(){
        return recipes;
    }

    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }

    public void deleteMyRecipe(Integer recipe_id, final RecipeCallBack recipeCallBack) {
        isUpdating.setValue(true);
        Call<Boolean> call = recipeApi.deleteRecipe(recipe_id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean isSuccessful = response.body().booleanValue();
                if (isSuccessful){
                    recipeCallBack.onDelete(true);
                }else{
                    recipeCallBack.onDelete(false);
                }
                isUpdating.postValue(false);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void updateMyRecipe(Recipe recipe, final RecipeCallBack recipeCallBack) {
        isUpdating.setValue(true);
        Call<Recipe> call = recipeApi.updateRecipe(
                recipe.getRecipe_id(),
                recipe.getTitle(),
                recipe.getPublisher(),
                recipe.getImage_url(),
                recipe.getSocial_rank(),
                recipe.getDesc()
        );
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (!response.isSuccessful()){
                    return;
                }
                recipeCallBack.onUpdate(response.body());
                isUpdating.postValue(false);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }
}