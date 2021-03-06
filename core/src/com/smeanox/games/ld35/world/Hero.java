package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.io.Textures;
import com.smeanox.games.ld35.screens.Renderable;

import java.util.HashMap;
import java.util.Map;

public class Hero implements PhysObject, Renderable {

	public static final Integer SENSOR_GROUND = 1, SENSOR_WALL_LEFT = 2, SENSOR_WALL_RIGHT = 3;

	private float x, y, destX, destY;
	private int onGround;
	private int onWallLeft, onWallRight;
	private int onLadder;
	private int inWater;
	private Button lastButton;
	private HeroForm currentForm, lastForm;
	private Map<HeroForm, Body> heroFormBodyMap;
	private Map<HeroForm, Boolean> heroFormPossible;
	private boolean turtleActive;

	private Animation activeAnimation, h, hw, hc, t, tw, ta, w, ww, trht, trth, trhw, trwh, trtw, trwt;
	private float animationTime;
	private boolean orientation, isTransforming;

	public Hero(float x, float y, float destX, float destY, HeroForm currentForm) {
		this.x = x;
		this.y = y;
		this.destX = destX;
		this.destY = destY;
		this.currentForm = currentForm;
		onGround = 0;
		onWallLeft = 0;
		onWallRight = 0;
		onLadder = 0;
		inWater = 0;
		turtleActive = false;

		initAnimations();
		animationTime = 0;
		orientation = false;
	}

	private void initAnimations(){
		h = createAnimation(Textures.spritesheet.get(), 0, 1, 0, 8, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.15f, Animation.PlayMode.LOOP);
		hw = createAnimation(Textures.spritesheet.get(), 0, 1, 8, 10, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.2f, Animation.PlayMode.LOOP);
		hc = createAnimation(Textures.spritesheet.get(), 0, 1, 10, 13, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
		t = createAnimation(Textures.spritesheet.get(), 1, 2, 0, 4, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.1f, Animation.PlayMode.LOOP);
		tw = createAnimation(Textures.spritesheet.get(), 1, 2, 0, 1, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.1f, Animation.PlayMode.LOOP);
		ta = createAnimation(Textures.spritesheet.get(), 1, 2, 4, 5, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.1f, Animation.PlayMode.LOOP);
		w = createAnimation(Textures.spritesheet.get(), 2, 3, 0, 4, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.1f, Animation.PlayMode.LOOP);
		ww = createAnimation(Textures.spritesheet.get(), 2, 3, 4, 6, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.2f, Animation.PlayMode.LOOP);
		trht = createAnimation(Textures.spritesheet.get(), 3, 4, 0, 5, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.10f, Animation.PlayMode.NORMAL);
		trth = createAnimation(Textures.spritesheet.get(), 3, 4, 0, 5, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.10f, Animation.PlayMode.REVERSED);
		trhw = createAnimation(Textures.spritesheet.get(), 4, 5, 0, 3, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.10f, Animation.PlayMode.NORMAL);
		trwh = createAnimation(Textures.spritesheet.get(), 4, 5, 0, 3, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.10f, Animation.PlayMode.REVERSED);
		trtw = createAnimation(Textures.spritesheet.get(), 5, 6, 0, 3, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.10f, Animation.PlayMode.REVERSED);
		trwt = createAnimation(Textures.spritesheet.get(), 5, 6, 0, 3, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO, 0.10f, Animation.PlayMode.NORMAL);

		activeAnimation = h;
	}

	public static Animation createAnimation(Texture texture, int startY, int endY, int startX, int endX, int texWidth, int texHeight, float duration, Animation.PlayMode playMode){
		Array<TextureRegion> regions;
		regions = new Array<TextureRegion>();
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				regions.add(new TextureRegion(texture, texWidth * x, texHeight * y, texWidth, texHeight));
			}
		}
		Animation animation = new Animation(duration, regions);
		animation.setPlayMode(playMode);
		return animation;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getDestX() {
		return destX;
	}

	public float getDestY() {
		return destY;
	}

