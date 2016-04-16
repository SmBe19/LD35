package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smeanox.games.ld35.screens.Renderable;
import com.smeanox.games.ld35.io.Textures;

public class Ball extends Actor {

	private Animation animation;
	private float animationTime;

	public Ball(int id, float x, float y, float radius) {
		super(id, x, y, 2*radius, 2*radius);
		animation = Hero.createAnimation(Textures.spritesheet.get(), 7, 8, 0, 2, 32, 32, 0.2f, Animation.PlayMode.LOOP);
		animationTime = 0;
	}

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = false;

		body = world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setRadius(width/2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 5000f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0f;

		Fixture fixture = body.createFixture(fixtureDef);
		shape.dispose();

		body.setUserData(this);
	}


	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		animationTime += delta * Math.abs(getBody().getAngularVelocity());
		spriteBatch.draw(animation.getKeyFrame(animationTime),
			getBody().getPosition().x - getBody().getFixtureList().get(0).getShape().getRadius(),
			getBody().getPosition().y - getBody().getFixtureList().get(0).getShape().getRadius(),
			2*getBody().getFixtureList().get(0).getShape().getRadius(), 2*getBody().getFixtureList().get(0).getShape().getRadius());
	}


}
