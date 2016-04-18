package com.smeanox.games.ld35.world;

public class CameraInfo {
	private float startX, minX, maxX;

	public CameraInfo(float startX, float minX, float maxX) {
		this.startX = startX;
		this.minX = minX;
		this.maxX = maxX;
	}

	public float getStartX() {
		return startX;
	}

	public float getMinX() {
		return minX;
	}

	public float getMaxX() {
		return maxX;
	}
}
