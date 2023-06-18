package com.cs360.inventoryapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AboutItem extends RecyclerView.Adapter<AboutItem.MyViewHolder>{
    private final Context context;
    Activity activity;
    private final ArrayList itemId;
    private final ArrayList itemName;
    private final ArrayList itemDescription;
    private final ArrayList itemQuantity;

    AboutItem(Activity activity, Context context,
              ArrayList itemId,
              ArrayList itemName,
              ArrayList itemDescription,
              ArrayList itemQuantity){
        this.activity = activity;
        this.context = context;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;

    }

    //****************************************
    //
    // Methods to fill the row on the home screen
    // access the db and fill the home screen
    // with data
    //
    //****************************************

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.itemName.setText(String.valueOf(itemName.get(position)));
        holder.itemQuantity.setText(String.valueOf(itemQuantity.get(position)));
        holder.itemDescription.setText(String.valueOf(itemDescription.get(position)));


        holder.itemView.findViewById(R.id.itemNameButton).setOnClickListener((view -> {
            Intent intent = new Intent(context, ItemInfo.class);
            intent.putExtra("itemId", String.valueOf(itemId.get(position)));
            intent.putExtra("itemName", String.valueOf(itemName.get(position)));
            intent.putExtra("itemDescription", String.valueOf(itemDescription.get(position)));
            intent.putExtra("itemQuantity", String.valueOf(itemQuantity.get(position)));
            activity.startActivityForResult(intent, 1);
        }));

    }

    @Override
    public int getItemCount() {
        return itemId.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemDescription, itemQuantity;
        Button itemName;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameButton);
            itemDescription = itemView.findViewById(R.id.itemViewDescription);
            itemQuantity = itemView.findViewById(R.id.itemViewQuantity);
            mainLayout = itemView.findViewById((R.id.mainLayout));

        }
    }

}

