package com.example.proiect.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proiect.Activity.ShowDetailActivity;
import com.example.proiect.Domain.FoodDomain;
import com.example.proiect.R;

import java.util.ArrayList;

public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.ViewHolder> {

    private ArrayList<FoodDomain> popularFoods;

    public PopularFoodAdapter(ArrayList<FoodDomain> popularFoods) {
        this.popularFoods = popularFoods;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(popularFoods.get(position).getTitle());
        holder.price.setText(String.valueOf(popularFoods.get(position).getPrice()));
        String picUrl ="";
        switch (position){
            case 0: {
                picUrl = "hamburger";
                break;
            }
            case 1: {
                picUrl = "pepperoni_pizza";
                break;
            }
            case 2: {
                picUrl = "vegan_pizza";
                break;
            }
        }

        int drawableResourceId = holder.itemView.getContext().getResources().
                getIdentifier(picUrl,"drawable",
                        holder.itemView.getContext().getPackageName());
        Log.d("ShowDetailActivity", "Drawable Resource ID: " + drawableResourceId);
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.photo);


        holder.addButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
            intent.putExtra("obj", popularFoods.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return popularFoods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView photo;
        public ImageView addButton;

        public ConstraintLayout viewLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            price = itemView.findViewById(R.id.price);
            photo = itemView.findViewById(R.id.photo);
            addButton = itemView.findViewById(R.id.addBtn);
           viewLayout = itemView.findViewById(R.id.viewLayout);


        }
    }
}
