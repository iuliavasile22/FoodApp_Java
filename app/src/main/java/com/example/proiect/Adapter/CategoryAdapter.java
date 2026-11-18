package com.example.proiect.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proiect.Domain.CategoriesDomain;
import com.example.proiect.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoriesDomain> categoriesDomains;

    public CategoryAdapter(ArrayList<CategoriesDomain> categoriesDomains) {
        this.categoriesDomains = categoriesDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_viewholder_layout, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoriesDomains.get(position).getTitle());
        String picUrl ="";
        switch (position){
            case 0: {
                picUrl = "hamburger";
                break;
            }
            case 1: {
                picUrl = "pizza";
                break;
            }
            case 2: {
                picUrl = "hotd";
                break;
            }
            case 3: {
                picUrl = "donut";
                break;
            }
            case 4: {
                picUrl = "soda";
                break;
            }
        }

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.categoryPhoto);
    }

    @Override
    public int getItemCount() {
        return categoriesDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryPhoto;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPhoto = itemView.findViewById(R.id.categoryPhoto);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
