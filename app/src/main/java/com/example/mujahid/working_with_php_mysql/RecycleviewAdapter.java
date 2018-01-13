package com.example.mujahid.working_with_php_mysql;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mujahid on 1/13/2018.
 */

public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.MyViewHolder> {
     ArrayList<contact> contactsList = new ArrayList<>();

    public RecycleviewAdapter(ArrayList<contact> list){
        contactsList = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclecustom,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(contactsList.get(position).getName());
        holder.father_name.setText(contactsList.get(position).getFather_name());
        holder.email.setText(contactsList.get(position).getEmail());
        holder.phone.setText(contactsList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, father_name, email, phone;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.show_name);
            father_name = itemView.findViewById(R.id.show_fatherName);
            email =  itemView.findViewById(R.id.show_email);
            phone = itemView.findViewById(R.id.show_phone);
        }
    }
}
