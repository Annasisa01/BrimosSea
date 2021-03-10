package com.example.brimossea;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;

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

    public LevelManager(Context context,
                        int pixelsPerMetre, int screenWidth,
                        InputController ic,
                        String level,
                        float px, float py) {
        this.level = level;
        switch (level) {
            case "LevelCave":
                levelData = new LevelCave();
                break;
            case "LevelTwo":
                levelData = new LevelTwo();
                break;
            case "LevelThree":
                levelData = new LevelThree();
                break;
        }

        // To hold all our GameObjects
        gameObjects = new ArrayList<>();
        // To hold 1 of every Bitmap
        bitmapsArray = new Bitmap[25];
        // Load all the GameObjects and Bitmaps
        loadMapData(context, pixelsPerMetre, px, py);
        // Ready to play
//        playing = true;
    }
    public boolean isPlaying(){
        return playing;
    }

    private void loadMapData(Context context, int pixelsPerMetre, float px, float py) {
        char c;
        //Keep track of where we load our game objects
        int currentIndex = -1;
        // how wide and high is the map? Viewport needs to know
        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for (int i = 0; i < levelData.tiles.size(); i++) {
            for (int j = 0; j <
                    levelData.tiles.get(i).length(); j++) {
                c = levelData.tiles.get(i).charAt(j);
                // Don't want to load the empty spaces
                if (c != '.'){
                    currentIndex++;
                    switch (c) {
                        case '1':
                            // Add grass to the gameObjects
                            gameObjects.add(new Worm(j, i, c));
                            break;
                        case 'p':
                            // Add a player to the gameObjects
                            gameObjects.add(new Player
                                    (context, px, py,
                                            pixelsPerMetre));
                            // We want the index of the player
                            playerIndex = currentIndex;
                            // We want a reference to the player
                            player = (Player)
                                    gameObjects.get(playerIndex);
                            break;
                        case 'e':
                            // Add a coin to the gameObjects
                            gameObjects.add(new ExtraLife(j, i, c));
                            break;
                        case 'b':
                            // Add a shield upgrade to the gameObjects
                            gameObjects.add(new Shield(j, i, c));
                            break;
                        case 's':
                            Shark shark = new Shark(j, i, c);
                            switch (level) {
                                case "LevelTwo":
                                    shark.setxVelocity(-2);
                                    break;
                                case "LevelThree":
                                    Random rand = new Random();
                                    int ranInt = rand.nextInt(3)-3;
                                    System.out.println("Randint is "+ ranInt);
                                    shark.setxVelocity(ranInt);
                                    shark.setyVelocity(rand.nextInt(4) - 2);
                                    break;
                                default:
                                    shark.setxVelocity(0);
                                    break;
                            }
                            //Add a shark enemy to the gameObjects
                            gameObjects.add(shark);
                            break;
                        case 'o':
                            gameObjects.add(new Ocean(j, i, c,0));
                            break;

                        case 'a':
                            gameObjects.add(new Ocean(j, i, c,1));
                            break;
                    }
                    // If the bitmap isn't prepared yet
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        // Prepare it now and put it in the bitmapsArrayList
                        bitmapsArray[getBitmapIndex(c)] = gameObjects.get(currentIndex).
                                prepareBitmap(context,
                                                gameObjects.get(currentIndex).
                                                        getBitmapName(),
                                                pixelsPerMetre);
                    }// End if
                }// End if (c != '.'){
            }// End for j
        }// End for i
    }// End loadMapData()


    // Each index Corresponds to a bitmap
    public Bitmap getBitmap(char blockType) {
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
            case 'e':
                index = 3;
                break;
            case 'b':
                index = 4;
                break;
            case 's':
                index = 5;
                break;
            case 'o':
                index = 6;
                break;
            case 'a':
                index = 7;
                break;
            default:
                index = 0;
                break;
        }// End switch
        return bitmapsArray[index];
    }// End getBitmap

    // This method allows each GameObject which 'knows'
    // its type to get the correct index to its Bitmap
    // in the Bitmap array.
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
            case 'e':
                index = 3;
                break;
            case 'b':
                index = 4;
                break;
            case 's':
                index = 5;
                break;
            case 'o':
                index = 6;
                break;
            case 'a':
                index = 7;
                break;
            default:
                index = 0;
                break;
        }// End switch
        return index;
    }// End getBitmapIndex()

    public void switchPlayingStatus() {
        playing = !playing;
    }

    public String getLevel() {
        return level;
    }
}
