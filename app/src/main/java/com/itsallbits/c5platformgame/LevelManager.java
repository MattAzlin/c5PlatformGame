package com.itsallbits.c5platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by matt on 1/7/17.
 */

public class LevelManager {
    private String level;
    int mapWidth;
    int mapHeight;

    Player player;
    int playerIndex;

    private boolean playing;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;

    ArrayList<Rect> currentButtons;
    Bitmap[] bitmapsArray;

    public LevelManager(Context context, int pixelsPerMeter, int screenWidth, InputController ic, String level, float px, float py) {
        this.level = level;

        switch (level) {
            case "LevelCave":
                levelData = new LevelCave();
                break;
        }

        //To hold all of our GameObjects
        gameObjects = new ArrayList<>();

        //to hold 1 of every Bitmap
        bitmapsArray = new Bitmap[25];

        //load all the GameObjects and Bitmaps
        loadMapData(context, pixelsPerMeter,px,py);

        //Ready to play
        playing = true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public Bitmap getBitmap(char blockType) {
        int index;
        switch(blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            default:
                index = 0;
                break;
        }
        return bitmapsArray[index];
    }

    //This method allows each GameObject which 'knows' its type to get the correct index to its Bitmap in the Bitmap array
    public int getBitmapIndex(char blockType) {
        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            default:
                index = 0;
                break;
        }
        return index;
    }

    private void loadMapData(Context context, int pixelsPerMeter, float px, float py) {
        char c;
        //keep track of where we load our game objects
        int currentIndex = -1;

        //how wide and high is the map?  Viewport needs to know

        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for(int i = 0; i < levelData.tiles.size(); i++) {
            for(int j = 0; j < levelData.tiles.get(i).length(); j++) {
                c = levelData.tiles.get(i).charAt(j);

                //skip empty spaces
                if(c != '.') {
                    currentIndex++;
                    switch (c) {
                        case '1':
                            //add grass to gameObjects
                            gameObjects.add(new Grass(j,i,c));
                            break;
                        case 'p':
                            //add player to gameObjects
                            gameObjects.add(new Player(context, px, py, pixelsPerMeter));

                            //we want the index of the player
                            playerIndex = currentIndex;
                            //we want a reference to the player
                            player = (Player) gameObjects.get(playerIndex);

                            break;
                    }

                    //If the bitmap isn't prepared yet
                    if(bitmapsArray[getBitmapIndex(c)] == null) {
                        //prepare it now and put it in the bitmapsArrayList
                        bitmapsArray[getBitmapIndex(c)] = gameObjects.get(currentIndex).prepareBitmap(context,gameObjects.get(currentIndex).getBitmapName(), pixelsPerMeter);
                    }
                }
            }
        }
    }
}