	public boolean reachedDest(){
		return Vector2.len(getBody().getPosition().x - destX, getBody().getPosition().y - destY) < Consts.HERO_DEST_RADIUS;
	}

	public HeroForm getCurrentForm() {
		return currentForm;
	}

	public void setCurrentForm(HeroForm heroForm){
		if(!isHeroFormPossible(heroForm) || currentForm == heroForm || isTransforming || isInWater()){
			return;
		}
		Body currentBody = heroFormBodyMap.get(currentForm);
		Body newBody = heroFormBodyMap.get(heroForm);
		currentBody.setActive(false);
		newBody.setTransform(currentBody.getPosition(), currentBody.getAngle());
		newBody.setLinearVelocity(currentBody.getLinearVelocity());
		newBody.setAngularVelocity(currentBody.getAngularVelocity());
		newBody.setActive(true);
		this.lastForm = this.currentForm;
		this.currentForm = heroForm;
		isTransforming = true;
		animationTime = 0;
		turtleActive = false;
	}

	public boolean isOnGround() {
		return onGround > 0;
	}

	public void setOnGround(boolean onGround) {
		if(onGround){
			this.onGround++;
		} else {
			this.onGround--;
		}
	}

	public boolean isOnWallLeft() {
		return onWallLeft > 0;
	}

	public void setOnWallLeft(boolean onWall) {
		if(onWall){
			this.onWallLeft++;
		} else {
			this.onWallLeft--;
		}
	}

	public boolean isOnWallRight() {
		return onWallRight > 0;
	}

	public void setOnWallRight(boolean onWall) {
		if(onWall){
			this.onWallRight++;
		} else {
			this.onWallRight--;
		}
	}

	public boolean isOnLadder() {
		return onLadder > 0;
	}

	public void setOnLadder(boolean onLadder) {
		if(onLadder){
			this.onLadder++;
		} else {
			this.onLadder--;
		}
	}

	public boolean isInWater() {
		return inWater > 0;
	}

	public void setInWater(boolean inWater) {
		if(inWater){
			this.inWater++;
		} else {
			this.inWater--;
		}
		if(isInWater()){
			getBody().setGravityScale(Consts.HERO_WATER_GRAVITY_SCALE);
			if(this.inWater == 1){
				getBody().setLinearVelocity(getBody().getLinearVelocity().x * Consts.WATER_ENTRY_DAMPING_X, getBody().getLinearVelocity().y * Consts.WATER_ENTRY_DAMPING_Y);
			}
		} else {
			getBody().setGravityScale(1f);
		}
	}

	public Button getLastButton() {
		return lastButton;
	}

	public void setLastButton(Button lastButton) {
		this.lastButton = lastButton;
	}

	public boolean isTurtleActive() {
		return turtleActive;
	}

	public void setTurtleActive(boolean turtleActive) {
		this.turtleActive = turtleActive;
	}

	public void toggleTurtleActive(){
		this.turtleActive = !this.turtleActive;
	}

	public void setHeroFormPossible(HeroForm heroForm, boolean possible) {
		heroFormPossible.put(heroForm, possible);
	}

	public boolean isHeroFormPossible(HeroForm heroForm) {
		return heroFormPossible.get(heroForm);
	}

