package com.smeanox.games.ld35;

import com.badlogic.gdx.Input;

public class Consts {
	public static float WIDTH = 40;
	public static final float HEIGHT = 30;
	public static final float CAMERA_BORDER = 8;
	public static final float DESKTOP_SCALE = 20;
	public static final boolean USE_DEBUG_RENDERER = true;

	public static final float BG1_DIST = 5;
	public static final float BG1_HEIGHT_PART = 0.66f;
	public static final float BG1_HEIGHT_OFF = 0.33f;
	public static final float BG2_DIST = 3;
	public static final float BG2_HEIGHT_PART = 0.66f;
	public static final float BG2_HEIGHT_OFF = 0f;
	public static final float BG3_DIST = 2;
	public static final float BG3_HEIGHT_PART = 0.66f;
	public static final float BG3_HEIGHT_OFF = 0.33f;

	public static final float PHYS_GRAVITY = -10;
	public static final float PHYS_TIME_STEP = 1/45f;
	public static final int PHYS_VELO_ITERATIONS = 6;
	public static final int PHYS_POS_ITERATIONS = 2;
	public static final int KEY_LEFT = Input.Keys.A;
	public static final int KEY_JUMP = Input.Keys.W;
	public static final int KEY_RIGHT = Input.Keys.D;
	public static final int KEY_INTERACT = Input.Keys.E;
	public static final int KEY_HUMAN = Input.Keys.NUM_1;
	public static final int KEY_TURTLE = Input.Keys.NUM_2;
	public static final int KEY_WOLF = Input.Keys.NUM_3;
	public static final float HERO_WIDTH = 1;
	public static final float HERO_HEIGHT = 2;
	public static final float HERO_MAX_VELO = 5;
	public static final float HERO_IMPULSE_X = 10;
	public static final float HERO_IMPULSE_Y = 50;
	public static final float HERO_DAMPING_X_COEF = 2;
	public static final float HERO_GROUND_SENSOR_HEIGHT = 0.1f;
	public static final float HERO_GROUND_SENSOR_WIDTH = 0.75f;
	public static final float HERO_JUMP_MAX_VELO_Y = 1f;
	public static final float HERO_IMPULSE_AIR_MODIFIER = 0.2f;
	public static final float HERO_IMPULSE_OTHER_DIRECTION_MODIFIER = 5f;
	public static final int TEX_WIDTH_HERO = 32;
	public static final int TEX_HEIGHT_HERO = 32;
	public static final int TEX_WIDTH_PLATFORM = 16;
	public static final int TEX_HEIGHT_PLATFORM = 16;
	public static final float TEX_WAIT_MAX_VELO = 1f;

	public static final float LETHAL_IMPULSE = 500;
}
