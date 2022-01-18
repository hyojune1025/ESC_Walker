package com.example.esc_walker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_train extends RecyclerView.Adapter<MyAdapter_train.MyViewHolder> {
    private ArrayList<Train> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    public MyAdapter_train(Context context, ArrayList<Train> trains){
        this.mList = trains;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_list_train,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.train_name.setText(mList.get(position).tName);
        holder.train_charge.setText(mList.get(position).charge);
        holder.train_depTime.setText(mList.get(position).depTime);
        holder.train_arrTime.setText(mList.get(position).arrTime);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView train_depTime;
        public TextView train_name;
        public TextView train_charge;
        public TextView train_arrTime;

        public MyViewHolder(View view){
            super(view);

            train_depTime = view.findViewById(R.id.tv_train_depTime);
            train_name = view.findViewById(R.id.tv_train_name);
            train_charge = view.findViewById(R.id.tv_train_charge);
            train_arrTime = view.findViewById(R.id.tv_train_arrTime);
        }
    }
}
