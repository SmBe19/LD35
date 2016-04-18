package com.smeanox.games.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smeanox.games.ld35.screens.GameScreen;
import com.smeanox.games.ld35.screens.MenuScreen;

public class LD35 extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}

	public void showGameScreen() {
		setScreen(new GameScreen());
	}

}
