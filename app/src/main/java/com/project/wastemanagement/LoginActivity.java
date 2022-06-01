package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private EditText L_mail,L_pass;
    private Button Login;
    TextView registerPage,resetpass,Buyeref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        L_mail=(EditText) findViewById(R.id.LEmail);
        L_pass=(EditText) findViewById(R.id.LPass);
        Login=(Button) findViewById(R.id.Login);
        registerPage=(TextView) findViewById(R.id.login_page);
        resetpass=(TextView) findViewById(R.id.resetpass);
        Buyeref=(TextView) findViewById(R.id.Buyeref);


        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        Login.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View v) {
                String email=L_mail.getText().toString();
                String password=L_pass.getText().toString();


                boolean verified =true;

                if(email==null || email.isEmpty() || email.contains(" ")){

                    verified=false;
                }
                if(password==null || password.isEmpty() || password.contains(" ")){

                    verified=false;
                }


                boolean finalVerified = verified;
                if(finalVerified){
                    Login(email,password);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Registeration Unsucessfull", Toast.LENGTH_SHORT).show();

                }
            }
        });


        registerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register_activity.class));
            }
        });

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPass.class));
            }
        });

        Buyeref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,buyer_register.class));
            }
        });

    }

    public void Login(String email,String password){
        ProgressDialog diaplog=new ProgressDialog(LoginActivity.this);
        diaplog.setTitle("Loading");
        diaplog.show();
    mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user= mAuth.getCurrentUser();
                            db.collection("User").document(user.getUid()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.getResult().getData()!=null){
                                                diaplog.dismiss();
                                               startActivity(new Intent(LoginActivity.this,Home_Activity.class));
                                               finish();
                                            }
                                            else if(task.getResult().getData()==null){
                                               startActivity(new Intent(LoginActivity.this,buyerHome.class));
                                                diaplog.dismiss();
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                        else {
                            Toast.makeText(LoginActivity.this, "Login Unseccessfull!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        if(user!=null ){
            ProgressDialog diaplog=new ProgressDialog(LoginActivity.this);
            diaplog.setTitle("Loading");
            diaplog.show();

          db.collection("User").document(user.getUid()).get()
          .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if(task.getResult().getData()!=null){
                      diaplog.dismiss();
                      reload();
                  }
                  else if(task.getResult().getData()==null){
                      startActivity(new Intent(LoginActivity.this,buyerHome.class));
                      diaplog.dismiss();
                      finish();
                  }
                  else{
                      Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                      diaplog.dismiss();
                  }
              }
          });
        }

    }
    public void reload(){

        Intent i=new Intent(LoginActivity.this,Home_Activity.class);

        startActivity(i);
        finish();
    }

}