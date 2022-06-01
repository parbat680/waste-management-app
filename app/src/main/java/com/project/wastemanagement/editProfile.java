package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class editProfile extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db;
    FirebaseAuth mauth;
    FirebaseUser user;
    EditText Adress1,Address2,city,pincode,Name,email,Number;
    Button save,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        db=FirebaseFirestore.getInstance();
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();

        Adress1=findViewById(R.id.address1);
        Address2=findViewById(R.id.address2);
        city=findViewById(R.id.city);
        pincode=findViewById(R.id.pincode);
        Name=findViewById(R.id.Name);
        email=findViewById(R.id.email);
        Number=findViewById(R.id.Number);

        save=(Button) findViewById(R.id.save);
        cancel=(Button) findViewById(R.id.cancel);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        getdata();
    }

    private void getdata() {
        DocumentReference dr=db.collection("User").document(user.getUid());
        dr.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){

                            DocumentSnapshot snapshot=task.getResult();
                            setdata(snapshot);
                        }
                        else{
                            new AlertBox(editProfile.this,"Error","Make sure you have a Internet Connection");
                        }
                    }
                });
    }

    private void setdata(DocumentSnapshot snapshot) {

        String Address1=snapshot.getString("Address Line 1");
        String Address2s=snapshot.getString("Address Line 2");
        String citys=snapshot.getString("City");
        String pincodes=snapshot.getString("Pincode");
        String number=snapshot.getString("Phone Number");
        String name=snapshot.getString("username");
        String emails=snapshot.getString("email");

        Adress1.setText(Address1);
        Address2.setText(Address2s);
        city.setText(citys);
        pincode.setText(pincodes);
        Number.setText(number);
        email.setText(emails);
        Name.setText(name);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.save){
            editdata();
        }
        else if(view.getId()==R.id.cancel){
            finish();
        }
    }

    private void editdata() {

        HashMap<String,Object> details=new HashMap<>();
        details.put("Phone Number",Number.getText().toString());
        details.put("Address Line 1",Adress1.getText().toString());
        details.put("Address Line 2",Address2.getText().toString());
        details.put("City",city.getText().toString());
        details.put("Pincode",pincode.getText().toString());
        details.put("email",email.getText().toString());
        details.put("username",Name.getText().toString());


        db.collection("User").document(user.getUid())
                .update(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finish();
                        }
                        else{
                            new AlertBox(editProfile.this,"Error",task.getException().toString());
                        }
                    }
                });

    }
}