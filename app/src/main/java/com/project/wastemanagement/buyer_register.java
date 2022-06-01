package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class buyer_register extends AppCompatActivity implements View.OnClickListener {

    private EditText BRname,BRemail,BRpass,BRconfirmpass,BRaddress1,BRaddress2,BRcity,BRpincode;
    private Button BRegister;
    private HashMap<String,Object> details;
    private FirebaseFirestore db;
    private FirebaseAuth mauth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);

        BRname=(EditText) findViewById(R.id.BRname);
        BRemail=(EditText) findViewById(R.id.BRemail);
        BRpass=(EditText) findViewById(R.id.BRpass);
        BRconfirmpass=(EditText) findViewById(R.id.BRconfirmpass);
        BRaddress1=(EditText) findViewById(R.id.BRaddress1);
        BRaddress2=(EditText) findViewById(R.id.BRaddress2);
        BRcity=(EditText) findViewById(R.id.BRcity);
        BRpincode=(EditText) findViewById(R.id.BRpincode);

        BRegister=(Button) findViewById(R.id.BRegister);
        BRegister.setOnClickListener(this);

        details=new HashMap<>();
        mauth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.BRegister) {
            boolean valid = validate();
            if (valid) {
                mauth.createUserWithEmailAndPassword(BRemail.getText().toString(), BRpass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    setData();

                                }
                            }
                        });
            }
        }
    }

    private boolean validate() {
        String name=BRname.getText().toString();
        String email=BRemail.getText().toString();
        String pass=BRpass.getText().toString();
        String confirmpass=BRconfirmpass.getText().toString();
        String address1=BRaddress1.getText().toString();
        String address2=BRaddress2.getText().toString();
        String city=BRcity.getText().toString();
        String pincode=BRpincode.getText().toString();

        if(name==null || name.isEmpty()){
            BRname.setError("Field Empty");
            return false;
        }
        if(email=="" || email.isEmpty() || (!email.contains("@"))){
            BRemail.setError("Enter Proper details");
            return false;
        }
        if(pass=="" || pass.isEmpty()){
            BRpass.setError("Field Empty");
            return false;
        }
        if(confirmpass=="" || confirmpass.isEmpty() || (!confirmpass.equals(pass))){
            BRconfirmpass.setError("Enter Proper Details");
            return false;
        } if(address1=="" || address1.isEmpty()){
            BRaddress1.setError("Field Empty");
            return false;
        }
        if(address2=="" || address2.isEmpty()){
            BRaddress2.setError("Field Empty");
            return false;
        }
        if(city=="" || city.isEmpty()){
            BRcity.setError("Field Empty");
            return false;
        }
        if(pincode=="" || pincode.isEmpty()){
            BRpincode.setError("Field Empty");
            return false;
        }
        return true;
    }

    private void setData() {
        details.put("buyername",BRname.getText().toString());
        details.put("email",BRemail.getText().toString());
        details.put("Address Line 1",BRaddress1.getText().toString());
        details.put("Address Line 2",BRaddress2.getText().toString());
        details.put("city",BRcity.getText().toString());
        details.put("pincode",BRpincode.getText().toString());
        user= mauth.getCurrentUser();

        DocumentReference ref=db.collection("Buyer").document(user.getUid());

        ref.set(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(buyer_register.this, "R Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}