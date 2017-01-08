package com.itsallbits.c5platformgame;

import android.content.Context;

/**
 * Created by matt on 1/7/17.
 */

public class Player extends GameObject {
    Player(Context context, float worldStartX, float worldStartY,int pixelsPerMeter) {
        final float HEIGHT = 2;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType('p');
        //Choose a bitmap
        //This is a sprite sheet with multiple frames of animation.  So it will look silly until we animate it

        setBitmapName("player");

        //X and Y locations from constructor parameters
        setWorldLocation(worldStartX,worldStartY,0);
    }

    public void update(long fps,float gravity) {

    }
}
