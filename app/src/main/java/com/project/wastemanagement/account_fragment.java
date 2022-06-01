package com.project.wastemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class account_fragment extends Fragment implements View.OnClickListener {

    private LinearLayout edit,order,contact,logout,repass;
    private FirebaseAuth mauth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_account_fragment, container, false);

        mauth=FirebaseAuth.getInstance();

        edit=(LinearLayout) v.findViewById(R.id.edit);
        order=(LinearLayout) v.findViewById(R.id.orders);
        contact=(LinearLayout) v.findViewById(R.id.contact);
        logout=(LinearLayout) v.findViewById(R.id.logout);
        repass=(LinearLayout) v.findViewById(R.id.repass);

        edit.setOnClickListener(this);
        order.setOnClickListener(this);
        contact.setOnClickListener(this);
        logout.setOnClickListener(this);
        repass.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.edit:
               startActivity(new Intent(getContext(),editProfile.class));
                break;
            case R.id.orders:
                startActivity(new Intent(getContext(),CurrentOrders.class));
                break;
            case R.id.contact:
                startActivity(new Intent(getContext(),ContactUs.class));
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.repass:
                startActivity(new Intent(getActivity(),ResetPass.class));
                break;
        }
    }

    private void logout() {
        mauth.signOut();
       startActivity(new Intent(getContext(),LoginActivity.class));
       getActivity().finish();


    }
}