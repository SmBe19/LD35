package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.screens.Renderable;
import com.smeanox.games.ld35.io.Textures;

public class Ball extends Actor {

	private Animation animation;
	private float animationTime;
	private float vx, vy;
	private boolean frozen;

	public Ball(int id, float x, float y, float vx, float vy, float radius, boolean frozen) {
		super(id, x, y, 2*radius, 2*radius, null, true, true);
		this.vx = vx;
		this.vy = vy;
		this.frozen = false;
		animation = Hero.createAnimation(Textures.spritesheet.get(), 7, 8, 0, 2, 32, 32, 0.2f, Animation.PlayMode.LOOP);
		animationTime = 0;
	}

	public float getVx() {
		return vx;
	}

	public float getVy() {
		return vy;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
		body.setActive(!frozen);
	}

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = false;
		bodyDef.active = !frozen;

		body = world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setRadius(width/2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 500f * Consts.DEFAULT_DENSITY;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.2f;

		Fixture fixture = body.createFixture(fixtureDef);
		shape.dispose();

		body.setUserData(this);

		body.setLinearVelocity(vx, vy);
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		animationTime += delta * Math.abs(getBody().getAngularVelocity());
		spriteBatch.draw(animation.getKeyFrame(animationTime),
				getBody().getPosition().x - width / 2,
				getBody().getPosition().y - height / 2,
				width / 2,
				height / 2,
				2*getBody().getFixtureList().get(0).getShape().getRadius(), 2*getBody().getFixtureList().get(0).getShape().getRadius(),
				1, 1, 0*getBody().getAngle() * MathUtils.radiansToDegrees);
		// prevent rotation
	}


}
