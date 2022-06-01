package com.project.wastemanagement;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class collectOrder extends AppCompatActivity {
     private TextView username,address,PhoneNumber;
     private Button collected;
     private ImageView image;
     private FirebaseFirestore db;
     private StorageReference ref;
     private FirebaseStorage storage;
     private FirebaseAuth auth;
     String arr[];
    HashMap<String,Object> details=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_order);

        db=FirebaseFirestore.getInstance();

        collected=(Button) findViewById(R.id.collected);
        username=(TextView) findViewById(R.id.username);
        address=(TextView) findViewById(R.id.address);
        PhoneNumber=(TextView) findViewById(R.id.phone);
        image=(ImageView) findViewById(R.id.image);
        auth=FirebaseAuth.getInstance();

        collected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
            }
        });
    }

    private void getInput() {
        final EditText text=new EditText(this);
        text.setHint("Enter code");



        new AlertDialog.Builder(this)
                .setTitle("Validation")
                .setView(text)
                .setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(collectOrder.this, text.getText(), Toast.LENGTH_SHORT).show();
                        PreviousOrderData(text.getText().toString());
                    }
                })
                .setNegativeButton("Back",null)

                .show();

    }

    private void PreviousOrderData (String code) {

        Log.e("new",details.get("SellerUid").toString());
        db.collection("Orders").document(details.get("SellerUid").toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       DocumentSnapshot  snapshot= task.getResult();
                        if(snapshot.getString("Code").equals(code)) {
                            executeOrder(snapshot);
                        }
                        else{
                            AlertBox box = new AlertBox(collectOrder.this, "Error", "Order Incorrect code");
                            return;

                        }
                    }


                });
    }

    private void executeOrder(DocumentSnapshot snapshot) {



        details.put("BuyerUid",auth.getCurrentUser().getUid());

        details.put("Address",snapshot.getString("Address Line 1")+" "+snapshot.getString("Address Line 2"));
        details.put("City",snapshot.getString("City"));
        details.put("Image",snapshot.getString("Imagepath"));
        details.put("Phone Number",snapshot.getString("Phone Number"));
        details.put("Date",snapshot.getString("Date"));
        details.put("Username",snapshot.getString("username"));

        String name="null";
        try {
            String Pattern="MM-dd-yyyy-HH-mm-ss";
            SimpleDateFormat df=new SimpleDateFormat(Pattern);
            String date=df.format(new Date());
            name=auth.getUid()+date;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        name.replace("-","");
        db.collection("OrderProcessed").document(name)
                .set(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                            db.collection("Orders").document(details.get("SellerUid").toString())
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                startActivity(new Intent(collectOrder.this, buyerHome.class));
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });




    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i=getIntent();
         arr =i.getStringArrayExtra("details");

        details.put("SellerUid",arr[8]);

        String uid=arr[3];
          username.setText(arr[6]);
        String address1=arr[0]+" "+arr[1]+" "+arr[5];
        address.setText(address1);
        PhoneNumber.setText(arr[4]);
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();

        storage=FirebaseStorage.getInstance();
        ref=storage.getReference();
        StorageReference path=ref.child(uid);



        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(collectOrder.this).load(uri.toString()).into(image);
                dialog.dismiss();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(collectOrder.this, "Cannot Load Image", Toast.LENGTH_SHORT).show();
                    }
                });





    }
}