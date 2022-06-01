package com.project.wastemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BOrderFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private ListView list;
    private ArrayList<buyerorders> details=new ArrayList<>();
    private  currentordersAdapter ad;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_b_order, container, false);

        list=(ListView) v.findViewById(R.id.currentorders);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        getpincode();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o=list.getItemAtPosition(i);
                buyerorders order=(buyerorders) o;
                Intent intent=new Intent(getContext(),collectOrder.class);
                String arr[]={
                      order.getAddress(),order.getCity(),order.getDate(),order.getImagepath()
                      ,order.getPhonenumber(),order.getPincode(),order.getUsername(),
                        order.getDocid(),order.getUid()
                };
                intent.putExtra("details",arr);
                startActivity(intent);
            }
        });
        return v;
    }

    public void getorders(DocumentSnapshot snapshot) {

        String Pincode=snapshot.getString("pincode");
        db.collection("Orders").whereEqualTo("Pincode", Pincode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot: task.getResult()){
                               String username=snapshot.getString("username");
                               String pincode=snapshot.getString("Pincode");
                               String city=snapshot.getString("city");
                               String number=snapshot.getString("Phone Number");
                               String imagepath=snapshot.getString("Imagepath");
                               String address=snapshot.getString("Address Line 1")+snapshot.getString("Address Line 2");
                               String date=snapshot.getString("Date");
                               String docname=snapshot.getId();
                                String uid=snapshot.getString("Uid");
                               buyerorders order=new buyerorders(username,pincode,city,imagepath,address,number,date,docname,uid);

                               details.add(order);


                            }
                            ad = new currentordersAdapter(getContext(),details);
                            list.setAdapter(ad);

                            return;
                        }
                        else{
                            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

private void getpincode(){
        db.collection("Buyer").document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       DocumentSnapshot snapshot=task.getResult();
                       getorders(snapshot);
                    }
                });
}


}