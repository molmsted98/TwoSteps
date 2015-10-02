package com.tsuruta.GameObjects;

import com.tsuruta.TSHelpers.AssetLoader;

/**
 * Created by Michael on 8/28/2015.
 */
public class Player
{
    private int health;
    private int xLoc;
    private int yLoc;

    //Initialize variables
    public Player(int startX, int startY, int health)
    {
        this.health = health;
        xLoc = startX;
        yLoc = startY;
    }

    public void updateReady(float delta)
    {

    }

    public void update(float delta)
    {

    }

    public void onRestart()
    {
        health = 3;
    }

    public int getxLoc()
    {
        return xLoc;
    }

    public void setXLoc(int x)
    {
        this.xLoc = x;
    }

    public void setYLoc(int y)
    {
        this.yLoc = y;
    }

    public int getHealth()
    {
        return health;
    }

    public int getyLoc()
    {
        return yLoc;
    }
}
