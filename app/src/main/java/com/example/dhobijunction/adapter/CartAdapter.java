package com.example.dhobijunction.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhobijunction.R;
import com.example.dhobijunction.model.CartModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Empty;

import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends FirestoreRecyclerAdapter<CartModel, CartAdapter.CartViewHolder> {
    FragmentActivity cartFragment;
    OnQtyUpdate qty;
    int cartTotal = 0;
    SharedPreferences preferences;

    public CartAdapter(FragmentActivity cartFragment, @NonNull FirestoreRecyclerOptions<CartModel> options, OnQtyUpdate qty) {
        super(options);
        this.cartFragment = cartFragment;
        this.qty = qty;
    }

    @Override
    protected void onBindViewHolder(@NonNull final CartViewHolder holder, final int position, @NonNull final CartModel model) {
        holder.cart_name.setText(model.getTitle());
        holder.cart_price.setText(" ₹ " + model.getPrice());
        preferences=cartFragment.getSharedPreferences("Users", Context.MODE_PRIVATE);
        // holder.product_kg_gm.setText(" Per " + list.get(position).getProduct_kg_gm());

      //  holder.cart_total_price.setText(" ₹ " + String.valueOf(Integer.parseInt(model.getQty().trim()) *Integer.parseInt(model.getPrice())));
        FirebaseFirestore.getInstance().collection("USERS").document(preferences.getString("userMobile",""))
                .collection("USERCART").whereEqualTo("pId",model.getpId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                holder.cart_total_price.setText(" ₹ " + String.valueOf(Integer.parseInt(value.getDocuments().get(0).get("qty").toString())
                        *Integer.parseInt(value.getDocuments().get(0).get("price").toString())));
            }
        });
        holder.cart_number.setText(model.getQty());
        Glide.with(cartFragment).load(model.getImage()).into(holder.cart_image);

        holder.cart_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cart_update.setVisibility(View.GONE);
                holder.cart_remove.setVisibility(View.GONE);
                holder.cart_number.setVisibility(View.GONE);
                holder.cart_add.setVisibility(View.GONE);
                holder.cart_edit.setVisibility(View.VISIBLE);
                qty.getQty(holder.cart_number.getText().toString(),model);


            }
        });
        holder.cart_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("USERS").document(preferences.getString("userMobile",""))
                        .collection("USERCART")
                        .whereEqualTo("cartItemId",model.getCartItemId())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value!=null && !value.isEmpty()){
                            value.getDocuments().get(0).getReference().delete();
                        }
                    }
                });
            }
        });

        holder.cart_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cart_edit.setVisibility(View.GONE);
                holder.cart_remove.setVisibility(View.VISIBLE);
                holder.cart_number.setVisibility(View.VISIBLE);
                holder.cart_add.setVisibility(View.VISIBLE);
                holder.cart_update.setVisibility(View.VISIBLE);
            }
        });

        holder.cart_remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(String.valueOf(holder.cart_number.getText()));

                if (count > 1) {
                    count--;
                    holder.cart_number.setText(String.valueOf(count));
                    holder.cart_total_price.setText(" ₹ " + (count * Integer.parseInt(model.getPrice())));
                }
            }
        });

        holder.cart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(String.valueOf(holder.cart_number.getText()));

                count++;
                holder.cart_number.setText(String.valueOf(count));
                holder.cart_total_price.setText(" ₹ " + (count * Integer.parseInt(model.getPrice())));
            }
        });

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_view, parent, false);
        return new CartAdapter.CartViewHolder(view);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView cart_name, cart_price, cart_kg_gm, cart_total, cart_total_price, cart_number;
        ImageView cart_image;
        ImageButton cart_remove, cart_add, cart_edit, cart_delete,cart_update;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_name = itemView.findViewById(R.id.cart_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_kg_gm = itemView.findViewById(R.id.cart_kg_gm);
            cart_total = itemView.findViewById(R.id.cart_total);
            cart_total_price = itemView.findViewById(R.id.cart_total_price);
            cart_image = itemView.findViewById(R.id.cart_image);
            cart_remove = itemView.findViewById(R.id.cart_remove);
            cart_add = itemView.findViewById(R.id.cart_add);
            cart_number = itemView.findViewById(R.id.cart_number);
            cart_edit = itemView.findViewById(R.id.cart_edit);
            cart_delete = itemView.findViewById(R.id.cart_delete);
            cart_update = itemView.findViewById(R.id.cart_update);
        }
    }
}
