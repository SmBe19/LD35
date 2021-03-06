package com.smeanox.games.ld35.world;

import com.smeanox.games.ld35.Consts;

public enum HeroForm {
	human (Consts.HERO_MAX_VELO, Consts.HERO_IMPULSE_X, Consts.HERO_IMPULSE_Y, Consts.LETHAL_IMPULSE, 0, 0, Consts.HERO_WIDTH, Consts.HERO_HEIGHT, 0, 0, 2, 2),
	turtle (Consts.HERO_MAX_VELO * 0.5f, Consts.HERO_IMPULSE_X * 0.5f, Consts.HERO_IMPULSE_Y * 0.3f, Consts.LETHAL_IMPULSE * 5f, 0, -Consts.HERO_HEIGHT * 0.375f, Consts.HERO_WIDTH * 2, Consts.HERO_HEIGHT * 0.25f, 0, 0, 2, 2),
	wolf (Consts.HERO_MAX_VELO * 1.75f, Consts.HERO_IMPULSE_X, Consts.HERO_IMPULSE_Y * 1.4f, Consts.LETHAL_IMPULSE, 0, -Consts.HERO_HEIGHT * 0.25f, Consts.HERO_WIDTH * 2, Consts.HERO_HEIGHT * 0.5f, 0, 0, 2, 2),
	;

	float maxVelo, impulseX, impulseY;
	float lethalImpulse;
	float x, y, width, height;
	float texX, texY, texWidth, texHeight;

	HeroForm(float maxVelo, float impulseX, float impulseY, float lethalImpulse, float x, float y, float width, float height, float texX, float texY, float texWidth, float texHeight) {
		this.maxVelo = maxVelo;
		this.impulseX = impulseX;
		this.impulseY = impulseY;
		this.lethalImpulse = lethalImpulse;
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

	public float getLethalImpulse() {
		return lethalImpulse;
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
