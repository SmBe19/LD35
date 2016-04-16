package com.smeanox.games.ld35.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smeanox.games.ld35.Consts;

public class Hero implements PhysObject {

	private Body body;
	private float x, y, width, height;
	private int onGround;
	private Button lastButton;

	public Hero(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		onGround = 0;
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

	public Button getLastButton() {
		return lastButton;
	}

	public void setLastButton(Button lastButton) {
		this.lastButton = lastButton;
	}

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.8f;
		fixtureDef.restitution = 0f;

		Fixture fixture = body.createFixture(fixtureDef);

		shape.setAsBox(width / 2, Consts.HERO_GROUND_SENSOR_HEIGHT / 2, new Vector2(0, -(height + Consts.HERO_GROUND_SENSOR_HEIGHT) / 2), 0);
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);

		shape.dispose();

		body.setUserData(this);
	}

	@Override
	public Body getBody() {
		return body;
	}
}
