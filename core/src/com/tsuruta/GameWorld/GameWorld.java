package com.tsuruta.GameWorld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.tsuruta.GameObjects.Board;
import com.tsuruta.GameObjects.Player;
import com.tsuruta.TSHelpers.AssetLoader;

import java.util.Random;

/**
 * Created by Michael on 8/28/2015.
 */
public class GameWorld
{
    private Player mPlayer;
    private Player cPlayer;
    private Board mBoard;
    private Rectangle ground;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;
    private GameRenderer renderer;
    private int turn = 0;
    private int currentTurn = 0;
    private String[] moves = {"add", "add", "add"};
    private String[] cMoves = {"add", "add", "add"};

    private GameState currentState;

    //Switch between screens
    public enum GameState
    {
        MENU, RUNNING, CHOICE, GAMEOVER, HIGHSCORE
    }

    //Initialize variables
    public GameWorld(int midPointY)
    {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        mPlayer = new Player(1, 1, 3);
        mBoard = new Board(9);
        cPlayer = new Player(mBoard.getSize(), mBoard.getSize(), 3);
        // The grass should start 66 pixels below the midPointY
        ground = new Rectangle(0, midPointY + 66, 137, 11);
    }

    //Based on state run other updates
    public void update(float delta)
    {
        runTime += delta;

        switch (currentState)
        {
            case MENU:
                updateReady(delta);
                break;

            case CHOICE:
            case RUNNING:
                updateRunning(delta);
                break;

            default:
                break;
        }

    }

    //Prevent either player from leaving field
    private boolean checkCollision(int direction, Player playerr)
    {
        int xLoc = playerr.getxLoc();
        int yLoc = playerr.getyLoc();
        int size = mBoard.getSize();
        // upLeft, down, up, upRight, downLeft, left, right, downRight
        switch (direction)
        {
            case 1:
                return (xLoc - 1 == 0 || yLoc - 1 == 0);
            case 2:
                return (yLoc + 1 > size);
            case 3:
                return (yLoc - 1 == 0);
            case 4:
                return (xLoc + 1 > size || yLoc - 1 == 0);
            case 5:
                return (xLoc - 1 == 0 || yLoc + 1 > size);
            case 6:
                return (xLoc - 1 == 0);
            case 7:
                return (xLoc + 1 > size);
            case 8:
                return (xLoc + 1 > size || yLoc + 1 > size);
            default:
                return true;
        }
    }

    //Maybe useless?
    //TODO: Check if this can be removed
    private void updateReady(float delta) {
        mPlayer.updateReady(runTime);
    }

    //Move the Computer
    public void runComputer()
    {
        if (currentTurn != turn && turn > 2)
        {
            Random rand = new Random();
            int randomNum = rand.nextInt((8 - 1) + 1) + 1;
            if(checkCollision(randomNum, cPlayer))
            {
                randomNum = 100;
            }
            // upLeft, down, up, upRight, downLeft, left, right, downRight
            switch (randomNum)
            {
                case 1:
                    cPlayer.setXLoc(cPlayer.getxLoc() - 1);
                    cPlayer.setYLoc(cPlayer.getyLoc() - 1);
                    break;
                case 2:
                    cPlayer.setYLoc(cPlayer.getyLoc() + 1);
                    break;
                case 3:
                    cPlayer.setYLoc(cPlayer.getyLoc() - 1);
                    break;
                case 4:
                    cPlayer.setYLoc(cPlayer.getyLoc() - 1);
                    cPlayer.setXLoc(cPlayer.getxLoc() + 1);
                    break;
                case 5:
                    cPlayer.setXLoc(cPlayer.getxLoc() - 1);
                    cPlayer.setYLoc(cPlayer.getyLoc() + 1);
                    break;
                case 6:
                    cPlayer.setXLoc(cPlayer.getxLoc() - 1);
                    break;
                case 7:
                    cPlayer.setXLoc(cPlayer.getxLoc() + 1);
                    break;
                case 8:
                    cPlayer.setYLoc(cPlayer.getyLoc() + 1);
                    cPlayer.setXLoc(cPlayer.getxLoc() + 1);
                    break;
            }
        }
    }

