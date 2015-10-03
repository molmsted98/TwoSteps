package com.tsuruta.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tsuruta.GameObjects.Board;
import com.tsuruta.GameObjects.Player;
import com.tsuruta.TSHelpers.AssetLoader;
import com.tsuruta.TSHelpers.InputHandler;
import com.tsuruta.TweenAccessors.Value;
import com.tsuruta.TweenAccessors.ValueAccessor;
import com.tsuruta.ui.SimpleButton;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Michael on 8/28/2015.
 */
public class GameRenderer
{
    //Variables for the game.
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;

    private int midPointY;

    // Game Objects
    private Player mPlayer, cPlayer;
    private Board mBoard;

    // Game Assets
    private TextureRegion up, down, left, right, upRight, upLeft, downRight, downLeft,
            sUp, sDown, sLeft, sRight, sUpRight, sUpLeft, sDownRight, sDownLeft,
            bg, p1, p2, add, moveCenter, shootCenter;

    //Transition helpers
    private TweenManager manager;
    private Value alpha = new Value();
    private Color transitionColor;

    // Buttons
    private List<SimpleButton> menuButtons;

    //Initialize variables. Set the fixed camera. Set the image maker.
    public GameRenderer(GameWorld world, int gameHeight, int midPointY)
    {
        myWorld = world;

        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    //Initialize game variables.
    private void initGameObjects()
    {
        mPlayer = myWorld.getPlayer();
        mBoard = myWorld.getBoard();
        cPlayer = myWorld.getCPlayer();
    }

    //Add pictures to objects to be drawn.
    private void initAssets()
    {
        bg = AssetLoader.bg;
        up = AssetLoader.up;
        up.flip(false, true);
        down = AssetLoader.down;
        down.flip(false, true);
        left = AssetLoader.left;
        left.flip(true, false);
        right = AssetLoader.right;
        right.flip(true, false);
        upRight = AssetLoader.upRight;
        upRight.flip(false, true);
        upLeft = AssetLoader.upLeft;
        downRight = AssetLoader.downRight;
        downRight.flip(true, false);
        downLeft = AssetLoader.downLeft;
        downLeft.flip(true, true);
        sUp = AssetLoader.sUp;
        sUp.flip(false, true);
        sDown = AssetLoader.sDown;
        sDown.flip(false, true);
        sLeft = AssetLoader.sLeft;
        sLeft.flip(true, false);
        sRight = AssetLoader.sRight;
        sRight.flip(true, false);
        sUpRight = AssetLoader.sUpRight;
        sUpRight.flip(false, true);
        sUpLeft = AssetLoader.sUpLeft;
        sDownRight = AssetLoader.sDownRight;
        sDownRight.flip(true, false);
        sDownLeft = AssetLoader.sDownLeft;
        sDownLeft.flip(true, true);
        bg = AssetLoader.bg;
        p1 = AssetLoader.p1;
        p2 = AssetLoader.p2;
        add = AssetLoader.add;
        moveCenter = AssetLoader.moveCenter;
        moveCenter.flip(false, true);
        shootCenter = AssetLoader.shootCenter;
        shootCenter.flip(false, true);
    }

    //Get the transition ready to be used.
    public void prepareTransition(int r, int g, int b, float duration)
    {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    //Main drawing.
    public void render(float delta, float runTime)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw Background color
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);
        shapeRenderer.end();

        //Draw elements.
        batcher.begin();
        batcher.disableBlending();
        if (myWorld.isMenu())
        {
            drawButtons();
        }
        else if (myWorld.isRunning() || myWorld.isChoice())
        {
            drawBoard();
            drawPlayer();
            drawButtons();
            drawMoveChoices();
            drawShootChoices();
        }
        batcher.end();
        drawTransition(delta);
    }

    //Draw board based on size
    private void drawBoard()
    {
        for (int i = 0; i < mBoard.getSize(); i++)
        {
            for (int e = 0; e < mBoard.getSize(); e++)
            {
                batcher.draw(bg, i*11, e*11);
            }
        }
    }

    //Draw both players
    private void drawPlayer()
    {
        batcher.enableBlending();
        batcher.draw(p1, (mPlayer.getxLoc() - 1) * 11, (mPlayer.getyLoc() - 1) * 11);
        batcher.draw(p2, (cPlayer.getxLoc() - 1) * 11, (cPlayer.getyLoc() - 1) * 11);
        batcher.disableBlending();
    }

    //Draw clickable buttons
    private void drawButtons()
    {
        if (myWorld.isMenu())
        {
            batcher.draw(bg, 0, 0, 22, 22);
        }
        else if(myWorld.isRunning())
        {
            drawMoveChoices();
            drawShootChoices();
        }
        else if (myWorld.isChoice())
        {
            batcher.draw(add, (mBoard.getSize() * 11) + 2, 0);
            batcher.draw(upLeft, 0, 0);
            batcher.draw(up, 11, 0);
            batcher.draw(upRight, 22, 0);
            batcher.draw(left, 0, 11);
            batcher.draw(moveCenter, 11, 11);
            batcher.draw(right, 22, 11);
            batcher.draw(downLeft, 0, 22);
            batcher.draw(down, 11, 22);
            batcher.draw(downRight, 22, 22);

            batcher.draw(sUpLeft, 46, 0);
            batcher.draw(sUp, 57, 0);
            batcher.draw(sUpRight, 68, 0);
            batcher.draw(sLeft, 46, 11);
            batcher.draw(shootCenter, 57, 11);
            batcher.draw(sRight, 68, 11);
            batcher.draw(sDownLeft, 46, 22);
            batcher.draw(sDown, 57, 22);
            batcher.draw(sDownRight, 68, 22);
        }
    }

    //Draw chosen tiles.
    private void drawMoveChoices()
    {
        for (int i = 0; i < 3; i ++)
        {
            if(myWorld.getMoves()[i] == "add")
            {
                batcher.draw(add, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "upLeft")
            {
                batcher.draw(upLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "upRight")
            {
                batcher.draw(upRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "down")
            {
                batcher.draw(down, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "up")
            {
                batcher.draw(up, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "downLeft")
            {
                batcher.draw(downLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "downRight")
            {
                batcher.draw(downRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("left"))
            {
                batcher.draw(left, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("right"))
            {
                batcher.draw(right, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
        }
    }

    //Draw chosen tiles.
    private void drawShootChoices()
    {
        for (int i = 0; i < 3; i ++)
        {
            if(myWorld.getMoves()[i].equals("sUpLeft"))
            {
                batcher.draw(sUpLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("sUpRight"))
            {
                batcher.draw(sUpRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("sDown"))
            {
                batcher.draw(sDown, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sUp")
            {
                batcher.draw(sUp, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sDownLeft")
            {
                batcher.draw(sDownLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sDownRight")
            {
                batcher.draw(sDownRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sLeft")
            {
                batcher.draw(sLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sRight")
            {
                batcher.draw(sRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
        }
    }

    //Activate the transition.
    private void drawTransition(float delta)
    {
        if (alpha.getValue() > 0)
        {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}
