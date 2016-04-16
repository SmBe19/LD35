package com.smeanox.games.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.Actor;
import com.smeanox.games.ld35.world.Button;
import com.smeanox.games.ld35.world.GameWorld;
import com.smeanox.games.ld35.world.Hero;
import com.smeanox.games.ld35.world.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

	GameWorld gameWorld;
	SpriteBatch spriteBatch;
	Box2DDebugRenderer debugRenderer;
	Camera camera;

	public GameScreen() {
		initDebugWorld();
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera(Consts.WIDTH, Consts.HEIGHT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		debugRenderer = new Box2DDebugRenderer();
	}

	private void initDebugWorld() {
		Hero hero = new Hero(0, 5, 1, 2);
		List<Actor> actors = new ArrayList<Actor>();
		List<Button> buttons = new ArrayList<Button>();
		List<Platform> platforms = new ArrayList<Platform>();

		platforms.add(new Platform(-12, -1, 10, 0.5f, -12, -1, -18, -1, 2, 1));
		platforms.add(new Platform(0, 0, 10, 0.5f, 0, 0, 0, -1, 0, 0.001f));
		platforms.add(new Platform(12, -1, 10, 0.5f, 12, -1, 18, 1, 2, 1));

		actors.add(new Actor(2, 5, 1, 2));
		actors.add(new Actor(3, 5, 1, 2));
		actors.add(new Actor(4, 5, 1, 2));

		buttons.add(new Button(-5, 5, 1, 1));
		buttons.add(new Button(-15, 0, 1, 1));

		gameWorld = new GameWorld(actors, buttons, platforms, hero);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		updateInput(delta);
		update(delta);
		renderWorld(delta);
	}

	private void updateInput(float delta) {
		Body body = gameWorld.getHero().getBody();
		if (Gdx.input.isKeyPressed(Consts.KEY_LEFT)) {
			if(body.getLinearVelocity().x > -Consts.HERO_MAX_VELO) {
				body.applyLinearImpulse(new Vector2(-Consts.HERO_IMPULSE_X, 0), body.getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_RIGHT)) {
			if(body.getLinearVelocity().x < Consts.HERO_MAX_VELO) {
				body.applyLinearImpulse(new Vector2(Consts.HERO_IMPULSE_X, 0), body.getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_JUMP)) {
			if(gameWorld.getHero().isOnGround() && Math.abs(body.getLinearVelocity().y) < Consts.HERO_JUMP_MAX_VELO_Y) {
				body.applyLinearImpulse(new Vector2(0, Consts.HERO_IMPULSE_Y), body.getWorldCenter(), true);
			}
		}
		if(!Gdx.input.isKeyPressed(Consts.KEY_LEFT) && !Gdx.input.isKeyPressed(Consts.KEY_RIGHT)){
			body.applyForceToCenter(-body.getLinearVelocity().x * Consts.HERO_DAMPING_X_COEF, 0, true);
		}
		if(Gdx.input.isKeyJustPressed(Consts.KEY_INTERACT)) {
			if (gameWorld.getHero().getLastButton() != null) {
				gameWorld.getHero().getLastButton().interact();
			}
		}
	}

	private void update(float delta) {
		gameWorld.update(delta);
	}

	private void renderWorld(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Consts.USE_DEBUG_RENDERER) {
			debugRenderer.render(gameWorld.getWorld(), camera.combined);
		}
	}

	@Override
	public void resize(int width, int height) {
		Consts.HEIGHT = Consts.WIDTH * height / ((float) width);
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
