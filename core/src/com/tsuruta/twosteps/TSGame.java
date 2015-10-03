package com.tsuruta.twosteps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tsuruta.Screens.SplashScreen;
import com.tsuruta.TSHelpers.AssetLoader;

public class TSGame extends Game
{
	@Override
	public void create()
	{
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose()
	{
		super.dispose();
		AssetLoader.dispose();
	}
}
