package com.example.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.activity.OrderActivity;
import com.example.admin.activity.OrderDetailActivity;
import com.example.admin.model.DeliveryModel;
import com.example.admin.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends FirestoreRecyclerAdapter<OrderModel,OrderAdapter.ViewHolder> {
    Context context;
    Spinner spinner;
    List<DeliveryModel> list = new ArrayList<>();
    List<String> DeliveryList = new ArrayList<>();

    public OrderAdapter(OrderActivity orderActivity, @NonNull FirestoreRecyclerOptions<OrderModel> options, OrderActivity activity) {
        super(options);
        this.context=orderActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OrderModel model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        holder.textView.setText(model.getTotal());
        holder.date.setText(sdf.format(model.getTimestamp()));
        holder.email.setText(model.getEmail());
        holder.address.setText(model.getAddress());
        holder.payment.setText(model.getPayMentMethod());
        holder.number.setText(model.getNumber());
        holder.name.setText(model.getName());

        FirebaseFirestore.getInstance().collection("DELIVERYBOY").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null && !value.isEmpty()) {
                    list = value.toObjects(DeliveryModel.class);
                    for (int i = 0; i < list.size(); i++) {
                        if (DeliveryList.size()<list.size())
                            DeliveryList.add(list.get(i).getName());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, DeliveryList);
                    holder.spinner.setAdapter(arrayAdapter);
                }
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryModel deliveryModel = new DeliveryModel();
                deliveryModel.getdId();

                FirebaseFirestore.getInstance().collection("DELIVERYBOY").whereEqualTo("name",holder.spinner.getSelectedItem().toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        value.getDocuments().get(0).getReference().collection("Order").add(model).addOnSuccessListener(documentReference -> {
                            String docId = documentReference.getId();
                            Map<String, Object> map = new HashMap<>();
                            map.put("oid", docId);
                            documentReference.update(map).addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }).addOnFailureListener(e -> {
                            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, OrderDetailActivity.class);
                intent.putExtra("Model",model);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, date,name,number,address,email,payment;
        LinearLayout linearLayout;
        ImageButton imageButton;
        Spinner spinner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Order_name);
            number=itemView.findViewById(R.id.Order_number);
            payment=itemView.findViewById(R.id.Order_payment);
            address=itemView.findViewById(R.id.Order_address);
            email=itemView.findViewById(R.id.Order_email);
            date = itemView.findViewById(R.id.Order_date);
            textView = itemView.findViewById(R.id.Order_total);
            linearLayout = itemView.findViewById(R.id.Order_click);
            spinner=itemView.findViewById(R.id.Order_spinner);
            imageButton = itemView.findViewById(R.id.Order_imagebutton);
        }
    }
}
