package com.melashkov.dailyselfie;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Selfie {

    private final long mId;
    private final String mName;
    private final long mTime;
    private final String mImagePath;

    public Selfie(long time, String imagePath) {
        this.mId = time;
        this.mTime = time;
        this.mName = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(time));
        this.mImagePath = imagePath;
    }

    public String getName() {
        return mName;
    }

    public long getTime() {
        return mTime;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public long getId() {
        return mId;
    }
}