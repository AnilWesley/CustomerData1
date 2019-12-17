package com.example.customerdata1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerdata1.R;
import com.example.customerdata1.model.Product;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameHolder>
{

    private Context context;
    NameAdapter adapter;
    private List<Product> list;
    private OnItemClickListener mItemClickListener;

    public NameAdapter(Context context, List<Product> list,OnItemClickListener mItemClickListener) {
        this.context = context;
        this.list = list;
        this.mItemClickListener = mItemClickListener;
        this.adapter = this; //This is an important line, you need this line to keep track the adapter variable

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

        final Product nameDetails1 = list.get(position);
        holder.textView.setText("Name : "+ nameDetails1.getName());
        holder.textView1.setText("Quantity : "+ nameDetails1.getQuantity());
        holder.textView2.setText("Price : "+ nameDetails1.getPrice());
        holder.textView3.setText("Total Price : "+ nameDetails1.getTotalPrice());
        holder.checkbox.setChecked(nameDetails1.isSelected());

        holder.checkbox.setTag(list.get(position));


        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Product contact = (Product) cb.getTag();

                contact.setSelected(cb.isChecked());
                list.get(position).setSelected(cb.isChecked());

               /* Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();*/
            }
        });
        holder.linear2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemClickListener.onItemClick(v,position);
                return false;
            }
        });

    }

    class NameHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        CheckBox checkbox;
        LinearLayout linear2;

        public NameHolder(View itemView) {

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_name);
            textView1 = (TextView) itemView.findViewById(R.id.text_quantity);
            textView2 = (TextView) itemView.findViewById(R.id.text_price);
            textView3 = (TextView) itemView.findViewById(R.id.text_total_price);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            linear2 = (LinearLayout) itemView.findViewById(R.id.linear2);

        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

