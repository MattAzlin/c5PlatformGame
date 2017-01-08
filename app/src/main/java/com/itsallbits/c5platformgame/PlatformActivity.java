package com.itsallbits.c5platformgame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class PlatformActivity extends Activity {
    //Our object to handle the view
    private PlatformView platformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //Load the resolution into a Point object
        Point resolution = new Point();
        display.getSize(resolution);

        //And finally set the view for our game
        //Also passing in the screen resolution
        platformView = new PlatformView(this, resolution.x,resolution.y);

        //Make our platformView the view for the activity
        setContentView(platformView);

    }
    //If the activity is paused mae sure to pause our thread
    @Override
    protected void onPause() {
        super.onPause();
        platformView.pause();
    }

    //if the activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
        super.onResume();
        platformView.resume();
    }

}