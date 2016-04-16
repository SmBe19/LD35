package com.smeanox.games.ld35.world;

import com.smeanox.games.ld35.Consts;

public enum HeroForm {
	human (Consts.HERO_MAX_VELO, Consts.HERO_IMPULSE_X, Consts.HERO_IMPULSE_Y, 0, 0, Consts.HERO_WIDTH, Consts.HERO_HEIGHT, 0, 0, 2, 2),
	turtle (Consts.HERO_MAX_VELO, Consts.HERO_IMPULSE_X, 0, 0, -Consts.HERO_HEIGHT / 4, Consts.HERO_WIDTH, Consts.HERO_HEIGHT / 2, 0, 0, 2, 2),
	wolf (Consts.HERO_MAX_VELO * 2, Consts.HERO_IMPULSE_X, Consts.HERO_IMPULSE_Y * 1.2f, 0, 0, Consts.HERO_WIDTH, Consts.HERO_HEIGHT, 0, 0, 2, 2),
	;

	float maxVelo, impulseX, impulseY;
	float x, y, width, height;
	float texX, texY, texWidth, texHeight;

	HeroForm(float maxVelo, float impulseX, float impulseY, float x, float y, float width, float height, float texX, float texY, float texWidth, float texHeight) {
		this.maxVelo = maxVelo;
		this.impulseX = impulseX;
		this.impulseY = impulseY;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texX = texX;
		this.texY = texY;
		this.texWidth = texWidth;
		this.texHeight = texHeight;
	}

	public float getMaxVelo() {
		return maxVelo;
	}

	public float getImpulseX() {
		return impulseX;
	}

	public float getImpulseY() {
		return impulseY;
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

	public float getTexX() {
		return texX;
	}

	public float getTexY() {
		return texY;
	}

	public float getTexWidth() {
		return texWidth;
	}

	public float getTexHeight() {
		return texHeight;
	}
}
