package com.tsuruta.TSHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;

/**
 * Created by Michael on 8/28/2015.
 */
public class AssetLoader
{
    //Variables for textures.
    public static Texture sTexture, sLogoTexture;
    public static TextureRegion logo, up, down, left, right, upRight, upLeft, downRight, downLeft,
            sUp, sDown, sLeft, sRight, sUpRight, sUpLeft, sDownRight, sDownLeft,
            bg, p1, p2, add, moveCenter, shootCenter;

    //Set textures to the objects.
    public static void load()
    {
        sLogoTexture = new Texture(Gdx.files.internal("data" + File.separator + "logo.png"));
        sLogoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sTexture = new Texture(Gdx.files.internal("data/texture.png"));
        sTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        logo = new TextureRegion(sLogoTexture, 0, 0, 512, 114);
        up = new TextureRegion(sTexture, 0, 0, 11, 11);
        down = new TextureRegion(sTexture, 11, 0, 11, 11);
        left = new TextureRegion(sTexture, 22, 0, 11, 11);
        right = new TextureRegion(sTexture, 33, 0, 11, 11);
        upRight = new TextureRegion(sTexture, 44, 0, 11, 11);
        upLeft = new TextureRegion(sTexture, 55, 0, 11, 11);
        downRight = new TextureRegion(sTexture, 66, 0, 11, 11);
        downLeft = new TextureRegion(sTexture, 77, 0, 11, 11);

        sUp = new TextureRegion(sTexture, 0, 13, 11, 11);
        sDown = new TextureRegion(sTexture, 11, 13, 11, 11);
        sLeft = new TextureRegion(sTexture, 22, 13, 11, 11);
        sRight = new TextureRegion(sTexture, 33, 13, 11, 11);
        sUpRight = new TextureRegion(sTexture, 44, 13, 11, 11);
        sUpLeft = new TextureRegion(sTexture, 55, 13, 11, 11);
        sDownRight = new TextureRegion(sTexture, 66, 13, 11, 11);
        sDownLeft = new TextureRegion(sTexture, 77, 13, 11, 11);

        bg = new TextureRegion(sTexture, 90, 0, 11, 11);
        p1 = new TextureRegion(sTexture, 103, 0, 11, 11);
        p2 = new TextureRegion(sTexture, 113, 0, 11, 11);
        add = new TextureRegion(sTexture, 0, 26, 11, 11);
        moveCenter = new TextureRegion(sTexture, 11, 26, 11, 11);
        shootCenter = new TextureRegion(sTexture, 22, 26, 11, 11);

        up.flip(false, true);
        down.flip(false, true);
        left.flip(true, false);
        right.flip(true, false);
        upRight.flip(false, true);
        downRight.flip(true, false);
        downLeft.flip(true, true);
        sUp.flip(false, true);
        sDown.flip(false, true);
        sLeft.flip(true, false);
        sRight.flip(true, false);
        sUpRight.flip(false, true);
        sDownRight.flip(true, false);
        sDownLeft.flip(true, true);
        moveCenter.flip(false, true);
        shootCenter.flip(false, true);
    }

    public static void dispose()
    {
        sTexture.dispose();
        sLogoTexture.dispose();
    }
}
