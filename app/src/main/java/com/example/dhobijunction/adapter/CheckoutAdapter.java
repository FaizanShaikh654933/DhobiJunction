package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.CheckoutActivity;
import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.CheckoutModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CheckoutAdapter extends FirestoreRecyclerAdapter<CheckoutModel, CheckoutAdapter.CheckoutViewHolder> {
    Context context;
    CheckoutActivity checkoutActivity;
    public CheckoutAdapter(Context context, @NonNull FirestoreRecyclerOptions<CheckoutModel> options, CheckoutActivity checkoutActivity) {
        super(options);
        this.checkoutActivity = checkoutActivity;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position, @NonNull CheckoutModel model) {
        holder.checkout_name.setText(model.getTitle());
        Glide.with(checkoutActivity).load(model.getImage()).into(holder.checkout_image);
        holder.checkout_price1.setText(model.getTotal());
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_list_view, parent, false);
        return new CheckoutAdapter.CheckoutViewHolder(view);
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {
        TextView checkout_name,checkout_price,checkout_price1;
        ImageView checkout_image;
        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            checkout_name = itemView.findViewById(R.id.checkout_name);
            checkout_price = itemView.findViewById(R.id.checkout_price);
            checkout_price1 = itemView.findViewById(R.id.checkout_price1);
            checkout_image = itemView.findViewById(R.id.checkout_image);
        }
    }
}
