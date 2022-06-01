package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class collectedOrdersPast extends AppCompatActivity {
    private TextView username,address,PhoneNumber,code;
    private ImageView image;
    private FirebaseFirestore db;
    private StorageReference ref;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    String arr[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected_orders_past);

        db=FirebaseFirestore.getInstance();


        username=(TextView) findViewById(R.id.username);
        address=(TextView) findViewById(R.id.address);
        PhoneNumber=(TextView) findViewById(R.id.phone);
        image=(ImageView) findViewById(R.id.image);
        auth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i=getIntent();
        arr =i.getStringArrayExtra("details");

        String uid=arr[3];
        username.setText(arr[6]);
        String address1=arr[0]+" "+arr[1]+" "+arr[5];
        address.setText(address1);
        PhoneNumber.setText(arr[4]);
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();

        storage= FirebaseStorage.getInstance();
        ref=storage.getReference();
        StorageReference path=ref.child(uid);



        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(collectedOrdersPast.this).load(uri.toString()).into(image);
                dialog.dismiss();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(collectedOrdersPast.this, "Cannot Load Image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
