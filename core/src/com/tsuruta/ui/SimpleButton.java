package com.tsuruta.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tsuruta.TSHelpers.AssetLoader;

/**
 * Created by Michael on 8/28/2015.
 */
public class SimpleButton
{
    private float x, y, width, height;
    private TextureRegion buttonUp, buttonDown;
    private Rectangle bounds;
    private boolean isPressed = false;

    //Constructor to make a button with up and down textures.
    public SimpleButton(float x, float y, float width, float height,
                        TextureRegion buttonUp)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        //this.buttonDown = buttonDown;
        bounds = new Rectangle(x, y, width, height);
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    //TODO: Use this method instead of the methods in GameRenderer.
    public void draw(SpriteBatch batcher)
    {
        if (isPressed)
        {
            batcher.draw(buttonDown, x, y, width, height);
        }
        else
        {
            batcher.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY)
    {
        if (bounds.contains(screenX, screenY))
        {
            isPressed = true;
            return true;
        }
        return false;
    }

    //Whenever a finger/click is lifted.
    public boolean isTouchUp(int screenX, int screenY)
    {
        // It only counts as a touchUp if the button was in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed)
        {
            isPressed = false;
            return true;
        }

        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }

    //Getters and setters.
    public void changeX(float x)
    {
        this.x = x;
        bounds.x = x;
    }
    public void changeY(float y)
    {
        this.y = y;
        bounds.y = y;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
}
