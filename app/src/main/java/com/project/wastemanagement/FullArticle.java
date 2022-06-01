package com.project.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class FullArticle extends AppCompatActivity {

    public TextView heading,timestamp,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);

        Intent intent=getIntent();
        String[] articles=intent.getStringArrayExtra("article");

        heading=(TextView) findViewById(R.id.heading);
        timestamp=(TextView) findViewById(R.id.timestamp);
        description=(TextView) findViewById(R.id.description);


        heading.setText(articles[0]);
        timestamp.setText(articles[1]);
        description.setText(articles[2]);
    }
}