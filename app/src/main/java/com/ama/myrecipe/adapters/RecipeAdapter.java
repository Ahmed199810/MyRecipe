package com.ama.myrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ama.myrecipe.MainActivity;
import com.ama.myrecipe.R;
import com.ama.myrecipe.models.Recipe;
import com.ama.myrecipe.ui.RecipeViewActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> list = new ArrayList<>();
    private Context context;

    public RecipeAdapter(Context context, List<Recipe> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolder)viewHolder).txtTitle.setText(list.get(i).getTitle());
        ((ViewHolder)viewHolder).txtPublisher.setText(list.get(i).getPublisher());
        ((ViewHolder)viewHolder).txtScore.setText(list.get(i).getSocial_rank() + "");
        Glide.with(context).load(list.get(i).getImage_url()).into(((ViewHolder)viewHolder).imgDesc);

        final Recipe recipe = list.get(i);
        ((ViewHolder) viewHolder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeViewActivity.class);
                intent.putExtra("recipe", recipe);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtPublisher;
        private TextView txtScore;
        private ImageView imgDesc;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_recipe_title);
            txtPublisher = itemView.findViewById(R.id.txt_recipe_publisher);
            txtScore = itemView.findViewById(R.id.txt_recipe_social_score);
            imgDesc = itemView.findViewById(R.id.img_recipe);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
