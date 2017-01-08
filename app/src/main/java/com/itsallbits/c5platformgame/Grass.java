package com.itsallbits.c5platformgame;

/**
 * Created by matt on 1/7/17.
 */

public class Grass extends GameObject {
    Grass(float worldStartX,float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); //1 meter tall
        setWidth(WIDTH);  //1 meter wide

        setType(type);

        //Choose a bitmap
        setBitmapName("turf");

        //where does the tile start
        //X and Y locations from constructor parameters
        setWorldLocation(worldStartX,worldStartY,0);
    }

    public void update(long fps, float gravity) {

    }
}
