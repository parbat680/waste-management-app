package com.project.wastemanagement;

public class userDetails {
    String name,number,address,imagepath,city,pincode;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    userDetails(String details[]){
        this.name=details[0];
        this.number=details[1];
        this.address=details[2];
        this.imagepath=details[3];
        this.city=details[4];
        this.pincode=details[5];

    }
}
