package com.project.wastemanagement;

import android.net.Uri;

public class Articles {
    private String heading,timestamp,description;


    public Articles(String heading, String timestamp,String description) {
        this.heading = heading;
        this.timestamp = timestamp;
        this.description=description;

    }

    public String getHeading() {
        return heading;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public String getDescription() {
        return description;
    }


}
