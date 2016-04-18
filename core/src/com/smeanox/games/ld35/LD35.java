package com.smeanox.games.ld35;

import com.badlogic.gdx.Game;
import com.smeanox.games.ld35.screens.CreditsScreen;
import com.smeanox.games.ld35.screens.GameScreen;
import com.smeanox.games.ld35.screens.MenuScreen;

public class LD35 extends Game {

	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private CreditsScreen creditsScreen;

	@Override
	public void create() {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		creditsScreen = new CreditsScreen(this);

		showMenuScreen();
//		showGameScreen();
//		showCreditsScreen();
	}

	public void showMenuScreen(){
		setScreen(menuScreen);
	}

	public void showGameScreen() {
		setScreen(gameScreen);
	}

	public void showCreditsScreen(){
		setScreen(creditsScreen);
	}

	public void resetGameScreen() {
		gameScreen = new GameScreen(this);
	}

}
