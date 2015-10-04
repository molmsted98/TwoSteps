package com.tsuruta.GameObjects;

/**
 * Created by Michael on 8/28/2015.
 */
public class Board {
    //Instance variables.
    private int size;
    private String[][] layout;

    //Construct a board of certain dimension and terrain type.
    public Board(int size) {
        this.size = size;
        layout = new String[size][size];
    }

    //Getters/Setters
    public int getSize() {
        return size;
    }

    public void setLayout(int row, int column, String value)
    {
        layout[row][column] = value;
    }

    public String getLayout(int row, int column)
    {
        return layout[row][column];
    }
}
