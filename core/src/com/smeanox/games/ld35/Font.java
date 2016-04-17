package com.smeanox.games.ld35;

import com.smeanox.games.ld35.io.Textures;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public enum Font {
	FONT1(Textures.font.get(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 8, 8, 8);

	private String order;
	private TextureRegion[] regions;

	private int drawWidth;

	Font(Texture tex, String order, int glyphWidth, int glyphHeight, int drawWidth) {
		regions = new TextureRegion[order.length()];
		int i = 0;
fetchLoop:	for (int y = 0; y < tex.getHeight(); y+=glyphHeight) {
			for (int x = 0; x < tex.getWidth(); x += glyphWidth) {
				regions[i++] = new TextureRegion(tex, x, y, glyphWidth, glyphHeight);
				if (i >= regions.length) {
					break fetchLoop;
				}
			}
		}
		this.order = order;
		this.drawWidth = drawWidth;
	}

	public void draw(SpriteBatch b, String text, float x, float y, float scale) {
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int ix = order.indexOf(c);
			if (ix >= 0) {
				b.draw(regions[ix], x + i * drawWidth * scale, y, regions[ix].getRegionWidth() * scale, regions[ix].getRegionHeight() * scale);
			}
		}
	}
}
