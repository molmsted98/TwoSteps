package com.tsuruta.GameWorld;

import com.badlogic.gdx.Gdx;
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
        MENU, RUNNING, CHOICE, GAMEOVER, HIGHSCORE, MOVING
    }

    //Initialize instance variables. Make players and board.
    public GameWorld(int midPointY)
    {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        mPlayer = new Player(1, 1, 3);
        mBoard = new Board(7);
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
        checkDeath();
        runComputer();
        runPlayer(getMoves()[0]);
    }

    public void checkDeath()
    {
        if (mPlayer.getHealth() == 0 || cPlayer.getHealth() == 0)
        {
            currentState = GameState.GAMEOVER;
        }
    }

    //Move the Computer
    public void runComputer()
    {
        //Only move after two turns.
        if (currentTurn != turn && turn > 2 && !isMoving())
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
            checkMelee(cPlayer, mPlayer);
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
                checkShotHit(mPlayer, cPlayer, 1);
            }
            if(move == "sDown")
            {
                checkShotHit(mPlayer, cPlayer, 2);
            }
            if(move == "sUp")
            {
                checkShotHit(mPlayer, cPlayer, 3);
            }
            if(move == "sUpRight")
            {
                checkShotHit(mPlayer, cPlayer, 4);
            }
            if(move == "sDownLeft")
            {
                checkShotHit(mPlayer, cPlayer, 5);
            }
            if(move == "sLeft")
            {
                checkShotHit(mPlayer, cPlayer, 6);
            }
            if(move == "sRight")
            {
                checkShotHit(mPlayer, cPlayer, 7);
            }
            if(move == "sDownRight")
            {
                checkShotHit(mPlayer, cPlayer, 8);
            }

            checkMelee(mPlayer, cPlayer);

            //Shift move tiles over by one
            setMoves(0, getMoves()[1]);
            setMoves(1, getMoves()[2]);
            setMoves(2, "add");

            System.out.println("Player location is: "
                    + mPlayer.getxLoc() + " , " + mPlayer.getyLoc());
            System.out.println("Player Two location is: "
                    + cPlayer.getxLoc() + " , " + cPlayer.getyLoc());
            System.out.println("Player One health is: " + mPlayer.getHealth());
            System.out.println("Player Two health is: " + cPlayer.getHealth());
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
            case 1: //upLeft
                return (xLoc - 1 == 0 || yLoc - 1 == 0);
            case 2: //Down
                return (yLoc + 1 > size);
            case 3: //Up
                return (yLoc - 1 == 0);
            case 4: //upRight
                return (xLoc + 1 > size || yLoc - 1 == 0);
            case 5: //downLeft
                return (xLoc - 1 == 0 || yLoc + 1 > size);
            case 6: //Left
                return (xLoc - 1 == 0);
            case 7: //Right
                return (xLoc + 1 > size);
            case 8: //downRight
                return (xLoc + 1 > size || yLoc + 1 > size);
            default:
                return true;
        }
    }

    private void checkShotHit(Player shooter, Player target, int direction)
    {
        switch (direction)
        {
            case 1: //upLeft

            case 2: //Down
                if (target.getyLoc() > shooter.getyLoc() && target.getxLoc() == shooter.getxLoc())
                {
                    target.setHealth(target.getHealth()-1);
                }
            case 3: //Up
                if (target.getxLoc() == shooter.getxLoc() && target.getyLoc() < shooter.getyLoc())
                {
                    target.setHealth(target.getHealth()-1);
                }
            case 4: //upRight

            case 5: //downLeft

            case 6: //Left
                if (target.getyLoc() == shooter.getyLoc() && target.getxLoc() < shooter.getxLoc())
                {
                    target.setHealth(target.getHealth()-1);
                }
            case 7: //Right
                if (target.getxLoc() > shooter.getxLoc() && target.getyLoc() == shooter.getyLoc())
                {
                    target.setHealth(target.getHealth()-1);
                }
            case 8: //downRight

        }
    }

    private void checkMelee(Player attacker, Player target)
    {
        if (attacker.getxLoc() == target.getxLoc() && attacker.getyLoc() == target.getyLoc())
        {
            target.setHealth(target.getHealth()-1);
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

    public void moving()
    {
        currentState = GameState.MOVING;
    }

    public void running()
    {
        currentState = GameState.RUNNING;
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
    public boolean isMoving()
    {
        return currentState == GameState.MOVING;
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
