package com.example.customerdata1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customerdata1.R;
import com.example.customerdata1.model.Note;

import java.util.List;

public class NameAdapter1 extends RecyclerView.Adapter<NameAdapter1.NameHolder>
{

    private Context context;
    NameAdapter1 adapter;
    private List<Note> list;
    private OnItemClickListener mItemClickListener;

    public NameAdapter1(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
        this.adapter = this; //This is an important line, you need this line to keep track the adapter variable

    }

    public void SetOnItemClickListener(
            final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.customlayout1,null);
        return new NameHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NameHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Note nameDetails1 = list.get(position);
        holder.textView.setText("Name : "+ nameDetails1.getProduct_name());
        holder.textView1.setText("Quantity : "+ nameDetails1.getProduct_quantity());
        holder.textView2.setText("Price : "+ nameDetails1.getProduct_price());
        holder.textView3.setText("Total Price : "+ nameDetails1.getProduct_total_price());

    }

    class NameHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public NameHolder(View itemView) {

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_name);
            textView1 = (TextView) itemView.findViewById(R.id.text_quantity);
            textView2 = (TextView) itemView.findViewById(R.id.text_price);
            textView3 = (TextView) itemView.findViewById(R.id.text_total_price);

        }

    }
}

