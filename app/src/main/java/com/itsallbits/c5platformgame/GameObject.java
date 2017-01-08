package com.itsallbits.c5platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by matt on 1/7/17.
 */

public abstract class GameObject {
    private Vector2Point5D worldLocation;
    private float width;
    private float height;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;

    public abstract void update(long fps, float gravity);

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMeter) {

        //Make a resource id from the bitmapName
        int resID = context.getResources().getIdentifier(bitmapName,"drawable",context.getPackageName());

        //Create the bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resID);

        //Scale the bitmap based on the number of pixels per meter
        //Multiply by the number of frames in the image
        //Default 1 frame

        bitmap = Bitmap.createScaledBitmap(bitmap,(int)(width * animFrameCount * pixelsPerMeter),(int)(height * pixelsPerMeter),false);

        return bitmap;
    }

    public Vector2Point5D getWorldLocation() {
        return worldLocation;
    }

    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new Vector2Point5D();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
