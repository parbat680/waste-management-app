package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Objects;

public class Home_Activity extends AppCompatActivity {
private TabLayout tabs;
private ViewPager pager;

    private static final int CONTENT_VIEW_ID = 10101010;
    private TextView uidtext,username;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tabs=(TabLayout) findViewById(R.id.tabLayout);
        pager=(ViewPager) findViewById(R.id.viewPager);




        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        user= auth.getCurrentUser();
        username=(TextView) findViewById(R.id.username);
       getuser();



        final fragmentAdapter adapter=new fragmentAdapter(getSupportFragmentManager(),this,tabs.getTabCount());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }


    public  void getuser(){

        DocumentReference dr=db.collection("User").document(user.getUid().toString());
        // Source can be CACHE, SERVER, or DEFAULT.


        dr.get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot snapshot=task.getResult();
                        String username2 =snapshot.getString("username");
                        username.setText(username2);

                        }

                }
            });

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Do you want to Exit?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            })
            .setNegativeButton("No", null)
                .show();
    }
}