package com.project.wastemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class currentordersAdapter extends ArrayAdapter<buyerorders> {

    ArrayList<buyerorders> details;
    Context context;

    public currentordersAdapter(@NonNull Context context, ArrayList<buyerorders> details) {
        super(context, 0,details);
        this.details=details;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        buyerorders orders=details.get(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.orderlistad,parent,false);

        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView date = (TextView) convertView.findViewById(R.id.dateOrdered);
        TextView Address = (TextView) convertView.findViewById(R.id.Address);

        username.setText(orders.getUsername());
        date.setText("Order Placed On"+orders.getDate());
        Address.setText(orders.getAddress());


        return convertView;
    }
}
