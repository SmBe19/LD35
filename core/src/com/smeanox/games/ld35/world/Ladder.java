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

public class Ladder implements PhysObject, Renderable {

	private Body body;
	private int id;
	private float x, width, startY, endY;
	private List<TextureRegion> textures;

	public Ladder(int id, float x, float width, float startY, float endY) {
		this.id = id;
		this.x = x;
		this.width = width;
		this.startY = startY;
		this.endY = endY;
		initTextures(0, 3, 8, 9);
	}

	private void initTextures(int startX, int endX, int startY, int endY) {
		textures = new ArrayList<TextureRegion>();
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				textures.add(new TextureRegion(Textures.spritesheet.get(), x * Consts.TEX_WIDTH_LADDER, y * Consts.TEX_HEIGHT_LADDER,
						Consts.TEX_WIDTH_LADDER, Consts.TEX_HEIGHT_LADDER));
			}
		}
	}

	public int getId() {
		return id;
	}

	public float getX() {
		return x;
	}

	public float getWidth() {
		return width;
	}

	public float getStartY() {
		return startY;
	}

	public float getEndY() {
		return endY;
	}

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x, (startY + endY) / 2);
		bodyDef.fixedRotation = true;

		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, Math.abs((endY - startY) / 2));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0f;
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

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		float step = width * Consts.TEX_HEIGHT_LADDER / ((float) Consts.TEX_WIDTH_LADDER);
		spriteBatch.draw(textures.get(2), x - width / 2, startY, width, step);
		for (float y = startY + step; y < endY - step; y += step) {
			spriteBatch.draw(textures.get(1), x - width / 2, y, width, step);
		}
		spriteBatch.draw(textures.get(0), x - width / 2, endY - step, width, step);
	}
}
