package com.ama.myrecipe.util;

import com.ama.myrecipe.models.Recipe;

import androidx.annotation.NonNull;

public interface RecipeCallBack {
    void onComplete(@NonNull Recipe recipe);

    void onDelete(@NonNull boolean isSuccessful);

    void onUpdate(@NonNull Recipe recipe);
}
