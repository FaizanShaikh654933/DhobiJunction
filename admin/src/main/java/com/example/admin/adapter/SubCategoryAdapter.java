package com.example.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.activity.SubCategoryActivity;
import com.example.admin.model.CategoryModel;
import com.example.admin.model.SubCategoryModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.Viewholder> {
    Context context;
    List<SubCategoryModel> modelList;
    public SubCategoryAdapter(SubCategoryActivity subcategoryActivity, List<SubCategoryModel> modelList) {
        this.context=subcategoryActivity;
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.subcategory_item,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.textView.setText(modelList.get(position).getTitle());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editText.setVisibility(View.VISIBLE);
                holder.textView.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
                holder.update.setVisibility(View.VISIBLE);
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,Object> map=new HashMap<>();
                map.put("title",holder.editText.getText().toString());
                FirebaseFirestore.getInstance().collection("SUB_CATEGORY").whereEqualTo("sId",modelList.get(position).getsId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (value !=null && !value.isEmpty()){
                            holder.update.setVisibility(View.GONE);
                            holder.editText.setVisibility(View.GONE);
                            holder.textView.setVisibility(View.VISIBLE);
                            value.getDocuments().get(0).getReference().update(map);
                            map.clear();
                        }
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CategoryModel categoryModel=new CategoryModel();
                FirebaseFirestore.getInstance().collection("SUB_CATEGORY").whereEqualTo("sId",modelList.get(position).getsId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        return modelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView textView;
        EditText editText;
        ImageButton edit,update,delete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.t1);
            editText=itemView.findViewById(R.id.e1);
            edit=itemView.findViewById(R.id.edit);
            update=itemView.findViewById(R.id.update);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
