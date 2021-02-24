package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.admin;
import com.example.dhobijunction.model.adminModel;

import java.util.List;

public class adminAdapter extends RecyclerView.Adapter<adminAdapter.ViewHolder> {
    Context context;
    List<adminModel> list;
    public adminAdapter(admin admin, List<adminModel> list) {
  this.context=admin;
  this.list=list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.t1.setText(list.get(position).getTitle());
        holder.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageButton i1,i2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            i1 = itemView.findViewById(R.id.i1);
            i2 = itemView.findViewById(R.id.i2);
        }
    }
}
