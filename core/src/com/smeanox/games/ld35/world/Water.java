package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.Texture;
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

public class Water implements PhysObject, Renderable {

	private Body body;
	private int id;
	private float x, y, width, height;
	private List<TextureRegion> textures;

	private float[] anim;

	public Water(int id, float x, float y, float width, float height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		textures = new ArrayList<TextureRegion>();
		initTextures(8,14, 11, 12);

		this.anim = new float[1+(int)(width/2)];
		for (int i = 0; i < anim.length; i++) {
			anim[i] = (float)Math.random()*(textures.size()-1);
		}

	}

	private void initTextures(int startX, int endX, int startY, int endY) {
		textures = new ArrayList<TextureRegion>();
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				textures.add(new TextureRegion(Textures.spritesheet.get(), x * Consts.TEX_WIDTH_WATER, y * Consts.TEX_HEIGHT_WATER,
						Consts.TEX_WIDTH_WATER, Consts.TEX_HEIGHT_WATER));
			}
		}
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

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
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
		for (int ix = 0; ix < width; ix+=2) {
			int animIx = 1+((int)anim[ix/2]) % (textures.size()-1);
			anim[ix/2] += 5.0*delta;
			spriteBatch.draw(textures.get(animIx),  x-width/2.0f+ix,y+height/2.0f-1.0f, 2.0f, 2.0f);
		}

		for (int iy = 0; iy < height; iy+=2) {
			for (int ix = 0; ix < width; ix+=2) {
			//	System.out.printf("%f %f %f %f\n", y+height/2.0f-iy-2, x-width/2.0f+ix, 2.0f, 2.0f);
				spriteBatch.draw(textures.get(0), x-width/2.0f + ix, y+height/2.0f-iy-2, 2.0f, 2.0f);
			}
		}
	}
}
