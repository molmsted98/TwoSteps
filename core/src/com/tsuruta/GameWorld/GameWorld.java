package com.tsuruta.GameWorld;

import com.tsuruta.GameObjects.Board;
import com.tsuruta.GameObjects.Player;

import java.util.Random;

/**
 * Created by Michael on 8/28/2015.
 */
public class GameWorld
{
    private Player mPlayer;
    private Player cPlayer;
    private Board mBoard;
    private float runTime = 0;
    private int midPointY;
    private GameRenderer renderer;
    private int turn = 0;
    private int currentTurn = 0;
    private String[] moves = {"add", "add", "add"};
    //TODO: Implent computer's logic using move list.
    private String[] cMoves = {"add", "add", "add"};

    private GameState currentState;

    //Variable to know which screen is to be shown.
    public enum GameState
    {
        MENU, RUNNING, CHOICE, GAMEOVER, HIGHSCORE
    }

    //Initialize instance variables. Make players and board.
    public GameWorld(int midPointY)
    {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        mPlayer = new Player(1, 1, 3);
        mBoard = new Board(9);
        cPlayer = new Player(mBoard.getSize(), mBoard.getSize(), 3);
    }

    //Run other update methods depending on game state.
    public void update(float delta)
    {
        runTime += delta;
        switch (currentState)
        {
            case MENU:
                break;
            case CHOICE:
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    //Run the game.
    public void updateRunning(float delta)
    {
        //Prevent frame skipping
        if (delta > .15f)
        {
            delta = .15f;
        }
        runComputer();
        runPlayer(getMoves()[0]);
    }

    //Move the Computer
    public void runComputer()
    {
        //Only move after two turns.
        if (currentTurn != turn && turn > 2)
        {
            //Pick a random move, then check for collision.
            Random rand = new Random();
            int randomNum = rand.nextInt((8 - 1) + 1) + 1;
            if(checkCollision(randomNum, cPlayer))
            {
                randomNum = 100;
            }
            //Do the move. upLeft, down, up, upRight, downLeft, left, right, downRight
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

    //Move the player
    public void runPlayer(String move)
    {
        //Only move after turn two.
        if (currentTurn != turn && turn > 2)
        {
            //Move in the proper direction.
            if(move == "upLeft")
            {
                if (!checkCollision(1, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() - 1);
                    mPlayer.setYLoc(mPlayer.getyLoc() - 1);
                }
            }
            if(move == "down")
            {
                if (!checkCollision(2, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() + 1);
                }
            }
            if(move == "up")
            {
                if (!checkCollision(3, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() - 1);
                }
            }
            if(move == "upRight")
            {
                if (!checkCollision(4, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() - 1);
                    mPlayer.setXLoc(mPlayer.getxLoc() + 1);
                }
            }
            if(move == "downLeft")
            {
                if (!checkCollision(5, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() - 1);
                    mPlayer.setYLoc(mPlayer.getyLoc() + 1);
                }
            }
            if(move == "left")
            {
                if (!checkCollision(6, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() - 1);
                }
            }
            if(move == "right")
            {
                if (!checkCollision(7, mPlayer))
                {
                    mPlayer.setXLoc(mPlayer.getxLoc() + 1);
                }
            }
            if(move == "downRight")
            {
                if (!checkCollision(8, mPlayer))
                {
                    mPlayer.setYLoc(mPlayer.getyLoc() + 1);
                    mPlayer.setXLoc(mPlayer.getxLoc() + 1);
                }
            }

            //Shoot in the proper direction.
            if(move == "sUpLeft")
            {

            }
            if(move == "sDown")
            {
                if (cPlayer.getyLoc() > mPlayer.getyLoc() && cPlayer.getxLoc() == mPlayer.getxLoc())
                {

                }
            }
            if(move == "sUp")
            {
                if (cPlayer.getxLoc() == mPlayer.getxLoc() && cPlayer.getyLoc() < mPlayer.getyLoc())
                {

                }
            }
            if(move == "sUpRight")
            {

            }
            if(move == "sDownLeft")
            {

            }
            if(move == "sLeft")
            {
                if (cPlayer.getyLoc() == mPlayer.getyLoc() && cPlayer.getxLoc() < mPlayer.getxLoc())
                {

                }
            }
            if(move == "sRight")
            {
                if (cPlayer.getxLoc() > mPlayer.getxLoc() && cPlayer.getyLoc() == mPlayer.getyLoc())
                {

                }
            }
            if(move == "sDownRight")
            {

            }

            //Shift move tiles over by one
            setMoves(0, getMoves()[1]);
            setMoves(1, getMoves()[2]);
            setMoves(2, "add");

            System.out.println("Player location is: "
                    + mPlayer.getxLoc() + " , " + mPlayer.getyLoc());
        }
        //Start the next turn.
        if(currentTurn != turn)
        {
            currentTurn ++;
        }
    }

    //Prevent either player from moving in a way to leave the field
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

    //Switch from menu to running
    public void ready(boolean transition)
    {
        currentState = GameState.RUNNING;
        if (transition)
        {
            renderer.prepareTransition(0, 0, 0, 1f);
        }
    }

    //Bring up UI for picking next move.
    public void choice()
    {
        currentState = GameState.CHOICE;
    }

    //Restart the game
    public void restart()
    {
        mPlayer.onRestart();
        ready(true);
    }

    //Check for state
    public boolean isGameOver()
    {
        return currentState == GameState.GAMEOVER;
    }
    public boolean isHighScore()
    {
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

    //Getters and setters.
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
}
