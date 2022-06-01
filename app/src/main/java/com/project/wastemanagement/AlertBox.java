package com.project.wastemanagement;

import android.app.AlertDialog;
import android.content.Context;

public class AlertBox {
    private Context context;
    private String tag,message;

    public AlertBox(Context context,String tag,String message){

        this.context=context;
        this.tag=tag;
        this.message=message;

        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setTitle(tag);
        alert.setMessage(message);
        alert.setPositiveButton("Ok",null);
        alert.create().show();
    }

}
