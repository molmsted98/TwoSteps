package com.tsuruta.TSHelpers;

import com.badlogic.gdx.InputProcessor;
import com.tsuruta.GameObjects.Board;
import com.tsuruta.GameWorld.GameWorld;
import com.tsuruta.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 8/28/2015.
 */
public class InputHandler implements InputProcessor
{
    private GameWorld mWorld;
    private Board mBoard;
    private List<SimpleButton> menuButtons;
    private SimpleButton add, start, upLeft, up, upRight, left, right, downLeft, down, downRight;
    private SimpleButton sUpLeft, sUp, sUpRight, sLeft, sRight, sDownLeft, sDown, sDownRight;
    private float scaleFactorX, scaleFactorY;

    //Create all buttons, including location and texture.
    public InputHandler(GameWorld myWorld, float scaleFactorX,
                        float scaleFactorY)
    {
        this.mWorld = myWorld;
        mBoard = myWorld.getBoard();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();

        add = new SimpleButton((mBoard.getSize() * 11) + 2, 0, 11, 11, AssetLoader.add);
        start = new SimpleButton(0, 0, 22, 22, AssetLoader.bg);

        upLeft = new SimpleButton(0, 0, 11, 11, AssetLoader.upLeft);
        up = new SimpleButton(11, 0, 11, 11, AssetLoader.up);
        right = new SimpleButton(22, 11, 11, 11, AssetLoader.right);
        left = new SimpleButton(0, 11, 11, 11, AssetLoader.left);
        upRight = new SimpleButton(22, 0, 11, 11, AssetLoader.upRight);
        downLeft = new SimpleButton(0, 22, 11, 11, AssetLoader.downLeft);
        down = new SimpleButton(11, 22, 11, 11, AssetLoader.down);
        downRight = new SimpleButton(22, 22, 11, 11, AssetLoader.downRight);

        sUpLeft = new SimpleButton(46, 0, 11, 11, AssetLoader.upLeft);
        sUp = new SimpleButton(57, 0, 11, 11, AssetLoader.up);
        sRight = new SimpleButton(68, 11, 11, 11, AssetLoader.right);
        sLeft = new SimpleButton(46, 11, 11, 11, AssetLoader.left);
        sUpRight = new SimpleButton(68, 0, 11, 11, AssetLoader.upRight);
        sDownLeft = new SimpleButton(46, 22, 11, 11, AssetLoader.downLeft);
        sDown = new SimpleButton(57, 22, 11, 11, AssetLoader.down);
        sDownRight = new SimpleButton(68, 22, 11, 11, AssetLoader.downRight);

        menuButtons.add(upLeft);
        menuButtons.add(left);
        menuButtons.add(up);
    }

    //See if a button was clicked
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (mWorld.isMenu())
        {
            start.isTouchDown(screenX, screenY);
        }
        else if (mWorld.isRunning() || mWorld.isChoice())
        {
            if (!add.isTouchDown(screenX, screenY) && !upLeft.isTouchDown(screenX, screenY) &&
                    !up.isTouchDown(screenX, screenY) && !upRight.isTouchDown(screenX, screenY) &&
                    !left.isTouchDown(screenX, screenY) && !right.isTouchDown(screenX, screenY) &&
                    !downLeft.isTouchDown(screenX, screenY) && !downRight.isTouchDown(screenX, screenY) &&
                    !down.isTouchDown(screenX, screenY) && !sUpLeft.isTouchDown(screenX, screenY) &&
                    !sUp.isTouchDown(screenX, screenY) && !sUpRight.isTouchDown(screenX, screenY) &&
                    !sLeft.isTouchDown(screenX, screenY) && !sRight.isTouchDown(screenX, screenY) &&
                    !sDownLeft.isTouchDown(screenX, screenY) && !sDownRight.isTouchDown(screenX, screenY) &&
                    !sDown.isTouchDown(screenX, screenY))
            {
                mWorld.ready(false);
            }
        }
        else if (mWorld.isGameOver() || mWorld.isHighScore())
        {
            mWorld.restart();
        }

        return true;
    }

    //See if button was meant to be clicked
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (mWorld.isMenu())
        {
            if (start.isTouchUp(screenX, screenY))
            {
                mWorld.ready(true);
                return true;
            }
        }
        else if (mWorld.isRunning())
        {
            if (add.isTouchUp(screenX, screenY))
            {
                mWorld.choice();
            }
        }
        else if (mWorld.isChoice())
        {
            if (upLeft.isTouchUp(screenX, screenY))
            {
                setMove("upLeft");
            }
            if (up.isTouchUp(screenX, screenY))
            {
                setMove("up");
            }
            if (upRight.isTouchUp(screenX, screenY))
            {
                setMove("upRight");
            }
            if (down.isTouchUp(screenX, screenY))
            {
                setMove("down");
            }
            if (downLeft.isTouchUp(screenX, screenY))
            {
                setMove("downLeft");
            }
            if (downRight.isTouchUp(screenX, screenY))
            {
                setMove("downRight");
            }
            if (left.isTouchUp(screenX, screenY))
            {
                setMove("left");
            }
            if (right.isTouchUp(screenX, screenY))
            {
                setMove("right");
            }

            if (sUpLeft.isTouchUp(screenX, screenY))
            {
                setMove("sUpLeft");
            }
            if (sUp.isTouchUp(screenX, screenY))
            {
                setMove("sUp");
            }
            if (sUpRight.isTouchUp(screenX, screenY))
            {
                setMove("sUpRight");
            }
            if (sDown.isTouchUp(screenX, screenY))
            {
                setMove("sDown");
            }
            if (sDownLeft.isTouchUp(screenX, screenY))
            {
                setMove("sDownLeft");
            }
            if (sDownRight.isTouchUp(screenX, screenY))
            {
                setMove("sDownRight");
            }
            if (sLeft.isTouchUp(screenX, screenY))
            {
                setMove("sLeft");
            }
            if (sRight.isTouchUp(screenX, screenY))
            {
                setMove("sRight");
            }
            for (int i = 0; i < 3; i ++)
            {
                System.out.println("Move " + i + " is " + mWorld.getMoves()[i]);
            }
            System.out.println("Current turn is: " + mWorld.getTurn());
        }
        return false;
    }

    //Update choices with new tile
    private void setMove(String move)
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorld.getMoves()[i] == "add")
            {
                mWorld.setMoves(i, move);
                mWorld.setTurn(mWorld.getTurn() + 1);
                if (i != 2)
                {
                    add.changeX((add.getX() + 11));
                }
                break;
            }
        }
        mWorld.ready(false);
    }

    //Scale button placement to screen size.
    private int scaleX(int screenX)
    {
        return (int) (screenX / scaleFactorX);
    }
    private int scaleY(int screenY)
    {
        return (int) (screenY / scaleFactorY);
    }

    //Used to check all of the buttons.
    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }

    //Default input methods.
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
