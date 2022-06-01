package com.project.wastemanagement;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class fragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTabs;

    public fragmentAdapter(@NonNull FragmentManager fm, Context context, int totaltabs) {
        super(fm);
        this.totalTabs=totaltabs;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                home_fragment home=new home_fragment();
                return home;
            case 1:
                articles_fragment article=new articles_fragment();
                return article;
            case 2:
                sell_fragment sell=new sell_fragment();
                return sell;
            case 3:
                account_fragment account=new account_fragment();
                return account;
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
