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
import com.example.admin.activity.CategoryActivity;
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

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    Context context;
    List<OffersModel>list;

    public OffersAdapter(OffersActivity offersActivity, List<OffersModel> list) {
        this.context=offersActivity;
        this.list=list;
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {
        holder.t1.setText(list.get(position).getTitle());
        holder.t2.setText(list.get(position).getCode());
        holder.t3.setText(list.get(position).getPrice());
        holder.t4.setText(list.get(position).getDate());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffersModel offersModel=new OffersModel();
                FirebaseFirestore.getInstance().collection("OFFERS").whereEqualTo("offerId",list.get(position).getOfferId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value !=null&& !value.isEmpty()){

                            value.getDocuments().get(0).getReference().delete();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.offers_item,parent,false);
        return new OffersAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.o_t1);
            t2=itemView.findViewById(R.id.o_t2);
            t3=itemView.findViewById(R.id.o_t3);
            t4=itemView.findViewById(R.id.o_t4);
            delete=itemView.findViewById(R.id.o_delete);
        }
    }
}