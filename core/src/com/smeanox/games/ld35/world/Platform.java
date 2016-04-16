package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.io.Textures;
import com.smeanox.games.ld35.screens.Renderable;

import java.util.ArrayList;
import java.util.List;

public class Platform implements PhysObject, Renderable {

	private Body body;
	private int id;
	private float x, y, width, height;
	private float startX, startY, endX, endY;
	private float movingVelo;
	private boolean movingEnabled;
	private float holdTime, aHoldTime;
	private Vector2 direction;

	private List<TextureRegion> textures;

	public Platform(int id, float x, float y, float width, float height, float startX, float startY, float endX, float endY, boolean movingEnabled, float movingVelo, float holdTime) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.movingEnabled = movingEnabled;
		this.movingVelo = movingVelo;
		this.holdTime = holdTime;
		aHoldTime = 0;
		direction = new Vector2(endX - startX, endY - startY).nor();
		initTextures(0, 6, 12, 13);
	}

	private void initTextures(int startX, int endX, int startY, int endY) {
		List<TextureRegion> possibleTextures = new ArrayList<TextureRegion>();
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				possibleTextures.add(new TextureRegion(Textures.spritesheet.get(), x * Consts.TEX_WIDTH_PLATFORM, y * Consts.TEX_HEIGHT_PLATFORM,
						Consts.TEX_WIDTH_PLATFORM, Consts.TEX_HEIGHT_PLATFORM));
			}
		}
		textures = new ArrayList<TextureRegion>();
		textures.add(possibleTextures.get(0));
		float step = height * Consts.TEX_WIDTH_PLATFORM / ((float) Consts.TEX_HEIGHT_PLATFORM);
		for(float x = step; x < width - step; x += step){
			int rand = (int) ((possibleTextures.size() - 2) * Math.random()) + 1;
			textures.add(possibleTextures.get(rand));
		}
		textures.add(possibleTextures.get(possibleTextures.size() - 1));
	}

	public int getId() {
		return id;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getStartX() {
		return startX;
	}

	public float getStartY() {
		return startY;
	}

	public float getEndX() {
		return endX;
	}

	public float getEndY() {
		return endY;
	}

	public float getHoldTime() {
		return holdTime;
	}

	public boolean isMovingEnabled() {
		return movingEnabled;
	}

	public void setMovingEnabled(boolean movingEnabled) {
		this.movingEnabled = movingEnabled;
	}

	public void toggleMovingEnabled() {
		this.movingEnabled = !this.movingEnabled;
	}

	public float getMovingVelo() {
		return movingVelo;
	}

	public void setMovingVelo(float movingVelo) {
		this.movingVelo = movingVelo;
	}

	public void update(float delta){
		direction.set(endX - startX, endY - startY).nor().scl(movingVelo);
		if(aHoldTime > 0 || !movingEnabled){
			body.setLinearVelocity(0, 0);
		} else {
			body.setLinearVelocity(direction);
		}

		if(aHoldTime > 0){
			aHoldTime -= delta;
			if(aHoldTime < 0){
				aHoldTime = 0;
				movingVelo *= -1;
			}
		} else {
			if(movingVelo > 0){
				if(Vector2.dot(endX - body.getPosition().x, endY - body.getPosition().y, direction.x, direction.y) < 0){
					aHoldTime = holdTime;
					body.setTransform(endX, endY, body.getAngle());
				}
			} else {
				if(Vector2.dot(startX - body.getPosition().x, startY - body.getPosition().y, direction.x, direction.y) < 0){
					aHoldTime = holdTime;
					body.setTransform(startX, startY, body.getAngle());
				}
			}
		}
	}

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(x, y);

		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0f;
		fixtureDef.friction = 0.8f;
		fixtureDef.restitution = 0f;

		Fixture fixture = body.createFixture(fixtureDef);
		shape.dispose();

		body.setUserData(this);
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		float step = height * Consts.TEX_WIDTH_PLATFORM / ((float) Consts.TEX_HEIGHT_PLATFORM);
		for (int i = 0; i < textures.size(); i++) {
			spriteBatch.draw(textures.get(i), body.getPosition().x - width / 2 + step * i, body.getPosition().y - height / 2, step, height);
		}
	}
}
