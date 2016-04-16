package com.smeanox.games.ld35.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Button implements PhysObject {

	private Body body;
	private float x, y, width, height;

	public Button(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public void interact(){
		System.err.println("interact");
	}

	public void startInteract(){
		System.err.println("start interact");
	}

	public void endInteract(){
		System.err.println("end interact");
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
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
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
}
