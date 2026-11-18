package com.example.proiect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proiect.Domain.FoodDomain;
import com.example.proiect.Helper.CartManager;
import com.example.proiect.Interface.ChangeIndexItemsListener;
import com.example.proiect.R;

import java.util.ArrayList;

public class CartActivityAdapter extends RecyclerView.Adapter<CartActivityAdapter.ViewHolder> {

    private ArrayList<FoodDomain> listFoodsSelected;
    private CartManager cartManager;
    ChangeIndexItemsListener changeIndexItemsListener;

    public CartActivityAdapter(ArrayList<FoodDomain> listFoodsSelected, Context context, ChangeIndexItemsListener changeIndexItemsListener) {
        this.listFoodsSelected= listFoodsSelected;
        cartManager = new CartManager(context);
        this.changeIndexItemsListener = changeIndexItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholdercart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain foodItem = listFoodsSelected.get(holder.getAdapterPosition());
        holder.title.setText(foodItem.getTitle());
        holder.feePerItem.setText("$" + foodItem.getFee());
        holder.total.setText("$" + Math.round(foodItem.getIndexInCart() * foodItem.getFee()));
        holder.num.setText(String.valueOf(foodItem.getIndexInCart()));
        String picUrl = "";
        switch (holder.getAdapterPosition()) {
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
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.photo);

        holder.plusItem.setOnClickListener(v -> cartManager.plusIndexFood(listFoodsSelected, holder.getAdapterPosition(), () -> {
            notifyDataSetChanged();
            changeIndexItemsListener.changed();
        }));

        holder.minusItem.setOnClickListener(v -> cartManager.minusIndexFood(listFoodsSelected, holder.getAdapterPosition(), () -> {
            notifyDataSetChanged();
            changeIndexItemsListener.changed();
        }));
    }

    @Override
    public int getItemCount() {
        return listFoodsSelected.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, feePerItem;
        public ImageView photo, plusItem, minusItem;

        TextView total, num;

        public ConstraintLayout viewLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_product);
            total= itemView.findViewById(R.id.total);
            feePerItem = itemView.findViewById(R.id.feePerItem);
            photo = itemView.findViewById(R.id.photo);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
           viewLayout = itemView.findViewById(R.id.viewLayout);
           num = itemView.findViewById(R.id.numberItemT);


        }
    }
}