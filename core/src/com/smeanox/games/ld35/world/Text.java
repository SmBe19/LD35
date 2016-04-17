package com.smeanox.games.ld35.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smeanox.games.ld35.Font;
import com.smeanox.games.ld35.screens.Renderable;

public class Text implements Renderable {
	private float x, y, scale;
	private String text;
	private Color color;

	public Text(float x, float y, float scale, String text, Color color) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.text = text.toUpperCase();
		this.color = color;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getScale() {
		return scale;
	}

	public String getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(color);
		Font.FONT1.draw(spriteBatch, text, x, y, scale);
		spriteBatch.setColor(oldColor);
	}
}
