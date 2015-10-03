package com.tsuruta.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tsuruta.GameWorld.GameRenderer;
import com.tsuruta.GameWorld.GameWorld;
import com.tsuruta.TSHelpers.InputHandler;

/**
 * Created by Michael on 8/28/2015.
 */
public class GameScreen implements Screen
{
    //Instance variables.
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    //Constructor for the game.
    public GameScreen()
    {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
        world.setRenderer(renderer);
    }

    //Causes the game update to happen, and image update.
    @Override
    public void render(float delta)
    {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    //Default methods.
    @Override
    public void resize(int width, int height) {}
    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}
}
