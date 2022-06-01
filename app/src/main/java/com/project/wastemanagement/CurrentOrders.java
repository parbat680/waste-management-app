package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CurrentOrders extends AppCompatActivity {
    private TextView username,address,PhoneNumber,code;
    private ImageView image;
    private FirebaseFirestore db;
    private StorageReference ref;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        username=(TextView) findViewById(R.id.username);
        address=(TextView) findViewById(R.id.address);
        PhoneNumber=(TextView) findViewById(R.id.phone);
        code=(TextView) findViewById(R.id.code);
        image=(ImageView) findViewById(R.id.image);



    }

    @Override
    protected void onStart() {
        super.onStart();

        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();

        db.collection("Orders").document(auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String images=task.getResult().getString("Imagepath");
                            storage= FirebaseStorage.getInstance();
                            ref=storage.getReference();
                            StorageReference path=ref.child(images);
                            path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(CurrentOrders.this).load(uri.toString()).into(image);
                                    dialog.dismiss();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CurrentOrders.this, "Cannot Load Image", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                            DocumentSnapshot snapshot= task.getResult();
                            username.setText(snapshot.getString("username"));
                            address.setText(snapshot.getString("Address Line 1")+snapshot.getString("Address Line 2"));
                            PhoneNumber.setText(snapshot.getString("Phone Number"));
                            code.setText(snapshot.getString("Code"));
                        }
                    }
                });
    }
}