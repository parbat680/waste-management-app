package com.project.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class buyerHome extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tablayout;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);
        pager=(ViewPager) findViewById(R.id.pager2);
        tablayout=(TabLayout) findViewById(R.id.tabLayout2);
        mauth=FirebaseAuth.getInstance();

        final buyerFragementAdapter ad=new buyerFragementAdapter(getSupportFragmentManager(),buyerHome.this,tablayout.getTabCount());
        pager.setAdapter(ad);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buyer_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Logout:
                Toast.makeText(buyerHome.this, "Logout selected", Toast.LENGTH_SHORT).show();
                mauth.signOut();
                startActivity(new Intent(this,LoginActivity.class));
                this.finish();
            case R.id.exit:
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
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}