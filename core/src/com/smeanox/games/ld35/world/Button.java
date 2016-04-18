package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class Button implements PhysObject, Renderable {

	private Body body;
	private int id;
	private float x, y, width, height;
	private List<Integer> destIdsInteract, destIdsActive;

	private TextureRegion texture;

	public Button(int id, float x, float y, float width, float height, List<Integer> destIdsInteract, List<Integer> destIdsActive) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.destIdsInteract = new ArrayList<Integer>(destIdsInteract);
		this.destIdsActive = new ArrayList<Integer>(destIdsActive);
		this.texture = new TextureRegion(Textures.spritesheet.get(), 3*32, 10*32, 32, 32);
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

	public void interact(GameWorld gameWorld){
		for (int i : destIdsInteract) {
			for (Platform platform : gameWorld.getPlatforms()) {
				if (platform.getId() == i) {
					platform.toggleMovingEnabled();
				}
			}
		}
	}

	public void startInteract(GameWorld gameWorld){
		for (int i : destIdsActive) {
			gameWorld.getPlatformById(i).toggleMovingEnabled();
		}
	}

	public void endInteract(GameWorld gameWorld){
		for (int i : destIdsActive) {
			gameWorld.getPlatformById(i).toggleMovingEnabled();
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
		fixtureDef.friction = Consts.DEFAULT_FRICTION;
		fixtureDef.restitution = 0f;
		fixtureDef.isSensor = true;

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
		spriteBatch.draw(texture, x-width/2, y-height/2, width, height);

	}
}
