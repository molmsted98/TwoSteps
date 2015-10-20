package com.tsuruta.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private int currentX;
    private int currentY;
    private int cCurrentX;
    private int cCurrentY;
    private double animation;
    private double cAnimation;
    // Game Objects
    private Player mPlayer, cPlayer;
    private Board mBoard;

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

        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    //Initialize game variables.
    private void initGameObjects()
    {
        mPlayer = myWorld.getPlayer();
        currentX = mPlayer.getxLoc();
        currentY = mPlayer.getyLoc();
        mBoard = myWorld.getBoard();
        cPlayer = myWorld.getCPlayer();
        cCurrentX = cPlayer.getxLoc();
        cCurrentY = cPlayer.getyLoc();
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
        else if (myWorld.isRunning() || myWorld.isChoice() || myWorld.isMoving())
        {
            drawBoard();
            drawPlayer();
            drawComputer();
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
                batcher.draw(AssetLoader.bg, i*11, e*11);
            }
        }
    }

    //Draw both players
    private void drawPlayer()
    {
        batcher.enableBlending();
        //Meaning that the player has just moved, start animation.
        if (currentY != mPlayer.getyLoc() && currentX != mPlayer.getxLoc())
        {
            myWorld.moving();
            int changeX = mPlayer.getxLoc() - currentX;
            int changeY = mPlayer.getyLoc() - currentY;
            if (animation < 30)
            {
                batcher.draw(AssetLoader.p1,
                        ((currentX - 1) * 11) + (int)(changeX * (11 * (animation/30))),
                            ((currentY - 1) * 11) + (int)(changeY * (11 * (animation/30))));
                animation ++;
            }
            else
            {
                currentY = mPlayer.getyLoc();
                currentX = mPlayer.getxLoc();
                animation = 0;
                myWorld.running();
            }
        }
        else if (currentY != mPlayer.getyLoc())
        {
            myWorld.moving();
            int changeY = mPlayer.getyLoc() - currentY;
            if (animation < 30)
            {
                batcher.draw(AssetLoader.p1, (currentX - 1) * 11,
                        ((currentY - 1) * 11) + (int)(changeY * (11 * (animation/30))));
                animation ++;
            }
            else
            {
                currentY = mPlayer.getyLoc();
                animation = 0;
                myWorld.running();
            }
        }
        else if (currentX != mPlayer.getxLoc())
        {
            myWorld.moving();
            int changeX = mPlayer.getxLoc() - currentX;
            if (animation < 30)
            {
                batcher.draw(AssetLoader.p1,
                        (currentX - 1) * 11 + (int)(changeX * (11 * (animation/30))),
                            ((currentY - 1) * 11));
                animation ++;
            }
            else
            {
                currentX = mPlayer.getxLoc();
                animation = 0;
                myWorld.running();
            }
        }
        else
        {
            batcher.draw(AssetLoader.p1, (mPlayer.getxLoc() - 1) * 11, (mPlayer.getyLoc() - 1) * 11);
        }
        batcher.disableBlending();
    }

    private void drawComputer()
    {
        batcher.enableBlending();
        if (myWorld.isMoving())
        {
            batcher.draw(AssetLoader.p2, (cCurrentX - 1) * 11, (cCurrentY - 1) * 11);
        }
        else
        {
            //Meaning that the player has just moved, start animation.
            if (cCurrentY != cPlayer.getyLoc() && cCurrentX != cPlayer.getxLoc())
            {
                int changeX = cPlayer.getxLoc() - cCurrentX;
                int changeY = cPlayer.getyLoc() - cCurrentY;
                if (cAnimation < 30)
                {
                    batcher.draw(AssetLoader.p2,
                            ((cCurrentX - 1) * 11) + (int)(changeX * (11 * (cAnimation/30))),
                            ((cCurrentY - 1) * 11) + (int)(changeY * (11 * (cAnimation/30))));
                }
                else
                {
                    cCurrentY = cPlayer.getyLoc();
                    cCurrentX = cPlayer.getxLoc();
                    cAnimation = 0;
                }
                cAnimation ++;
            }
            else if (cCurrentY != cPlayer.getyLoc())
            {
                int changeY = cPlayer.getyLoc() - cCurrentY;
                if (cAnimation < 30)
                {
                    batcher.draw(AssetLoader.p2, (cCurrentX - 1) * 11,
                            ((cCurrentY - 1) * 11) + (int)(changeY * (11 * (cAnimation/30))));
                }
                else
                {
                    cCurrentY = cPlayer.getyLoc();
                    cAnimation = 0;
                }
                cAnimation ++;
            }
            else if (cCurrentX != cPlayer.getxLoc())
            {
                int changeX = cPlayer.getxLoc() - cCurrentX;
                if (cAnimation < 30)
                {
                    batcher.draw(AssetLoader.p2,
                            (cCurrentX - 1) * 11 + (int)(changeX * (11 * (cAnimation/30))),
                            ((cCurrentY - 1) * 11));
                }
                else
                {
                    cCurrentX = cPlayer.getxLoc();
                    cAnimation = 0;
                }
                cAnimation ++;
            }
            else
            {
                batcher.draw(AssetLoader.p2, (cCurrentX - 1) * 11, (cCurrentY - 1) * 11);
            }
        }

        batcher.disableBlending();
    }

    //Draw clickable buttons
    private void drawButtons()
    {
        if (myWorld.isMenu())
        {
            batcher.draw(AssetLoader.bg, 0, 0, 22, 22);
        }
        else if(myWorld.isRunning())
        {
            drawMoveChoices();
            drawShootChoices();
        }
        else if (myWorld.isChoice())
        {
            batcher.draw(AssetLoader.add, (mBoard.getSize() * 11) + 2, 0);
            batcher.draw(AssetLoader.upLeft, 0, 0);
            batcher.draw(AssetLoader.up, 11, 0);
            batcher.draw(AssetLoader.upRight, 22, 0);
            batcher.draw(AssetLoader.left, 0, 11);
            batcher.draw(AssetLoader.moveCenter, 11, 11);
            batcher.draw(AssetLoader.right, 22, 11);
            batcher.draw(AssetLoader.downLeft, 0, 22);
            batcher.draw(AssetLoader.down, 11, 22);
            batcher.draw(AssetLoader.downRight, 22, 22);

            batcher.draw(AssetLoader.sUpLeft, 46, 0);
            batcher.draw(AssetLoader.sUp, 57, 0);
            batcher.draw(AssetLoader.sUpRight, 68, 0);
            batcher.draw(AssetLoader.sLeft, 46, 11);
            batcher.draw(AssetLoader.shootCenter, 57, 11);
            batcher.draw(AssetLoader.sRight, 68, 11);
            batcher.draw(AssetLoader.sDownLeft, 46, 22);
            batcher.draw(AssetLoader.sDown, 57, 22);
            batcher.draw(AssetLoader.sDownRight, 68, 22);

            batcher.draw(AssetLoader.bg, 40, 40);
        }
    }

    //Draw chosen tiles.
    private void drawMoveChoices()
    {
        for (int i = 0; i < 3; i ++)
        {
            if(myWorld.getMoves()[i] == "add")
            {
                batcher.draw(AssetLoader.add, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "upLeft")
            {
                batcher.draw(AssetLoader.upLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "upRight")
            {
                batcher.draw(AssetLoader.upRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "down")
            {
                batcher.draw(AssetLoader.down, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "up")
            {
                batcher.draw(AssetLoader.up, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "downLeft")
            {
                batcher.draw(AssetLoader.downLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "downRight")
            {
                batcher.draw(AssetLoader.downRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("left"))
            {
                batcher.draw(AssetLoader.left, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("right"))
            {
                batcher.draw(AssetLoader.right, (mBoard.getSize() * 11) + (11*i) + 2, 0);
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
                batcher.draw(AssetLoader.sUpLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("sUpRight"))
            {
                batcher.draw(AssetLoader.sUpRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i].equals("sDown"))
            {
                batcher.draw(AssetLoader.sDown, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sUp")
            {
                batcher.draw(AssetLoader.sUp, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sDownLeft")
            {
                batcher.draw(AssetLoader.sDownLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sDownRight")
            {
                batcher.draw(AssetLoader.sDownRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sLeft")
            {
                batcher.draw(AssetLoader.sLeft, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "sRight")
            {
                batcher.draw(AssetLoader.sRight, (mBoard.getSize() * 11) + (11*i) + 2, 0);
            }
            if(myWorld.getMoves()[i] == "swap")
            {
                batcher.draw(AssetLoader.bg, (mBoard.getSize() * 11) + (11*i) + 2, 0);

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
