package com.example.esc_walker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_plane extends RecyclerView.Adapter<MyAdapter_plane.MyViewHolder> {
    private ArrayList<Plane> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    public MyAdapter_plane(Context context, ArrayList<Plane> planes){
        this.mList = planes;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_list_plane,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.plane_grade.setText(mList.get(position).airline);
        holder.plane_charge.setText(mList.get(position).charge);
        holder.plane_depTime.setText(mList.get(position).depTime);
        holder.plane_arrTime.setText(mList.get(position).arrTime);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView plane_depTime;
        public TextView plane_grade;
        public TextView plane_charge;
        public TextView plane_arrTime;

        public MyViewHolder(View view){
            super(view);

            plane_depTime = view.findViewById(R.id.tv_plane_depTime);
            plane_grade = view.findViewById(R.id.tv_plane_airline);
            plane_charge = view.findViewById(R.id.tv_plane_charge);
            plane_arrTime = view.findViewById(R.id.tv_plane_arrTime);
        }
    }
}
