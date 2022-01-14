package com.example.esc_walker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Bus> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    public MyAdapter(Context context, ArrayList<Bus> buses){
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
        holder.bus_start.setText(mList.get(position).depPlaceNm);
        holder.bus_arrive.setText(mList.get(position).arrplaceNm);
        holder.bus_charge.setText(mList.get(position).charge);
        holder.bus_arrTime.setText(mList.get(position).arrPlandTime);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView bus_start;
        public TextView bus_arrive;
        public TextView bus_charge;
        public TextView bus_arrTime;

        public MyViewHolder(View view){
            super(view);

            bus_start = view.findViewById(R.id.tv_bus_start);
            bus_arrive = view.findViewById(R.id.tv_bus_arrive);
            bus_charge = view.findViewById(R.id.tv_bus_charge);
            bus_arrTime = view.findViewById(R.id.tv_bus_arrTime);
        }
    }
}
