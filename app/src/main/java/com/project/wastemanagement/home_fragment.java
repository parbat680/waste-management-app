package com.project.wastemanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.DiskLruCache;


public class home_fragment extends Fragment {
    private FirebaseFirestore db;
    private TextView paperName,paperPrice,cartoonName,CartoonPrice,plasticName,plasticPrice,eWasteName,eWastePrice,metalName,metalPrice;
    private AdapterViewFlipper flipper;
    private int[] img={R.drawable.recyclewaste,R.drawable.getcredit};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_fragment, container, false);

        db=FirebaseFirestore.getInstance();

        flipAdapter ad=new flipAdapter(getActivity(),img);
        flipper=view.findViewById(R.id.imgflipper);
        flipper.setAdapter(ad);
        flipper.setAutoStart(true);

        //for paperWaste
        paperName=(TextView) view.findViewById(R.id.paperName);
        paperPrice=(TextView) view.findViewById(R.id.paperprice);
        getNamePrice("paper waste");

        //For Cartoon
        cartoonName=(TextView) view.findViewById(R.id.cartoonName);
        CartoonPrice=(TextView) view.findViewById(R.id.CartoonPrice);
        getNamePrice("cartoon waste");

        //for plastic
        plasticName=(TextView) view.findViewById(R.id.plasticName);
        plasticPrice=(TextView) view.findViewById(R.id.plasticPrice);
        getNamePrice("plastic waste");

        //for ewaste
        eWasteName=(TextView) view.findViewById(R.id.EWasteName);
        eWastePrice=(TextView) view.findViewById(R.id.EWastePrice);
        getNamePrice("e waste");

        //for metals
        metalName=(TextView) view.findViewById(R.id.MetalName);
        metalPrice=(TextView) view.findViewById(R.id.MetalPrice);
        getNamePrice("metal waste");




        return view;
    }

    private void getNamePrice(String s){

        DocumentReference dr=db.collection("RateList").document(s);

        dr.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot snapshot =task.getResult();
                            setdata(snapshot,s);
                        }
                        else{
                            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    private void setdata(DocumentSnapshot snapshot,String s) {
        String s1[]=new String[2];
        s1[0]=snapshot.getString("Name");
        s1[1]=snapshot.getString("price");

        switch (s){

            case "paper waste":
                paperName.setText(s1[0]);
                paperPrice.setText(s1[1]);
                break;
            case "cartoon waste":
                cartoonName.setText(s1[0]);
                CartoonPrice.setText(s1[1]);
                break;
            case "plastic waste":
                plasticName.setText(s1[0]);
                plasticPrice.setText(s1[1]);
                break;
            case "e waste":
                eWasteName.setText(s1[0]);
                eWastePrice.setText(s1[1]);
                break;
            case "metal waste":
                metalName.setText(s1[0]);
                metalPrice.setText(s1[1]);
                break;


        }

    }


}