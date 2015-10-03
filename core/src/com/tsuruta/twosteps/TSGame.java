package com.tsuruta.twosteps;

import com.badlogic.gdx.Game;
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
