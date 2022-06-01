package com.project.wastemanagement;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.DocumentTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


public class sell_fragment extends Fragment implements View.OnClickListener {

    private  FirebaseFirestore db;
    ProgressDialog dialog;
    private Button sell;
    private FirebaseAuth mauth;
    private FirebaseUser user;
    private ArrayList<String> address;
    private LinearLayout UploadLayout;
    private static ImageView sellImage;
    private EditText Adress1,Address2,city,pincode,Number,Name;
    private Uri imageuri;
    private HashMap<String,Object> details;
    private Random rand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_sell_fragment, container, false);
        // Inflate the layout for this fragment



        mauth=FirebaseAuth.getInstance();
        user= mauth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        address=new ArrayList<>();
        UploadLayout = view.findViewById(R.id.uploadLyout);
        sellImage= view.findViewById(R.id.sellimage);
        Adress1=(EditText) view.findViewById(R.id.address1);
        Address2=(EditText) view.findViewById(R.id.address2);
        city=(EditText) view.findViewById(R.id.city);
        pincode=(EditText) view.findViewById(R.id.pincode);
        Number=(EditText) view.findViewById(R.id.Number);
        Name=(EditText) view.findViewById(R.id.Name);
        rand=new Random();

        sell=(Button) view.findViewById(R.id.sell);
        sell.setOnClickListener(this);


        UploadLayout.setOnClickListener(this);

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        if(details==null) {
            DocumentReference dr = db.collection("User").document(user.getUid());
            dr.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                DocumentSnapshot snapshot = task.getResult();
                                setdata(snapshot);
                            } else {
                                new AlertBox(getActivity(), "Error", "Make sure you have a Internet Connection");
                            }
                        }
                    });
        }


    }

public void setdata(DocumentSnapshot snapshot){
    String Address1=snapshot.getString("Address Line 1");
    String Address2s=snapshot.getString("Address Line 2");
    String citys=snapshot.getString("City");
    String pincodes=snapshot.getString("Pincode");
    String number=snapshot.getString("Phone Number");
    String name=snapshot.getString("username");

    Adress1.setText(Address1);
    Address2.setText(Address2s);
    city.setText(citys);
    pincode.setText(pincodes);
    Number.setText(number);
    Name.setText(name);

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && data!=null && data.getData()!=null ){

                imageuri=data.getData();
            sellImage.setImageURI(imageuri);
            Number.setText(details.get("Phone Number").toString());
            Adress1.setText(details.get("Address Line 1").toString());
            Address2.setText(details.get("Address Line 2").toString());
            city.setText(details.get("City").toString());
            pincode.setText(details.get("Pincode").toString());

        }
    }





    @Override
    public void onClick(View view) {
        if(view.getId()==UploadLayout.getId()){


             details=new HashMap<>();
            details.put("Phone Number",Number.getText().toString());
            details.put("Address Line 1",Adress1.getText().toString());
            details.put("Address Line 2",Address2.getText().toString());
            details.put("City",city.getText().toString());
            details.put("Pincode",pincode.getText().toString());


                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,100);



        }

        else if(view.getId()==sell.getId()){
            sellwaste();
        }
    }

    private void sellwaste() {

         dialog=new ProgressDialog(getActivity());
        dialog.setTitle("Loading");
        dialog.show();

        int code=rand.nextInt(1000)+900*rand.nextInt(10);
       try {
           String Pattern="MM/dd/yyyy";
           SimpleDateFormat df=new SimpleDateFormat(Pattern);
           String date=df.format(new Date());
           details.put("Date",date);
       }
       catch(Exception e){
           e.printStackTrace();
        }


        details.put("Code",Integer.toString(code));
       details.put("Uid",user.getUid());

        DocumentReference dr=db.collection("User").document(user.getUid());


        dr.update(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), details.get("Pincode").toString(), Toast.LENGTH_SHORT).show();
                            setOrder(details);
                        }
                        else{
                            new AlertBox(getActivity(),"Error","Cannot Place Order Please try Again");
                            dialog.dismiss();
                        }
                    }
                });

    }

    private void setOrder(HashMap<String,Object> details) {
        DocumentReference dr=db.collection("Orders").document(user.getUid());
        String filename= user.getUid();
        StorageReference srf= FirebaseStorage.getInstance().getReference("productImages/"+filename);


        details.put("username",Name.getText().toString());
        details.put("Imagepath","productImages/"+user.getUid());


        dr.set(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                           srf.putFile(imageuri)
                                   .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                           if(task.isSuccessful()){
                                               dialog.dismiss();
                                               startActivity(new Intent(getActivity(),soldsuccess.class));

                                               getActivity().finish();
                                           }
                                           else{
                                               new AlertBox(getActivity(),"Error","Cannot Place Order Please try Again");
                                               dialog.dismiss();

                                           }
                                       }
                                   });

                        }
                        else{
                            new AlertBox(getActivity(),"Error","Cannot Place Order Please try Again");
                        }
                    }
                });


    }

}