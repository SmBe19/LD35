package com.smeanox.games.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.Font;
import com.smeanox.games.ld35.io.LevelReader;
import com.smeanox.games.ld35.io.Textures;
import com.smeanox.games.ld35.world.Actor;
import com.smeanox.games.ld35.world.Button;
import com.smeanox.games.ld35.world.GameWorld;
import com.smeanox.games.ld35.world.Hero;
import com.smeanox.games.ld35.world.HeroForm;
import com.smeanox.games.ld35.world.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

	GameWorld gameWorld;
	SpriteBatch spriteBatch;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camera;
	List<Renderable> renderables;
	float cameraX;

	public GameScreen() {
		if (!loadGameWorld()) return;

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera(Consts.WIDTH, Consts.HEIGHT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		debugRenderer = new Box2DDebugRenderer();
	}

	private boolean loadGameWorld() {
		//initDebugWorld();
		try {
			gameWorld = LevelReader.readLevel(Gdx.files.internal("lvl/lvl1.xml"));
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.exit();
			return false;
		}
		addGameWorldObjectsToRenderables();
		return true;
	}

	private void addGameWorldObjectsToRenderables() {
		renderables = new ArrayList<Renderable>();
		addGameWorldObjectsToRenderables(gameWorld.getWaters());
		addGameWorldObjectsToRenderables(gameWorld.getPlatforms());
		addGameWorldObjectsToRenderables(gameWorld.getLadders());
		addGameWorldObjectsToRenderables(gameWorld.getButtons());
		addGameWorldObjectsToRenderables(gameWorld.getActors());
		renderables.add(gameWorld.getHero());
	}

	private void addGameWorldObjectsToRenderables(List<? extends Renderable> list){
		for (Renderable renderable : list) {
			renderables.add(renderable);
		}
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
		if (Gdx.input.isKeyJustPressed(Consts.KEY_HUMAN)) {
			gameWorld.getHero().setCurrentForm(HeroForm.human);
		}
		if (Gdx.input.isKeyJustPressed(Consts.KEY_TURTLE)) {
			gameWorld.getHero().setCurrentForm(HeroForm.turtle);
		}
		if (Gdx.input.isKeyJustPressed(Consts.KEY_WOLF)) {
			gameWorld.getHero().setCurrentForm(HeroForm.wolf);
		}
		Body body = gameWorld.getHero().getBody();
		float impulseX = gameWorld.getHero().getCurrentForm().getImpulseX();
		if(!gameWorld.getHero().isOnGround()){
			impulseX *= Consts.HERO_IMPULSE_AIR_MODIFIER;
		}
		float maxVeloX = gameWorld.getHero().getCurrentForm().getMaxVelo();
		if (gameWorld.getHero().isInWater()) {
			maxVeloX = Consts.HERO_WATER_MAX_VELO_X;
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_LEFT)) {
			if(body.getLinearVelocity().x > -maxVeloX) {
				body.applyLinearImpulse(new Vector2(-impulseX, 0), body.getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_RIGHT)) {
			if(body.getLinearVelocity().x < maxVeloX) {
				body.applyLinearImpulse(new Vector2(impulseX, 0), body.getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_JUMP)) {
			if(gameWorld.getHero().isOnGround() && Math.abs(body.getLinearVelocity().y) < Consts.HERO_JUMP_MAX_VELO_Y) {
				body.applyLinearImpulse(new Vector2(0, gameWorld.getHero().getCurrentForm().getImpulseY()), body.getWorldCenter(), true);
			}
		}
		if(!gameWorld.getHero().isOnGround() && gameWorld.getHero().isOnLadder() && gameWorld.getHero().getCurrentForm() == HeroForm.human) {
			if (Gdx.input.isKeyPressed(Consts.KEY_UP)) {
				if (body.getLinearVelocity().y < Consts.HERO_LADDER_MAX_VELO_Y) {
					body.applyLinearImpulse(new Vector2(0, Consts.HERO_LADDER_IMPULSE_Y), body.getWorldCenter(), true);
					body.applyForceToCenter(-body.getLinearVelocity().x * Consts.HERO_DAMPING_X_COEF_LADDER, 0, true);
				}
			}
		}
		if(gameWorld.getHero().isInWater()){
			if(Gdx.input.isKeyPressed(Consts.KEY_UP)) {
				if (body.getLinearVelocity().y < Consts.HERO_WATER_MAX_VELO_Y) {
					body.applyLinearImpulse(new Vector2(0, Consts.HERO_WATER_IMPULSE_Y), body.getWorldCenter(), true);
				}
			}
			if(Gdx.input.isKeyPressed(Consts.KEY_DOWN)){
				if (body.getLinearVelocity().y > -Consts.HERO_WATER_MAX_VELO_Y) {
					body.applyLinearImpulse(new Vector2(0, -Consts.HERO_WATER_IMPULSE_Y), body.getWorldCenter(), true);
				}
			}
		}
		if(!Gdx.input.isKeyPressed(Consts.KEY_LEFT) && !Gdx.input.isKeyPressed(Consts.KEY_RIGHT)
				&& (gameWorld.getHero().isOnGround() || gameWorld.getHero().isOnLadder() || gameWorld.getHero().isInWater())){
			body.applyForceToCenter(-body.getLinearVelocity().x * Consts.HERO_DAMPING_X_COEF, 0, true);
		}
		if(Gdx.input.isKeyJustPressed(Consts.KEY_INTERACT)) {
			if (gameWorld.getHero().getLastButton() != null && gameWorld.getHero().getCurrentForm() == HeroForm.human) {
				gameWorld.getHero().getLastButton().interact(gameWorld);
			}
			if(gameWorld.getHero().getCurrentForm() == HeroForm.turtle){
				gameWorld.getHero().toggleTurtleActive();
			}
		}
		if (Gdx.input.isKeyJustPressed(Consts.KEY_RESTART)) {
			gameWorld.setGameLost(true);
		}
	}

	private void update(float delta) {
		gameWorld.update(delta);

		if(gameWorld.isGameLost()){
			loadGameWorld();
		}
	}

	private void renderWorld(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (gameWorld.getHero().getBody().getPosition().x > cameraX + Consts.WIDTH / 2 - Consts.CAMERA_BORDER) {
			cameraX = gameWorld.getHero().getBody().getPosition().x - Consts.WIDTH / 2 + Consts.CAMERA_BORDER;
			camera.position.x = cameraX;
			camera.update();
		}
		if (gameWorld.getHero().getBody().getPosition().x < cameraX - Consts.WIDTH / 2 + Consts.CAMERA_BORDER) {
			cameraX = gameWorld.getHero().getBody().getPosition().x + Consts.WIDTH / 2 - Consts.CAMERA_BORDER;
			camera.position.x = cameraX;
			camera.update();
		}

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		// FIXME Remove me
		Font.FONT1.draw(spriteBatch, gameWorld.getHero().getCurrentForm().name().toUpperCase(), camera.position.x - camera.position.x / 10f, 2);

		// bg1
		float width = Consts.HEIGHT * Consts.BG1_HEIGHT_PART * Textures.bg1.get().getWidth() / ((float) Textures.bg1.get().getHeight());
		float off = camera.position.x - camera.position.x / Consts.BG1_DIST;
		for(int i = -10; i < 11; i++) {
			spriteBatch.draw(Textures.bg1.get(), -width / 2 + off + i * width, -Consts.HEIGHT / 2 + Consts.HEIGHT * Consts.BG1_HEIGHT_OFF,
					width, Consts.HEIGHT * Consts.BG1_HEIGHT_PART);
		}

		// bg2
		width = Consts.HEIGHT * Consts.BG2_HEIGHT_PART * Textures.bg2.get().getWidth() / ((float) Textures.bg2.get().getHeight());
		off = camera.position.x - camera.position.x / Consts.BG2_DIST;
		for(int i = -10; i < 11; i++) {
			spriteBatch.draw(Textures.bg2.get(), -width / 2 + off + i * width, -Consts.HEIGHT / 2 + Consts.HEIGHT * Consts.BG2_HEIGHT_OFF,
					width, Consts.HEIGHT * Consts.BG2_HEIGHT_PART);
		}

		for (Renderable renderable : renderables) {
			renderable.render(spriteBatch, delta);
		}

		if(gameWorld.isGameWon()) {
			spriteBatch.setColor(Color.RED);
			Font.FONT1.draw(spriteBatch, "WON", camera.position.x - 10, -5);
			spriteBatch.setColor(Color.WHITE);
		} else if(gameWorld.isGameLost()){
			Font.FONT1.draw(spriteBatch, "LOST", camera.position.x - 10, -5);
		}

		spriteBatch.end();

		if (Consts.USE_DEBUG_RENDERER) {
			debugRenderer.render(gameWorld.getWorld(), camera.combined);
		}
	}

	@Override
	public void resize(int width, int height) {
		Consts.WIDTH = Consts.HEIGHT * width / ((float) height);
		camera = new OrthographicCamera(Consts.WIDTH, Consts.HEIGHT);
		camera.position.x = cameraX;
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
