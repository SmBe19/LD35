package com.smeanox.games.ld35.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smeanox.games.ld35.Consts;

public class Platform implements PhysObject {

	private Body body;
	private int id;
	private float x, y, width, height;
	private float startX, startY, endX, endY;
	private float movingVelo;
	private float holdTime, aHoldTime;

	public Platform(int id, float x, float y, float width, float height, float startX, float startY, float endX, float endY, float movingVelo, float holdTime) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.movingVelo = movingVelo;
		this.holdTime = holdTime;
		aHoldTime = 0;
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

	public float getMovingVelo() {
		return movingVelo;
	}

	public void setMovingVelo(float movingVelo) {
		this.movingVelo = movingVelo;
	}

	public void update(float delta){
		Vector2 dir = new Vector2(endX - startX, endY - startY).nor();
		if(aHoldTime > 0) {
			dir.set(0, 0);
		}
		body.setLinearVelocity(dir.scl(movingVelo));

		if(aHoldTime > 0){
			aHoldTime -= delta;
			if(aHoldTime < 0){
				aHoldTime = 0;
				movingVelo *= -1;
			}
		} else {
			// FIXME doesn't work if Platform too fast
			if ((Vector2.len(body.getPosition().x - endX, body.getPosition().y - endY) < Consts.PLATFORM_DIR_CHANGE_RADIUS && movingVelo > 0)
					|| (Vector2.len(body.getPosition().x - startX, body.getPosition().y - startY) < Consts.PLATFORM_DIR_CHANGE_RADIUS && movingVelo < 0)) {
				aHoldTime = holdTime;
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
		fixtureDef.density = 0.5f;
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
}
