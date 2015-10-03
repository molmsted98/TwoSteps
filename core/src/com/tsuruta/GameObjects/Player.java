package com.tsuruta.GameObjects;

/**
 * Created by Michael on 8/28/2015.
 */
public class Player
{
    //Instance variables.
    private int health;
    private int xLoc;
    private int yLoc;

    //Make a player at a starting point with health.
    public Player(int startX, int startY, int health)
    {
        this.health = health;
        xLoc = startX;
        yLoc = startY;
    }

    public void onRestart()
    {
        health = 3;
    }

    //Getters and setters.
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
