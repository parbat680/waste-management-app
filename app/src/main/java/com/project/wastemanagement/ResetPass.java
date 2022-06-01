package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {
    private static FirebaseAuth mAuth;
    private static EditText Resetemail;
    private static Button Resetbutton;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        mAuth=FirebaseAuth.getInstance();
        Resetemail=(EditText) findViewById(R.id.resetE);
        Resetbutton=(Button) findViewById(R.id.resetP);
        back=(TextView) findViewById(R.id.back);

        Resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Resetemail.getText().toString().trim();
                resetpassword(email);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void resetpassword(String email){
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                            else{
                                Toast.makeText(ResetPass.this, "Operation Unsuccessfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

}