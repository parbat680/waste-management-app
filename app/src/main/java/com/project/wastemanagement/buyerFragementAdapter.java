package com.project.wastemanagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class buyerFragementAdapter extends FragmentPagerAdapter {
    int totaltabs;
    Context context;

    public buyerFragementAdapter(FragmentManager fm, Context context, int totaltabs){
        super(fm);
        this.totaltabs=totaltabs;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                BOrderFragment order=new BOrderFragment();
                return order;

            case 1:
                BPastOrders past=new BPastOrders();
                return past;

        }

        return null;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