    public void runPlayer()
    {
        //Move player based on choices
        if (currentTurn != turn && turn > 2)
        {
            if(getMoves()[0] == "upLeft")
            {
                if (!checkCollision(1, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() - 1);
                    mPlayer.setYLoc(mPlayer.getyLoc() - 1);
                }
            }
            if(getMoves()[0] == "down")
            {
                if (!checkCollision(2, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() + 1);
                }
            }
            if(getMoves()[0] == "up")
            {
                if (!checkCollision(3, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() - 1);
                }
            }
            if(getMoves()[0] == "upRight")
            {
                if (!checkCollision(4, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() - 1);
                    mPlayer.setXLoc(mPlayer.getxLoc() + 1);
                }
            }
            if(getMoves()[0] == "downLeft")
            {
                if (!checkCollision(5, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() - 1);
                    mPlayer.setYLoc(mPlayer.getyLoc() + 1);
                }

            }
            if(getMoves()[0] == "left")
            {
                if (!checkCollision(6, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() - 1);
                }
            }
            if(getMoves()[0] == "right")
            {
                if (!checkCollision(7, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() + 1);
                }
            }
            if(getMoves()[0] == "downRight")
            {
                if (!checkCollision(8, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() + 1);
                    mPlayer.setXLoc(mPlayer.getxLoc() + 1);
                }
            }

            if(getMoves()[0] == "sUpLeft")
            {

            }
            if(getMoves()[0] == "sDown")
            {
                if (cPlayer.getyLoc() > mPlayer.getyLoc() && cPlayer.getxLoc() == mPlayer.getxLoc())
                {

                }
            }
            if(getMoves()[0] == "sUp")
            {
                if (cPlayer.getxLoc() == mPlayer.getxLoc() && cPlayer.getyLoc() < mPlayer.getyLoc())
                {

                }
            }
            if(getMoves()[0] == "sUpRight")
            {

            }
            if(getMoves()[0] == "sDownLeft")
            {

            }
            if(getMoves()[0] == "sLeft")
            {
                if (cPlayer.getyLoc() == mPlayer.getyLoc() && cPlayer.getxLoc() < mPlayer.getxLoc())
                {

                }
            }
            if(getMoves()[0] == "sRight")
            {
                if (cPlayer.getxLoc() > mPlayer.getxLoc() && cPlayer.getyLoc() == mPlayer.getyLoc())
                {

                }
            }
            if(getMoves()[0] == "sDownRight")
            {

            }

            //Shift move tiles over
            setMoves(0, getMoves()[1]);
            setMoves(1, getMoves()[2]);
            setMoves(2, "add");
            System.out.println(mPlayer.getxLoc() + " " + mPlayer.getyLoc());
        }

        if(currentTurn != turn)
        {
            currentTurn ++;
        }
    }

    //The main brain for the game
    //TODO: Split this up into smaller voids
    public void updateRunning(float delta)
    {
        if (delta > .15f)
        {
            delta = .15f;
        }

        runComputer();
        runPlayer();

        //Useless code below
        //TODO: Check to remove
        if (turn > 2)
        {

        }

        mPlayer.update(delta);
    }

    public Player getPlayer()
    {
        return mPlayer;
    }

    public int getTurn()
    {
        return turn;
    }

    public void setTurn(int turn)
    {
        this.turn = turn;
    }

    public Board getBoard()
    {
        return mBoard;
    }

    public int getMidPointY() {
        return midPointY;
    }

    //Switch from menu to game
    public void ready(boolean transition)
    {
        currentState = GameState.RUNNING;
        if (transition)
        {
            renderer.prepareTransition(0, 0, 0, 1f);
        }
    }

    //Switch to showing move choices
    public void choice()
    {
        currentState = GameState.CHOICE;
    }

    //Restart the game
    public void restart()
    {
        score = 0;
        mPlayer.onRestart();
        ready(true);
    }


    //Check for state
    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isChoice()
    {
        return currentState == GameState.CHOICE;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }


    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public String[] getMoves()
    {
        return moves;
    }

    public Player getCPlayer()
    {
        return cPlayer;
    }

    public void setMoves(int position, String value)
    {
        moves[position] = value;
    }
}
