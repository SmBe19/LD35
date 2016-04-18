package com.smeanox.games.ld35.world;

import com.smeanox.games.ld35.io.Textures;
import com.smeanox.games.ld35.screens.Renderable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Deco implements Renderable {

	private float x;
	private float y;
	private float scale;

	private TextureRegion texture;

	public Deco (float x, float y, float scale, int index) {
		this.x = x;
		this.y = y;
		
		this.scale = scale;
		this.texture = new TextureRegion(Textures.spritesheet.get(), index*32, 12*32, 32, 32);
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		batch.draw(texture, x-scale/2, y-scale/2, scale, scale);
	}
}
