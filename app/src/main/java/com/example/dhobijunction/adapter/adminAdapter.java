package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.t1.setText(list.get(position).getTitle());
        holder.i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.i3.setVisibility(View.GONE);
                holder.i2.setVisibility(View.GONE);
                holder.i1.setVisibility(View.VISIBLE);
                holder.e1.setVisibility(View.GONE);
                holder.t1.setVisibility(View.VISIBLE);
            }
        });
        holder.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.i1.setVisibility(View.GONE);
                holder.i3.setVisibility(View.VISIBLE);
                holder.i2.setVisibility(View.VISIBLE);
                holder.e1.setVisibility(View.VISIBLE);
                holder.t1.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        EditText e1;
        ImageButton i1,i2,i3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            i1 = itemView.findViewById(R.id.i1);
            i2 = itemView.findViewById(R.id.i2);
            i3 = itemView.findViewById(R.id.i3);
            e1 = itemView.findViewById(R.id.e1);
        }
    }
}
