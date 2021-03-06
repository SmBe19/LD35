package com.smeanox.games.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.Font;
import com.smeanox.games.ld35.LD35;
import com.smeanox.games.ld35.Sounds;
import com.smeanox.games.ld35.io.LevelReader;
import com.smeanox.games.ld35.io.Textures;
import com.smeanox.games.ld35.world.GameWorld;
import com.smeanox.games.ld35.world.HeroForm;
import com.smeanox.games.ld35.world.narrator.Narrator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

	private LD35 game;
	private Narrator narrator;
	private GameWorld gameWorld;
	private SpriteBatch spriteBatch;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private List<Renderable> renderables;
	private ShaderProgram waterShader;
	private float cameraX;

	private TextureRegion overlayRegion;

	private String loadedLevel;
	private float jumpFreezeTime;
	private float waveTime;

	public GameScreen(LD35 game) {
		this.game = game;
		narrator = new Narrator();

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera(Consts.WIDTH, Consts.HEIGHT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		waterShader = new ShaderProgram(
				"attribute vec4 a_position;\n" +
				"attribute vec2 a_texCoord0;\n" +
				"uniform mat4 u_projTrans;\n" +
				"varying vec2 v_texCoord;\n" +
				"void main() {\n" +
				"    v_texCoord = a_texCoord0;\n" +
				"    gl_Position = u_projTrans * a_position;\n" +
				"}\n",

				"#ifdef GL_ES\n"+
				"    precision mediump float;\n"+
				"#endif\n"+
				"varying vec2 v_texCoord;\n"+
				"uniform sampler2D u_texture;\n"+
				"uniform sampler2D u_texture2;\n"+
				"uniform float u_mult;\n"+
				"uniform vec2 u_offset;\n"+
				"uniform float u_time;\n"+
				"vec4 text2D(sampler2D t, vec2 tc) {\n"+
				"    vec4 col = texture2D(t,tc);\n"+
				"    return col.a > 0.5 ? col : vec4(0.388, 0.607, 1.0, 1.0);\n"+
				" }\n"+
				"vec4 gaussSamp(sampler2D t, vec2 tc, vec2 d) {\n"+
				"    return 0.4*text2D(t,tc)+0.24*text2D(t,tc+d)+0.24*text2D(t,tc-d)+0.06*text2D(t,tc-d-d)+0.06*text2D(t,tc+d+d);\n"+
				"}\n"+
				"void main() {\n"+
				"    vec4 col = texture2D(u_texture, v_texCoord);\n"+
				"    if (distance(col.rgb, vec3(1.0, 0.0, 1.0)) < 0.1){\n"+
				"        float z = 1.0/v_texCoord.y;\n"+
				"        vec2 tc = vec2(v_texCoord.x+0.007*cos(70.0*v_texCoord.x ), 1.0-1.3*v_texCoord.y+0.005*sin(300.0*z + u_time));\n"+
				"        gl_FragColor = gaussSamp(u_texture2, tc + u_offset, vec2(1.0/256.0, 0.0));\n"+
				"        gl_FragColor.xyz *= 0.8;\n"+
				"    } else {\n"+
				"        gl_FragColor = col;\n"+
				"    }\n"+
				"}\n"
				);
		Textures.bg1.get().setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		Textures.sky.get().setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		System.out.println(waterShader.getLog());
		ShaderProgram.pedantic = false;
		debugRenderer = new Box2DDebugRenderer();
		overlayRegion = new TextureRegion(Textures.spritesheet.get(), 3*32, 7*32, 32, 32);

		loadGameWorld(narrator.getCurrentFile());
	}

	private boolean loadGameWorld(String file) {
		try {
			gameWorld = LevelReader.readLevel(Gdx.files.internal(file));
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.exit();
			return false;
		}
		loadedLevel = file;
		cameraX = gameWorld.getCameraInfo().getStartX();
		camera.position.x = gameWorld.getCameraInfo().getStartX();
		camera.update();
		addGameWorldObjectsToRenderables();
		narrator.loadedLevel(file);
		return true;
	}

	private void addGameWorldObjectsToRenderables() {
		renderables = new ArrayList<Renderable>();
		addGameWorldObjectsToRenderables(gameWorld.getWaters());
		addGameWorldObjectsToRenderables(gameWorld.getButtons());
		addGameWorldObjectsToRenderables(gameWorld.getPlatforms());
		addGameWorldObjectsToRenderables(gameWorld.getLadders());
		addGameWorldObjectsToRenderables(gameWorld.getActors());
		addGameWorldObjectsToRenderables(gameWorld.getTexts());
		addGameWorldObjectsToRenderables(gameWorld.getDecos());
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
		game.getMusicManager().update(delta);
		updateInput(delta);
		update(delta);
		renderWorld(delta);
	}

	private void updateInput(float delta) {
		if(Gdx.input.isKeyJustPressed(Consts.KEY_SKIP)){
			narrator.skip(gameWorld);
		}
		if(Gdx.input.isKeyJustPressed(Consts.KEY_BACK_TO_MENU)){
			game.showMenuScreen();
			return;
		}
		if(narrator.isHeroFrozen()){
			return;
		}
		if (Gdx.input.isKeyJustPressed(Consts.KEY_RESTART)) {
			loadGameWorld(loadedLevel);
		}
		if(gameWorld.isGameLost()){
			return;
		}
		jumpFreezeTime -= delta;
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
		if(!gameWorld.getHero().isTurtleActive()) {
			if(!gameWorld.getHero().isOnWallLeft()) {
				if (Gdx.input.isKeyPressed(Consts.KEY_LEFT)) {
					if (body.getLinearVelocity().x > -maxVeloX) {
						body.applyLinearImpulse(new Vector2(-impulseX, 0), body.getWorldCenter(), true);
					}
				}
			}
			if(!gameWorld.getHero().isOnWallRight()){
				if (Gdx.input.isKeyPressed(Consts.KEY_RIGHT)) {
					if (body.getLinearVelocity().x < maxVeloX) {
						body.applyLinearImpulse(new Vector2(impulseX, 0), body.getWorldCenter(), true);
					}
				}
			}
			if (Gdx.input.isKeyPressed(Consts.KEY_JUMP)) {
				if (gameWorld.getHero().isOnGround() && jumpFreezeTime < 0) {
					body.applyLinearImpulse(new Vector2(0, gameWorld.getHero().getCurrentForm().getImpulseY()), body.getWorldCenter(), true);
					jumpFreezeTime = Consts.HERO_JUMP_FREEZE_TIME;
				}
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
		if(gameWorld.getHero().isInWater() && !gameWorld.getHero().isTurtleActive()){
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
				Sounds.BUTTON.play();
				gameWorld.getHero().getLastButton().interact(gameWorld);
			}
			if(gameWorld.getHero().getCurrentForm() == HeroForm.turtle){
				gameWorld.getHero().toggleTurtleActive();
			}
		}
	}

	private void update(float delta) {
		gameWorld.update(delta);
		narrator.update(delta, gameWorld);
		if(narrator.isRollCredits()){
			game.showCreditsScreen();
		} else if(narrator.isNeedLevelReload()) {
			loadGameWorld(narrator.getCurrentFile());
		}
	}

	private void renderWorld(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (gameWorld.getHero().getBody().getPosition().x > cameraX + Consts.WIDTH / 2 - Consts.CAMERA_BORDER) {
			cameraX = gameWorld.getHero().getBody().getPosition().x - Consts.WIDTH / 2 + Consts.CAMERA_BORDER;
			cameraX = Math.min(gameWorld.getCameraInfo().getMaxX(), cameraX);
			camera.position.x = cameraX;
			camera.update();
		}
		if (gameWorld.getHero().getBody().getPosition().x < cameraX - Consts.WIDTH / 2 + Consts.CAMERA_BORDER) {
			cameraX = gameWorld.getHero().getBody().getPosition().x + Consts.WIDTH / 2 - Consts.CAMERA_BORDER;
			cameraX = Math.max(gameWorld.getCameraInfo().getMinX(), cameraX);
			camera.position.x = cameraX;
			camera.update();
		}

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		// sky
		float width = Consts.HEIGHT* Textures.sky.get().getWidth() / ((float) Textures.sky.get().getHeight());
		for (int i = -10; i < 11; i++) {
			spriteBatch.draw(Textures.sky.get(), -width / 2 + i * width + camera.position.x, -Consts.HEIGHT / 2, width, Consts.HEIGHT);
		}

		// bg1
		width = Consts.HEIGHT * Consts.BG1_HEIGHT_PART * Textures.bg1.get().getWidth() / ((float) Textures.bg1.get().getHeight());
		float off = camera.position.x - camera.position.x / Consts.BG1_DIST;
		for(int i = -10; i < 11; i++) {
			spriteBatch.draw(Textures.bg1.get(), -width / 2 + off + i * width, -Consts.HEIGHT / 2 + Consts.HEIGHT * Consts.BG1_HEIGHT_OFF,
					width, Consts.HEIGHT * Consts.BG1_HEIGHT_PART);
		}

		spriteBatch.end();
		spriteBatch.setShader(waterShader);
		Textures.bg1.get().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		spriteBatch.begin();

		Textures.bg1.get().bind(1);
		waveTime += delta;
		waterShader.setUniformi("u_texture2", 1);
		waterShader.setUniformf("u_mult", 1.0f/100f);
		waterShader.setUniformf("u_time", waveTime);
		waterShader.setUniformf("u_offset", -(cameraX/width)*(1.0f/Consts.BG2_DIST - 1.0f/Consts.BG1_DIST), 0.3f);
		Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);

		// bg2
		width = Consts.HEIGHT * Consts.BG2_HEIGHT_PART * Textures.bg2.get().getWidth() / ((float) Textures.bg2.get().getHeight());
		off = camera.position.x - camera.position.x / Consts.BG2_DIST;
		for(int i = -10; i < 11; i++) {
			spriteBatch.draw(Textures.bg2.get(), -width / 2 + off + i * width, -Consts.HEIGHT / 2 + Consts.HEIGHT * Consts.BG2_HEIGHT_OFF,
					width, Consts.HEIGHT * Consts.BG2_HEIGHT_PART);
		}

		spriteBatch.end();
		Textures.bg1.get().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		spriteBatch.setShader(null);
		spriteBatch.begin();

		for (Renderable renderable : renderables) {
			renderable.render(spriteBatch, gameWorld.isGameLost() ? 0 : delta);
		}

		if(gameWorld.isGameWon()) {
			spriteBatch.setColor(Color.RED);
			Font.FONT1.drawBordered(spriteBatch, "LEVEL FINISHED", camera.position.x - 15, 5, 0.25f);
			spriteBatch.setColor(Color.WHITE);
		} else if(gameWorld.isGameLost()){
			spriteBatch.setColor(1, 0, 0, 0.3f);
			spriteBatch.draw(overlayRegion, camera.position.x - Consts.WIDTH/2, -Consts.HEIGHT/2, Consts.WIDTH, Consts.HEIGHT);
			spriteBatch.setColor(Color.RED);
			Font.FONT1.drawBordered(spriteBatch, "YOU DIED", camera.position.x - 15, 5, 0.5f);
			spriteBatch.setColor(Color.WHITE);
		}

		narrator.setCameraX(cameraX);
		narrator.drawCurrentSubtitles(spriteBatch);
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
