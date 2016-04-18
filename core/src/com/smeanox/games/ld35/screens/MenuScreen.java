package com.smeanox.games.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.smeanox.games.ld35.io.Textures;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.Font;
import com.smeanox.games.ld35.LD35;

public class MenuScreen implements Screen {

	private LD35 game;
	private boolean orbTouched = false;
	private float transitionTime = 0;

	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	
	
	private int w, h;

	private TextureRegion orb;
	private TextureRegion shit;
	
	public MenuScreen(LD35 game) {
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
		if (Gdx.input.justTouched()) {
			float x = Consts.WIDTH/w * (Gdx.input.getX()-w/2);
			float y = Consts.HEIGHT/h * (Gdx.input.getY()-h/2);
			if (x * x + y * y < 5*5) {
				orbTouched = true;
			}
			
			if (x < -Consts.WIDTH/2 + "SUBTITLES".length() + 1.f && y > Consts.HEIGHT/2 - 2.f) {
				Consts.SUBTITLES_ENABLED = ! Consts.SUBTITLES_ENABLED;
			}
		}
		if (orbTouched) {
			transitionTime += delta;
			if (transitionTime > 1.0f) {
				transitionTime = 1.0f;
				game.showGameScreen();
			}
		}


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		spriteBatch.begin();
		spriteBatch.setColor(1, 1, 1, 1);
		float orbScale = 1.0f + transitionTime * transitionTime;
		spriteBatch.draw(orb, -5*orbScale, -5*orbScale, 10*orbScale, 10*orbScale);
		if (!orbTouched) {
			if (Consts.SUBTITLES_ENABLED)
				spriteBatch.setColor(1, 1, 1, 1);
			else 
				spriteBatch.setColor(0.3f, 0.3f, 0.3f, 1);

			Font.FONT1.draw(spriteBatch, "SUBTITLES", -Consts.WIDTH/2 + 1.f/8, -Consts.HEIGHT/2 + 1.f/8, 1.0f/8);
		}
		spriteBatch.setColor(1, 1, 1, transitionTime);
		spriteBatch.draw(shit, -Consts.WIDTH/2, -Consts.HEIGHT/2, Consts.WIDTH, Consts.HEIGHT);
		spriteBatch.end();

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
