package com.example.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.activity.DeliveryBoyActivity;
import com.example.admin.model.DeliveryModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DeliveryboyAdapter extends FirestoreRecyclerAdapter<DeliveryModel,DeliveryboyAdapter.ViewHolder> {
    Context context;

    public DeliveryboyAdapter(DeliveryBoyActivity deliveryBoyActivity, @NonNull FirestoreRecyclerOptions<DeliveryModel> options, DeliveryBoyActivity boyActivity) {
        super(options);
        this.context=deliveryBoyActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull DeliveryModel model) {
        holder.t1.setText(model.getName());
        holder.t2.setText(model.getEmail());
        holder.t3.setText(model.getPhone());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.deliveryboy_item,parent,false);
        return new DeliveryboyAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.d_t1);
            t2=itemView.findViewById(R.id.d_t2);
            t3=itemView.findViewById(R.id.d_t3);

        }
    }
}
