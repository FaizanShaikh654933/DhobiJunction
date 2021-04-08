package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhobijunction.R;
import com.example.dhobijunction.model.ProductModel;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> {
    Context context;
    List<ProductModel> list;

    public ProductListAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_view, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductHolder holder, final int position) {
        holder.product_name.setText(list.get(position).getProduct_name());
        holder.product_price.setText(" ₹ " + list.get(position).getProduct_price());
        holder.product_Rs.setText(" Per " + list.get(position).getProduct_Rs());
        holder.product_total_price.setText(" ₹ " + list.get(position).getProduct_price());
        Glide.with(context).load(list.get(position).getProduct_image()).into(holder.product_image);

        holder.product_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(String.valueOf(holder.product_number.getText()));

                if (count > 1) {
                    count--;
                    holder.product_number.setText(String.valueOf(count));
                    holder.product_total_price.setText(" ₹ " + (count * Integer.parseInt(list.get(position).getProduct_price())));
                }
            }
        });

        holder.product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(String.valueOf(holder.product_number.getText()));
                    count++;
                    holder.product_number.setText(String.valueOf(count));
                    holder.product_total_price.setText(" ₹ " + (count * Integer.parseInt(list.get(position).getProduct_price())));

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_price, product_Rs, product_number, product_total, product_total_price;
        ImageView product_image;
        ImageButton product_remove, product_add;
        Button product_button;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            product_total_price = itemView.findViewById(R.id.product_total_price);
            product_total = itemView.findViewById(R.id.product_total);
            product_button = itemView.findViewById(R.id.product_button);
            product_number = itemView.findViewById(R.id.product_number);
            product_remove = itemView.findViewById(R.id.product_remove);
            product_add = itemView.findViewById(R.id.product_add);
            product_Rs = itemView.findViewById(R.id.product_Rs);
            product_price = itemView.findViewById(R.id.product_price);
            product_name = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
        }
    }
}
