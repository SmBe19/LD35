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
import com.smeanox.games.ld35.screens.Renderable;

public class Actor implements PhysObject, Renderable {

	public enum ActorType{
		ball,
		bear,
		human,
	}

	protected Body body;
	protected int id;
	protected float x, y, width, height;
	protected TextureRegion texture;
	protected boolean collision, dynamicBody;
	protected boolean frozen;
	private int inWater;

	public Actor(int id, float x, float y, float width, float height, TextureRegion texture, boolean collision, boolean dynamicBody, boolean frozen) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.collision = collision;
		this.dynamicBody = dynamicBody;
		this.frozen = frozen;
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

	public TextureRegion getTexture() {
		return texture;
	}

	public boolean isCollision() {
		return collision;
	}

	public boolean isDynamicBody() {
		return dynamicBody;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
		body.setActive(!frozen);
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

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = (collision && dynamicBody) ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		bodyDef.active = !frozen;

		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = Consts.DEFAULT_DENSITY;
		fixtureDef.friction = Consts.DEFAULT_FRICTION;
		fixtureDef.restitution = 0f;
		if(collision) {
			Fixture fixture = body.createFixture(fixtureDef);
		}
		shape.dispose();

		body.setUserData(this);
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		spriteBatch.draw(texture, getBody().getPosition().x - width / 2, getBody().getPosition().y - height / 2, width, height);
	}
}
