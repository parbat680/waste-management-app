package com.project.wastemanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Article_Adapter extends ArrayAdapter<Articles> {

    private ArrayList<Articles> articles;

    public Article_Adapter(@NonNull Context context, ArrayList<Articles> articles) {
        super(context, 0,articles);
        this.articles=articles;
        articles_fragment.arr=articles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Articles article=articles.get(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.articles_xml,parent,false);

        }

        if(article!=null) {
            TextView heading = (TextView) convertView.findViewById(R.id.heading);
            TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            TextView description = (TextView) convertView.findViewById(R.id.description);


            heading.setText(article.getHeading());
            timestamp.setText(article.getTimestamp());
            description.setText(article.getDescription());
        }

        return convertView;
    }


}
