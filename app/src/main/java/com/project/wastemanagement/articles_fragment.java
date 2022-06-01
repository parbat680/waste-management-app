package com.project.wastemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class articles_fragment extends Fragment  {

    public static ArrayList<Articles> arr=new ArrayList<>();
    private  Article_Adapter ad;
    private FirebaseFirestore db;
    private ListView list;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.fragment_articles_fragment, container, false);

        db=FirebaseFirestore.getInstance();
        list=(ListView) view.findViewById(R.id.article_list);
        getArticles();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object o=list.getItemAtPosition(position);
                Articles art=(Articles) o;
                Intent i=new Intent(getContext(),FullArticle.class);
               String arr[]=new String[3];
               arr[0]=art.getHeading();
               arr[1]=art.getTimestamp();
               arr[2]=art.getDescription();
               i.putExtra("article",arr);
               startActivity(i);

            }
        });
        return view;

    }



    public void getArticles() {
        db.collection("Articles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Articles> art=new ArrayList<>();

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot: task.getResult()){
                              String heading=snapshot.getString("heading");
                              Timestamp timestamp=snapshot.getTimestamp("date");
                              String date=timestamp.toDate().toString().substring(0,10);
                              String description=snapshot.getString("description");
                              Articles articles=new Articles(heading,date,description);
                              art.add(articles);

                            }
                            ad = new Article_Adapter(getActivity(), art);
                            list.setAdapter(ad);

                            return;
                        }
                        else{
                            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }



}
