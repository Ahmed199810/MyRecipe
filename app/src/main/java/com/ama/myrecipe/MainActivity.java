package com.ama.myrecipe;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ama.myrecipe.adapters.RecipeAdapter;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.ui.AddNewRecipeActivity;
import com.ama.myrecipe.ui.RecipesListActivity;
import com.ama.myrecipe.viewmodels.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardView cardBarbecue;
    private CardView cardBreakfast;
    private CardView cardDinner;
    private CardView cardBeef;
    private CardView cardEgyptian;
    private CardView cardFruits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init views
        initViews();

        cardBarbecue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipesListActivity.class);
                startActivity(intent);
            }
        });


        cardBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipesListActivity.class);
                startActivity(intent);
            }
        });


        cardDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipesListActivity.class);
                startActivity(intent);
            }
        });


        cardBeef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipesListActivity.class);
                startActivity(intent);
            }
        });


        cardEgyptian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipesListActivity.class);
                startActivity(intent);
            }
        });


        cardFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipesListActivity.class);
                startActivity(intent);
            }
        });



    }

    private void initViews() {

        cardBarbecue = findViewById(R.id.card_barbecue);
        cardBreakfast = findViewById(R.id.card_breakfast);
        cardDinner = findViewById(R.id.card_dinner);
        cardBeef = findViewById(R.id.card_beef);
        cardEgyptian = findViewById(R.id.card_egy);
        cardFruits = findViewById(R.id.card_fruits);

    }
}