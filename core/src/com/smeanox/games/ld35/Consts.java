package com.smeanox.games.ld35;

import com.badlogic.gdx.Input;

public class Consts {
	public static float WIDTH = 80;
	public static float HEIGHT = 60;
	public static final float DESKTOP_SCALE = 10;
	public static final boolean USE_DEBUG_RENDERER = true;

	public static final float PHYS_GRAVITY = -10;
	public static final float PHYS_TIME_STEP = 1/45f;
	public static final int PHYS_VELO_ITERATIONS = 6;
	public static final int PHYS_POS_ITERATIONS = 2;
	public static final int KEY_LEFT = Input.Keys.A;
	public static final int KEY_JUMP = Input.Keys.W;
	public static final int KEY_RIGHT = Input.Keys.D;
	public static final int KEY_INTERACT = Input.Keys.E;
	public static final float HERO_MAX_VELO = 5;
	public static final float HERO_IMPULSE_X = 1;
	public static final float HERO_IMPULSE_Y = 10;
	public static final float HERO_DAMPING_X_COEF = 2;
	public static final float HERO_GROUND_SENSOR_HEIGHT = 0.5f;
	public static final float HERO_JUMP_MAX_VELO_Y = 1f;
	public static final float PLATFORM_DIR_CHANGE_RADIUS = 0.1f;
}
