package com.project.wastemanagement;

public class buyerorders {
    private String username,pincode,city,imagepath,address,phonenumber,date,docid,uid;

    public String getDocid() {
        return docid;
    }

    public String getUid() {
        return uid;
    }

    public buyerorders(String username, String pincode, String city, String imagepath, String address, String phonenumber, String date, String docid, String uid) {
        this.username = username;
        this.pincode = pincode;
        this.city = city;
        this.imagepath = imagepath;
        this.address = address;
        this.phonenumber = phonenumber;
        this.date=date;
        this.docid=docid;
        this.uid=uid;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getAddress() {
        return address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
