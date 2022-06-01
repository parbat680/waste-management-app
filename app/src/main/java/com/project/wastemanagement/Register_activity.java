package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class Register_activity extends AppCompatActivity {

    private FirebaseAuth user;
    private EditText user_name,R_mail,R_pass;

    private Button register ;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_name=(EditText) findViewById(R.id.username);
        R_mail=(EditText) findViewById(R.id.LEmail);
        R_pass=(EditText) findViewById(R.id.LPass);


        //firebase initializations
        user=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=R_mail.getText().toString();
                String password=R_pass.getText().toString();
                String userName=user_name.getText().toString();
                boolean verified =true;

                if(email==null || email.isEmpty() || email.contains(" ")){

                    verified=false;
                }
                if(password==null || password.isEmpty() || password.contains(" ")){

                    verified=false;
                }
                if(userName==null || userName.isEmpty()){

                    verified=false;
                }

                boolean finalVerified = verified;
                if(finalVerified){
                        register(email,password,userName);
                }
                else{
                    Toast.makeText(Register_activity.this, "Registeration here unsuccess", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }


    public void Update(FirebaseUser usr){


        Intent i= new Intent(Register_activity.this,Home_Activity.class);

        startActivity(i);
        finish();

    }
    public void register(String email,String password,String userName){
        ProgressDialog dialog=new ProgressDialog(Register_activity.this);
        dialog.setTitle("Loading");
        dialog.show();
        user.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Register_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser usr= user.getCurrentUser();
                            HashMap<String,Object> details=new HashMap<>();

                            details.put("email",email);
                            details.put("username",userName);
                            details.put("Address Line 1","");
                            details.put("Address Line 2","");
                            details.put("City","");
                            details.put("Pincode","");
                            details.put("Phone Number","");
                            String userUId=usr.getUid();
                            DocumentReference dr=db.collection("User").document(userUId);
                            dr.set(details)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Register_activity.this, "Data Entered", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Update(usr);
                                        }
                                    });



                        } else {
                            Toast.makeText(Register_activity.this, "Registeration Unsucessfull", Toast.LENGTH_SHORT).show();
                            user_name.setText("");
                            R_mail.setText("");
                            R_pass.setText("");
                            dialog.dismiss();
                        }

                    }
                });
    }
}