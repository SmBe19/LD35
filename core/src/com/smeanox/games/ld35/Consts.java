package com.smeanox.games.ld35;

import com.badlogic.gdx.Input;

public class Consts {
	public static float WIDTH = 40;
	public static final float HEIGHT = 30;
	public static final float CAMERA_BORDER = 15;
	public static final float DESKTOP_SCALE = 20;
	public static final boolean USE_DEBUG_RENDERER = true;

	public static boolean SUBTITLES_ENABLED = false;

	public static final float SUBTITLE_TEXT_SCALE = 1f/8f;
	public static final float FONT_BORDER_WIDTH = 0.7f;

	public static final float BG1_DIST = 8;
	public static final float BG1_HEIGHT_PART = 0.66f;
	public static final float BG1_HEIGHT_OFF = 0.33f;
	public static final float BG2_DIST = 4;
	public static final float BG2_HEIGHT_PART = 0.66f;
	public static final float BG2_HEIGHT_OFF = 0f;
	public static final float BG3_DIST = 2;
	public static final float BG3_HEIGHT_PART = 0.66f;
	public static final float BG3_HEIGHT_OFF = 0.33f;

	public static final float PHYS_GRAVITY = -40;
	public static final float PHYS_TIME_STEP = 1/45f;
	public static final int PHYS_VELO_ITERATIONS = 6;
	public static final int PHYS_POS_ITERATIONS = 2;
	public static final int KEY_LEFT = Input.Keys.A;
	public static final int KEY_JUMP = Input.Keys.W;
	public static final int KEY_RIGHT = Input.Keys.D;
	public static final int KEY_UP = Input.Keys.W;
	public static final int KEY_DOWN = Input.Keys.S;
	public static final int KEY_INTERACT = Input.Keys.E;
	public static final int KEY_HUMAN = Input.Keys.NUM_1;
	public static final int KEY_TURTLE = Input.Keys.NUM_2;
	public static final int KEY_WOLF = Input.Keys.NUM_3;
	public static final int KEY_RESTART = Input.Keys.R;
	public static final float HERO_WIDTH = 1;
	public static final float HERO_HEIGHT = 2;
	public static final float HERO_MAX_VELO = 5;
	public static final float HERO_LADDER_MAX_VELO_Y = 1f;
	public static final float HERO_LADDER_IMPULSE_Y = 10;
	public static final float HERO_IMPULSE_X = 5;
	public static final float HERO_IMPULSE_Y = 25;
	public static final float HERO_DAMPING_X_COEF = 5;
	public static final float HERO_DAMPING_X_COEF_LADDER = 100;
	public static final float HERO_GROUND_SENSOR_HEIGHT = 0.1f;
	public static final float HERO_GROUND_SENSOR_WIDTH = 0.75f;
	public static final float HERO_WALL_SENSOR_WIDTH = 0.1f;
	public static final float HERO_WALL_SENSOR_HEIGHT = 0.9f;
	public static final float HERO_JUMP_FREEZE_TIME = 0.5f;
	public static final float HERO_IMPULSE_AIR_MODIFIER = 0.2f;
	public static final float HERO_WATER_GRAVITY_SCALE = 0.1f;
	public static final float HERO_WATER_MAX_VELO_X = 2f;
	public static final float HERO_WATER_MAX_VELO_Y = 2f;
	public static final float HERO_WATER_IMPULSE_X = 2;
	public static final float HERO_WATER_IMPULSE_Y = 2;
	public static final float DEFAULT_DENSITY = 1f;
	public static final float DEFAULT_FRICTION = 0.4f;
	public static final int TEX_WIDTH_HERO = 32;
	public static final int TEX_HEIGHT_HERO = 32;
	public static final int TEX_WIDTH_PLATFORM = 16;
	public static final int TEX_HEIGHT_PLATFORM = 16;
	public static final int TEX_WIDTH_BRIDGE = 32;
	public static final int TEX_HEIGHT_BRIDGE = 32;
	public static final int TEX_WIDTH_LADDER = 32;
	public static final int TEX_HEIGHT_LADDER = 32;
	public static final int TEX_WIDTH_WATER = 32;
	public static final int TEX_HEIGHT_WATER = 32;
	public static final float TEX_WAIT_MAX_VELO = 1f;
	public static final float LETHAL_IMPULSE = 500;
	public static final float LETHAL_IMPULSE_MULTIPLIER_TURTLE = 2;
	public static final float BRIDGE_LETHAL_IMPULSE = 10;
	public static final float DESTR_TIMEOUT = 1.5f;
	public static final float HERO_DEST_RADIUS = 1;
}
