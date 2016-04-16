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
	Camera camera;
	List<Renderable> renderables;

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

	private void initDebugWorld() {
		Hero hero = new Hero(0, 5, HeroForm.human);
		List<Actor> actors = new ArrayList<Actor>();
		List<Button> buttons = new ArrayList<Button>();
		List<Platform> platforms = new ArrayList<Platform>();

		platforms.add(new Platform(1, -12, -1, 10, 0.5f, -12, -1, -18, -1, true, 2, 1));
		platforms.add(new Platform(2, 0, 0, 10, 0.5f, 0, 0, 0, -1, false, 4f, 0.001f));
		platforms.add(new Platform(3, 12, -1, 10, 0.5f, 12, -1, 18, 1, true, 2, 1));

		actors.add(new Actor(1, 2, 5, 1, 2));
		actors.add(new Actor(2, 3, 5, 1, 2));
		actors.add(new Actor(3, 4, 5, 1, 2));

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		buttons.add(new Button(1, -5, 5, 1, 1, ids, new ArrayList<Integer>()));
		ids.clear();
		ids.add(2);
		buttons.add(new Button(2, -15, 0, 1, 1, new ArrayList<Integer>(), ids));

		gameWorld = new GameWorld("Debug", actors, buttons, platforms, hero);
	}

	private void addGameWorldObjectsToRenderables() {
		renderables = new ArrayList<Renderable>();
		addGameWorldObjectsToRenderables(gameWorld.getPlatforms());
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
		if (Gdx.input.isKeyPressed(Consts.KEY_LEFT)) {
			if(body.getLinearVelocity().x > -gameWorld.getHero().getCurrentForm().getMaxVelo()) {
				body.applyLinearImpulse(new Vector2(-impulseX, 0), body.getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_RIGHT)) {
			if(body.getLinearVelocity().x < gameWorld.getHero().getCurrentForm().getMaxVelo()) {
				body.applyLinearImpulse(new Vector2(impulseX, 0), body.getWorldCenter(), true);
			}
		}
		if (Gdx.input.isKeyPressed(Consts.KEY_JUMP)) {
			if(gameWorld.getHero().isOnGround() && Math.abs(body.getLinearVelocity().y) < Consts.HERO_JUMP_MAX_VELO_Y) {
				body.applyLinearImpulse(new Vector2(0, gameWorld.getHero().getCurrentForm().getImpulseY()), body.getWorldCenter(), true);
			}
		}
		if(!Gdx.input.isKeyPressed(Consts.KEY_LEFT) && !Gdx.input.isKeyPressed(Consts.KEY_RIGHT) && gameWorld.getHero().isOnGround()){
			body.applyForceToCenter(-body.getLinearVelocity().x * Consts.HERO_DAMPING_X_COEF, 0, true);
		}
		if(Gdx.input.isKeyJustPressed(Consts.KEY_INTERACT)) {
			if (gameWorld.getHero().getLastButton() != null && gameWorld.getHero().getCurrentForm() == HeroForm.human) {
				gameWorld.getHero().getLastButton().interact(gameWorld);
			}
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

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		float width = Consts.HEIGHT * Textures.bg1.get().getWidth() / ((float) Textures.bg1.get().getHeight());
		spriteBatch.draw(Textures.bg1.get(), -width / 2, -Consts.HEIGHT / 2, width, Consts.HEIGHT);

		for (Renderable renderable : renderables) {
			renderable.render(spriteBatch, delta);
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
