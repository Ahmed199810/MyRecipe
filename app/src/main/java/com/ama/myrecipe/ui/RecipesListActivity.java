package com.ama.myrecipe.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ama.myrecipe.MainActivity;
import com.ama.myrecipe.R;
import com.ama.myrecipe.adapters.RecipeAdapter;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.viewmodels.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecipesListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton btnNew;
    private ProgressBar pb;
    private RecipeViewModel recipeViewModel;
    private RecipeAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        // init views
        initViews();
        setSupportActionBar(toolbar);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.init(this);
        recipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> list) {
                initRecyclerView();
                adapter.notifyDataSetChanged();
            }
        });

        recipeViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    showProgressBar();
                }else {
                    hideProgressBar();
                }
            }
        });


        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesListActivity.this, AddNewRecipeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initRecyclerView() {
        adapter = new RecipeAdapter(this, recipeViewModel.getRecipes().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        pb.setVisibility(View.GONE);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        btnNew = findViewById(R.id.fab);
        pb = findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.toolbar);
    }


}

/*

 */