package com.ama.myrecipe.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ama.myrecipe.MainActivity;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.repositories.RecipeRepository;
import com.ama.myrecipe.util.RecipeCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class RecipeViewModel extends ViewModel {
    private MutableLiveData<List<Recipe>> recipes;
    private RecipeRepository recipeRepository;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private LifecycleOwner lifecycleOwner;
    private Activity context;

    public void init(Activity context){
        this.lifecycleOwner = (LifecycleOwner) context;
        this.context = context;
        if (recipes != null) {
            return;
        }
        recipes = new MutableLiveData<List<Recipe>>();
        loadRecipes();
    }
    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    private void loadRecipes() {
        recipeRepository = RecipeRepository.getInstance();
        recipeRepository.getIsUpdating().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    isUpdating.setValue(true);
                }else {
                    isUpdating.postValue(false);
                }
            }
        });

        recipes = recipeRepository.getRecipes();

    }

    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }

    public void publishNewRecipe(Recipe recipe){
        recipeRepository.publishRecipe(recipe, new RecipeCallBack() {
            @Override
            public void onComplete(Recipe recipe) {
                List<Recipe> currentRecipes = new ArrayList<>();
                currentRecipes.add(recipe);
                currentRecipes.addAll(recipes.getValue());
                recipes.postValue(currentRecipes);
                isUpdating.postValue(false);
                context.finish();
            }

            @Override
            public void onDelete(@NonNull boolean isSuccessful) {

            }

            @Override
            public void onUpdate(@NonNull Recipe recipe) {

            }

        });

    }

    public void deleteRecipe(final Recipe recipe) {
        recipeRepository.deleteMyRecipe(recipe.getRecipe_id(), new RecipeCallBack() {
            @Override
            public void onComplete(@NonNull Recipe recipe) {

            }

            @Override
            public void onDelete(@NonNull boolean isSuccessful) {
                List<Recipe> currentRecipes = new ArrayList<>();
                currentRecipes.addAll(recipes.getValue());
                for (int i = 0; i < currentRecipes.size(); i++){
                    if (currentRecipes.get(i).getRecipe_id() == recipe.getRecipe_id()){
                        currentRecipes.remove(i);
                        break;
                    }
                }
                recipes.postValue(currentRecipes);
                isUpdating.postValue(false);
                context.finish();
            }

            @Override
            public void onUpdate(@NonNull Recipe recipe) {

            }
        });
    }

    public void updateRecipe(final Recipe recipe) {
        recipeRepository.updateMyRecipe(recipe, new RecipeCallBack() {
            @Override
            public void onComplete(@NonNull Recipe recipe) {

            }

            @Override
            public void onDelete(@NonNull boolean isSuccessful) {

            }

            @Override
            public void onUpdate(@NonNull Recipe recipe) {
                List<Recipe> currentRecipes = new ArrayList<>();
                currentRecipes.addAll(recipes.getValue());
                for (int i = 0; i < currentRecipes.size(); i++){
                    if (currentRecipes.get(i).getRecipe_id() == recipe.getRecipe_id()){
                        currentRecipes.remove(i);
                        currentRecipes.add(i, recipe);
                        break;
                    }
                }
                recipes.postValue(currentRecipes);
                isUpdating.postValue(false);
                context.finish();
            }

        });
    }
}