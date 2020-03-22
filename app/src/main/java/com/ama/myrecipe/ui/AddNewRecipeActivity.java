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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ama.myrecipe.R;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.viewmodels.RecipeViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddNewRecipeActivity extends AppCompatActivity {

    private TextView txtTitle, txtPublisher, txtDesc;
    private ImageView imgRecipe;
    private Button btnPublish;
    private String img_url = "";
    private RecipeViewModel recipeViewModel;
    private ProgressBar pb;
    private int GALLERY_REQUEST = 100;
    private Uri imgUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        // init views
        initViews();

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
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });

        imgRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGallery();
            }
        });
    }

    private void publish() {
        String title = txtTitle.getText().toString();
        String publisher = txtPublisher.getText().toString();
        String desc = txtDesc.getText().toString();

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

        Recipe recipe = new Recipe(
                title,
                publisher,
                img_url,
                0,
                desc
        );

        String img = ImgToString();
        recipe.setImage_url(img);
        recipeViewModel.publishNewRecipe(recipe);

    }

    private void viewGallery() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i , GALLERY_REQUEST);
    }

    private void initViews() {
        txtTitle = findViewById(R.id.txt_title);
        txtPublisher = findViewById(R.id.txt_publisher);
        txtDesc = findViewById(R.id.txt_desc);
        btnPublish = findViewById(R.id.btn_publish);
        imgRecipe = findViewById(R.id.img_recipe);
        pb = findViewById(R.id.progress_bar);
    }

    private void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        pb.setVisibility(View.GONE);
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


}