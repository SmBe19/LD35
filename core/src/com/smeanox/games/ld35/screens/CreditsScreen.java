package com.smeanox.games.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.Font;
import com.smeanox.games.ld35.LD35;
import com.smeanox.games.ld35.io.Textures;

public class CreditsScreen implements Screen {

	private LD35 game;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private int w, h;

	private TextureRegion orb;
	private TextureRegion shit;

	private float animationTime = 0;

	public CreditsScreen(LD35 game) {
		this.game = game;
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera(Consts.WIDTH, Consts.HEIGHT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		orb = new TextureRegion(Textures.spritesheet.get(), 2*32, 7*32, 32, 32);
		shit = new TextureRegion(Textures.spritesheet.get(), 3*32, 7*32, 32, 32);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		animationTime += delta;

		if(animationTime > 5){
			game.resetGameScreen();
			game.showMenuScreen();
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		spriteBatch.begin();
		spriteBatch.setColor(1, 1, 1, 1);
		float orbScale;
		if(animationTime <= 1) {
			orbScale = 1.0f + (1 - animationTime) * (1 - animationTime);
		} else {
			orbScale = 1.0f;
		}

		spriteBatch.draw(orb, -5*orbScale, -5*orbScale, 10*orbScale, 10*orbScale);
		if(animationTime <= 1){
			spriteBatch.setColor(1, 1, 1, 1-animationTime);
		} else{
			spriteBatch.setColor(1, 1, 1, 0);
		}
		spriteBatch.draw(shit, -Consts.WIDTH/2, -Consts.HEIGHT/2, Consts.WIDTH, Consts.HEIGHT);

		spriteBatch.setColor(1, 1, 1, 1);
		drawCenterd(Consts.GAME_NAME.toUpperCase(), 10, 0.1f);
		drawCenterd("BY", 8, 0.08f);
		drawCenterd("MATTEO SIGNER", -8, 0.1f);
		drawCenterd("BENJAMIN SCHMID", -10, 0.1f);
		drawCenterd("PHILIPP WALLIMANN", -12, 0.1f);

		spriteBatch.end();
	}

	private void drawCenterd(String text, float y, float scale){
		float width = text.length() * Font.FONT1.getGlyphWidth() * scale;
		Font.FONT1.draw(spriteBatch, text, -width / 2, y, scale);
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
		Consts.WIDTH = Consts.HEIGHT * width / ((float) height);
		camera = new OrthographicCamera(Consts.WIDTH, Consts.HEIGHT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
