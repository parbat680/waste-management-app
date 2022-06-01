package com.project.wastemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class flipAdapter extends BaseAdapter {
    private int[] img;
    private Context context;
    private LayoutInflater inflate;

    flipAdapter(Context context,int[] img){
        this.img=img;
        this.context=context;
        inflate=LayoutInflater.from(context);

    }



    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view=inflate.inflate(R.layout.img_flipper,viewGroup,false);

        ImageView img1=view.findViewById(R.id.flipperimg);

        img1.setImageResource(img[i]);


        return view;
    }
}
