package com.example.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.activity.DeliveryBoyActivity;
import com.example.admin.activity.OffersActivity;
import com.example.admin.model.CategoryModel;
import com.example.admin.model.DeliveryModel;
import com.example.admin.model.OffersModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OffersAdapter extends FirestoreRecyclerAdapter<OffersModel,OffersAdapter.ViewHolder> {
    Context context;

    public OffersAdapter(OffersActivity offersActivity, @NonNull FirestoreRecyclerOptions<OffersModel> options, OffersActivity activity) {
        super(options);
        this.context=offersActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position, @NonNull OffersModel model) {
        holder.t1.setText(model.getTitle());
        holder.t2.setText(model.getCode());
        holder.t3.setText(model.getPrice());
        holder.t4.setText(model.getDate());
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.offers_item,parent,false);
        return new OffersAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.o_t1);
            t2=itemView.findViewById(R.id.o_t2);
            t3=itemView.findViewById(R.id.o_t3);
            t4=itemView.findViewById(R.id.o_t4);

        }
    }
}