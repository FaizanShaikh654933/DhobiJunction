package com.example.dhobijunction.adapter;

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
import com.example.dhobijunction.fragment.ProductFragment;
import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.ProModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ProductAdapter extends FirestoreRecyclerAdapter<ProModel, ProductAdapter.ProductViewHolder> {
    ProductFragment productFragment;
    FirestoreRecyclerOptions<ProModel> rvOptions;
    OnAddToCartListner listner;

    public ProductAdapter(ProductFragment productFragment, FirestoreRecyclerOptions<ProModel> rvOptions, OnAddToCartListner listner) {
        super(rvOptions);
        this.productFragment = productFragment;
        this.rvOptions = rvOptions;
        this.listner = listner;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position, @NonNull final ProModel model) {
        holder.product_name.setText(model.getTitle());
        holder.product_price.setText(" ₹ " + model.getPrice());
        // holder.product_kg_gm.setText(" Per " + list.get(position).getProduct_kg_gm());
        holder.product_total_price.setText(" ₹ " + model.getPrice());
        Glide.with(productFragment).load(model.getImage()).into(holder.product_image);

        holder.product_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(String.valueOf(holder.product_number.getText()));

                if (count > 1) {
                    count--;
                    holder.product_number.setText(String.valueOf(count));
                    holder.product_total_price.setText(" ₹ " + (count * Integer.parseInt(model.getPrice())));
                }
            }
        });

        holder.product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(String.valueOf(holder.product_number.getText()));
                count++;
                holder.product_number.setText(String.valueOf(count));
                holder.product_total_price.setText(" ₹ " + (count * Integer.parseInt(model.getPrice())));


            }
        });

        holder.product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.addToCart(model, holder.product_number.getText().toString());
            }
        });
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_view, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_price, product_kg_gm, product_number, product_total, product_total_price;
        ImageView product_image;
        ImageButton product_remove, product_add;
        Button product_button;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_total_price = itemView.findViewById(R.id.product_total_price);
            product_total = itemView.findViewById(R.id.product_total);
            product_button = itemView.findViewById(R.id.product_button);
            product_number = itemView.findViewById(R.id.product_number);
            product_remove = itemView.findViewById(R.id.product_remove);
            product_add = itemView.findViewById(R.id.product_add);
            product_kg_gm = itemView.findViewById(R.id.product_kg_gm);
            product_price = itemView.findViewById(R.id.product_price);
            product_name = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
        }
    }
}
