package com.tsuruta.GameObjects;

/**
 * Created by Michael on 8/28/2015.
 */
public class Board {
    //Instance variables.
    private int size;
    private String type;

    //Construct a board of certain dimension and terrain type.
    public Board(int size) {
        this.size = size;
        //this.type = type;
    }

    //Getters.
    public int getSize() {
        return size;
    }
    public String getType()
    {
        return type;
    }
}
