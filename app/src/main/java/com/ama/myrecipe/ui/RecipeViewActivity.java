package com.ama.myrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ama.myrecipe.R;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.viewmodels.RecipeViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class RecipeViewActivity extends AppCompatActivity {

    private EditText txtTitle;
    private EditText txtDesc;
    private TextView txtRank;
    private EditText txtPublisher;
    private ImageView imgRecipe;
    private FloatingActionButton btnDel;
    private FloatingActionButton btnEdit;
    private RecipeViewModel recipeViewModel;
    private ProgressBar pb;
    private boolean edit = false;
    private int GALLERY_REQUEST = 100;
    private Uri imgUri;
    private Bitmap bitmap;
    private String img_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        // init views
        initViews();

        Intent intent = getIntent();
        final Recipe recipe = intent.getParcelableExtra("recipe");
        viewData(recipe);


        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.init(this);
        recipeViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    showProgressBar();
                }else {
                    hideProgressBar();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeViewModel.deleteRecipe(recipe);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit){
                    publish(recipe);
                    disableViews(txtDesc, txtPublisher, txtTitle, imgRecipe);
                    edit = false;
                }else {
                    enableViews(txtDesc, txtPublisher, txtTitle, imgRecipe);
                    edit = true;
                }

            }
        });

        imgRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGallery();
            }
        });

    }

    private void viewGallery() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i , GALLERY_REQUEST);
    }

    private void viewData(Recipe recipe) {
        txtTitle.setText(recipe.getTitle());
        txtDesc.setText(recipe.getDesc());
        txtPublisher.setText(recipe.getPublisher());
        txtRank.setText(recipe.getSocial_rank() + "");
        Glide.with(RecipeViewActivity.this).load(recipe.getImage_url()).into(imgRecipe);
    }

    private void initViews() {
        txtTitle = findViewById(R.id.txt_recipe_title);
        txtRank = findViewById(R.id.txt_recipe_social_score);
        txtDesc = findViewById(R.id.txt_recipe_desc);
        txtPublisher = findViewById(R.id.txt_recipe_publisher);
        imgRecipe = findViewById(R.id.img_recipe);
        btnDel = findViewById(R.id.btn_del);
        btnEdit = findViewById(R.id.btn_edit);
        pb = findViewById(R.id.progress_bar);

    }

    private void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        pb.setVisibility(View.GONE);
    }

    private void disableViews(View ...views){
        for (View view : views){
            view.setEnabled(false);
            view.setClickable(false);
        }
    }

    private void enableViews(View ...views){
        for (View view : views){
            view.setEnabled(true);
            view.setClickable(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST){
            imgUri = data.getData();
            img_url = imgUri.toString();
            imgRecipe.setImageURI(imgUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            }catch (Exception e){

            }
        }
    }

    private String ImgToString(){
        if (bitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] image = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(image, Base64.DEFAULT);
        }else {
            return "";
        }
    }

    private void publish(Recipe recipe) {
        String title = txtTitle.getText().toString();
        String publisher = txtPublisher.getText().toString();
        String desc = txtDesc.getText().toString();
        float rank = Float.parseFloat(txtRank.getText().toString());

        if (TextUtils.isEmpty(title)){
            txtTitle.setError("Required !");
            return;
        }
        if (TextUtils.isEmpty(publisher)){
            txtPublisher.setError("Required !");
            return;
        }
        if (TextUtils.isEmpty(desc)){
            txtDesc.setError("Required !");
            return;
        }

        String img = ImgToString();
        recipe.setTitle(title);
        recipe.setPublisher(publisher);
        recipe.setImage_url(img);
        recipe.setSocial_rank(rank);
        recipe.setDesc(desc);

        recipeViewModel.updateRecipe(recipe);

    }


}