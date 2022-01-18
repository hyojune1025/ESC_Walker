package com.example.esc_walker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_bus extends RecyclerView.Adapter<MyAdapter_bus.MyViewHolder> {
    private ArrayList<Bus> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    public MyAdapter_bus(Context context, ArrayList<Bus> buses){
        this.mList = buses;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_list_bus,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bus_grade.setText(mList.get(position).grade);
        holder.bus_charge.setText(mList.get(position).charge);
        holder.bus_depTime.setText(mList.get(position).depTime);
        holder.bus_arrTime.setText(mList.get(position).arrTime);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView bus_depTime;
        public TextView bus_grade;
        public TextView bus_charge;
        public TextView bus_arrTime;

        public MyViewHolder(View view){
            super(view);

            bus_depTime = view.findViewById(R.id.tv_bus_depTime);
            bus_grade = view.findViewById(R.id.tv_bus_grade);
            bus_charge = view.findViewById(R.id.tv_bus_charge);
            bus_arrTime = view.findViewById(R.id.tv_bus_arrTime);
        }
    }
}
