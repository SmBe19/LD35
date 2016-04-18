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

	private int texWidth;
	private int texHeight;

	public enum PlatformType{
		normal,
		oldschool,
		bridge,
		destr,
		invisible,
	}

	private Body body;
	private int id;
	private float x, y, width, height;
	private List<Vector2> points;
	private PlatformType platformType;
	private float startX, startY, endX, endY;
	private float movingVelo;
	private boolean movingEnabled;
	private float holdTime, aHoldTime;
	private boolean destroying, destroyed;
	private float destroyTimer;
	private Vector2 direction;

	private List<TextureRegion> textures;

	private TextureRegion[][] regions;

	public Platform(int id, float x, float y, float width, float height, List<Vector2> points, PlatformType platformType,
					float startX, float startY, float endX, float endY, boolean movingEnabled, float movingVelo, float holdTime) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.points = new ArrayList<Vector2>(points);
		this.platformType = platformType;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.movingEnabled = movingEnabled;
		this.movingVelo = movingVelo;
		this.holdTime = holdTime;
		this.regions = null;
		aHoldTime = 0;
		direction = new Vector2(endX - startX, endY - startY).nor();
		switch (platformType){
			case normal:
				if(points.size() == 0) {
					this.points.add(new Vector2(-width / 2, -height / 2));
					this.points.add(new Vector2(width / 2, -height / 2));
					this.points.add(new Vector2(width / 2, height / 2));
					this.points.add(new Vector2(-width / 2, height / 2));
				}
				initTextures(0, 10, 22, 24, 16, 16);
				break;
			case oldschool:
				initTextures(0, 6, 12, 13, Consts.TEX_WIDTH_PLATFORM, Consts.TEX_HEIGHT_PLATFORM);
				break;
			case bridge:
				initTextures(0, 3, 9, 10, Consts.TEX_WIDTH_BRIDGE, Consts.TEX_HEIGHT_BRIDGE);
				break;
			case destr:
				initTextures(0, 6, 12, 13, Consts.TEX_WIDTH_PLATFORM, Consts.TEX_HEIGHT_PLATFORM);
				break;
			case invisible:
				break;
		}
	}

	private void initTextures(int startX, int endX, int startY, int endY, int texWidth, int texHeight) {
		this.texWidth = texWidth;
		this.texHeight = texHeight;
		List<TextureRegion> possibleTextures = new ArrayList<TextureRegion>();
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				this.texWidth = texWidth;
				this.texHeight = texHeight;
				possibleTextures.add(new TextureRegion(Textures.spritesheet.get(), x * texWidth, y * texHeight, texWidth, texHeight));
			}
		}
		if (platformType == PlatformType.normal) {
			textures = possibleTextures;
		} else {
			textures = new ArrayList<TextureRegion>();
			textures.add(possibleTextures.get(0));
			float step = height * texWidth / ((float) texHeight);
			for(float x = step; x < width - step; x += step){
				int rand = (int) ((possibleTextures.size() - 2) * Math.random()) + 1;
				textures.add(possibleTextures.get(rand));
			}
			textures.add(possibleTextures.get(possibleTextures.size() - 1));
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

	public PlatformType getPlatformType() {
		return platformType;
	}

	public void update(float delta){
		if(destroyed){
			return;
		}

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

		if(destroying){
			destroyTimer -= delta;
			if(destroyTimer < 0){
				destroyForReal();
			}
		}
	}

	private static boolean testPoint(Fixture[] fixtures, float x, float y) {
		for (Fixture f : fixtures) {
			if (f != null && f.testPoint(x,y))
				return true;
		}
		return false;
	}

	@Override
	public void addToWorld(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(x, y);

		body = world.createBody(bodyDef);

		if(points.size() == 0) {
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width / 2, height / 2);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0f;
			fixtureDef.friction = Consts.DEFAULT_FRICTION;
			fixtureDef.restitution = 0f;
			Fixture fixture = body.createFixture(fixtureDef);
			shape.dispose();
		} else {
			PolygonShape shape = new PolygonShape();
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0f;
			fixtureDef.friction = Consts.DEFAULT_FRICTION;
			fixtureDef.restitution = 0f;
			Fixture[] fixtures = new Fixture[points.size()/4];
			for (int i = 0; i+3 < points.size(); i+= 4) {
				shape.set(points.subList(i, i+4).toArray(new Vector2[4]));
				fixtures[i/4] = body.createFixture(fixtureDef);
			}
			if (platformType == PlatformType.normal) {
				float width = 2.0f*(float)Math.floor(this.width / 2.0f);
				float height = 2.0f*(float)Math.floor(this.height / 2.0f);
				regions = new TextureRegion[2*(1+(int)Math.ceil(height/2.0f))][2*(1+(int)Math.ceil(width/2.0f))];
				int iyi = 0;
				for ( float iy = y - height/2; iy <= y + height/2; iy += 2, iyi++) {
					int ixi = 0;
					for (float ix = x - width/2; ix <= x + width/2; ix += 2, ixi++) {
						boolean center = testPoint(fixtures, ix, iy);
						if (center) {
							boolean l = !testPoint(fixtures, ix-2, iy);
							boolean r = !testPoint(fixtures, ix+2, iy);
							boolean t = !testPoint(fixtures, ix, iy+2);
							boolean b = !testPoint(fixtures, ix, iy-2);

							if (l) {
								regions[2*iyi+1][2*ixi+0] = textures.get(t?0:6);
								regions[2*iyi+0][2*ixi+0] = textures.get(b?10:16);
							} else {
								if (t) {
									regions[2*iyi+1][2*ixi+0] = textures.get(8);
								} else {
									boolean tl = !testPoint(fixtures, ix-2, iy+2);
									regions[2*iyi+1][2*ixi+0] = textures.get(tl?4:2);
								}
								if (b) {
									regions[2*iyi+0][2*ixi+0] = textures.get(18);
								} else {
									boolean bl = !testPoint(fixtures, ix-2, iy-2);
									regions[2*iyi+0][2*ixi+0] = textures.get(bl?14:12);

								}
							}
							if (r) {
								regions[2*iyi+1][2*ixi+1] = textures.get(t?1:7);
								regions[2*iyi+0][2*ixi+1] = textures.get(b?11:17);
							} else {
								if (t) {
									regions[2*iyi+1][2*ixi+1] = textures.get(9);
								} else {
									boolean tr = !testPoint(fixtures, ix+2, iy+2);
									regions[2*iyi+1][2*ixi+1] = textures.get(tr?5:3);
								}
								if (b) {
									regions[2*iyi+0][2*ixi+1] = textures.get(19);
								} else {
									boolean br = !testPoint(fixtures,ix+2, iy-2);
									regions[2*iyi+0][2*ixi+1] = textures.get(br?15:13);

								}
							}
							//System.out.printf("%d/%d: %d\n", 2*iyi+0,2*ixi+0, textures.indexOf(regions[2*iyi+0][2*ixi+0]));
							//System.out.printf("%d/%d: %d\n", 2*iyi+0,2*ixi+1, textures.indexOf(regions[2*iyi+0][2*ixi+1]));
							//System.out.printf("%d/%d: %d\n", 2*iyi+1,2*ixi+0, textures.indexOf(regions[2*iyi+1][2*ixi+0]));
							//System.out.printf("%d/%d: %d\n", 2*iyi+1,2*ixi+1, textures.indexOf(regions[2*iyi+1][2*ixi+1]));
						}
					}
				}
			}
			shape.dispose();
		}






		body.setUserData(this);
	}

	public void destroy(){
		switch (platformType){
			case normal:
			case oldschool:
			case invisible:
				break;
			case bridge:
				destroying = true;
				destroyTimer = 0;
				break;
			case destr:
				destroying = true;
				destroyTimer = Consts.DESTR_TIMEOUT;
				break;
		}
	}

	private void destroyForReal(){
		destroyed = true;
		getBody().getWorld().destroyBody(getBody());
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		if(destroyed || platformType == PlatformType.invisible){
			return;
		}

		if (platformType == PlatformType.normal && points.size() > 0) {
			if (regions == null) return;

			float width = 2.0f*(float)Math.floor(this.width / 2.0f);
			float height = 2.0f*(float)Math.floor(this.height / 2.0f);


			for (int iy = 0; iy < regions.length; iy++) {
				for (int ix = 0; ix < regions[iy].length; ix++) {
					if (regions[iy][ix] != null) {
						spriteBatch.draw(regions[iy][ix], body.getPosition().x - width/2.0f + ix - 1.0f, body.getPosition().y - height /2.0f + iy - 1.0f, 1.0f, 1.0f);
					}
				}
			}
		} else {
			float step = height * texWidth / ((float)texHeight);
			for (int i = 0; i < textures.size(); i++) {
				spriteBatch.draw(textures.get(i), body.getPosition().x - width/2 + step*i, body.getPosition().y - height/2, step, height);
			}

		}
	}
}