	@Override
	public void addToWorld(World world) {
		heroFormBodyMap = new HashMap<HeroForm, Body>();
		heroFormPossible = new HashMap<HeroForm, Boolean>();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		Body body;
		Fixture fixture;
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = Consts.DEFAULT_DENSITY;
		fixtureDef.friction = Consts.DEFAULT_FRICTION;
		fixtureDef.restitution = 0f;
		for (HeroForm form : HeroForm.values()) {
			body = world.createBody(bodyDef);
			body.setActive(form == currentForm);
			heroFormBodyMap.put(form, body);
			heroFormPossible.put(form, true);

			shape.setAsBox(form.getWidth() / 2, form.getHeight() / 2, new Vector2(form.getX(), form.getY()), 0);
			fixtureDef.isSensor = false;
			fixtureDef.density = Consts.DEFAULT_DENSITY;
			fixture = body.createFixture(fixtureDef);

			for (HeroForm sensorForm : HeroForm.values()) {
				fixtureDef.isSensor = true;
				fixtureDef.density = 0f;
				shape.setAsBox(sensorForm.getWidth() / 2 * 0.9f, sensorForm.getHeight() / 2 * 0.9f, new Vector2(sensorForm.getX(), sensorForm.getY()), 0);
				fixture = body.createFixture(fixtureDef);
				fixture.setUserData(sensorForm);
			}

			shape.setAsBox(form.getWidth() * Consts.HERO_GROUND_SENSOR_WIDTH / 2, Consts.HERO_GROUND_SENSOR_HEIGHT / 2,
					new Vector2(form.getX(), form.getY() - (form.getHeight() + Consts.HERO_GROUND_SENSOR_HEIGHT) / 2), 0);
			fixtureDef.isSensor = true;
			fixtureDef.density = 0f;
			fixture = body.createFixture(fixtureDef);
			fixture.setUserData(SENSOR_GROUND);

			shape.setAsBox(Consts.HERO_WALL_SENSOR_WIDTH / 2, form.getHeight() * Consts.HERO_WALL_SENSOR_HEIGHT / 2,
					new Vector2(form.getX() - (form.getWidth() + Consts.HERO_WALL_SENSOR_WIDTH) / 2, form.getY()), 0);
			fixture = body.createFixture(fixtureDef);
			fixture.setUserData(SENSOR_WALL_LEFT);

			shape.setAsBox(Consts.HERO_WALL_SENSOR_WIDTH / 2, form.getHeight() * Consts.HERO_WALL_SENSOR_HEIGHT / 2,
					new Vector2(form.getX() + (form.getWidth() + Consts.HERO_WALL_SENSOR_WIDTH) / 2, form.getY()), 0);
			fixture = body.createFixture(fixtureDef);
			fixture.setUserData(SENSOR_WALL_RIGHT);

			body.setUserData(this);
		}

		shape.dispose();
	}

	@Override
	public Body getBody() {
		return heroFormBodyMap.get(currentForm);
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		boolean walking = Math.abs(getBody().getLinearVelocity().x) > Consts.TEX_WAIT_MAX_VELO;
		if(walking){
			orientation = getBody().getLinearVelocity().x < 0;
		}

		if(isTransforming){
			animationTime += delta;
			switch (currentForm){
				case human:
					switch (lastForm){
						case human:
							break;
						case turtle:
							activeAnimation = trth;
							break;
						case wolf:
							activeAnimation = trwh;
							break;
					}
					break;
				case turtle:
					switch (lastForm){
						case human:
							activeAnimation = trht;
							break;
						case turtle:
							break;
						case wolf:
							activeAnimation = trwt;
							break;
					}
					break;
				case wolf:
					switch (lastForm){
						case human:
							activeAnimation = trhw;
							break;
						case turtle:
							activeAnimation = trtw;
							break;
						case wolf:
							break;
					}
					break;
			}
			if(animationTime > activeAnimation.getAnimationDuration()){
				animationTime = 0;
				isTransforming = false;
			}
		}
		if(!isTransforming){
			animationTime += delta;
			switch (currentForm){
				case human:
					activeAnimation = (!isOnGround() && isOnLadder()) ? hc : (walking ? h : hw);
					break;
				case turtle:
					activeAnimation = turtleActive ? ta : (walking ? t : tw);
					break;
				case wolf:
					activeAnimation = walking ? w : ww;
					break;
			}
		}
		TextureRegion keyFrame = activeAnimation.getKeyFrame(animationTime);
		if(keyFrame.isFlipX() != orientation){
			keyFrame.flip(true, false);
		}
		spriteBatch.draw(keyFrame,
				getBody().getPosition().x + currentForm.getTexX() - currentForm.getTexWidth() / 2,
				getBody().getPosition().y + currentForm.getTexY() - currentForm.getTexHeight() / 2,
				currentForm.getTexWidth(), currentForm.getTexHeight());
	}
}
