package com.smeanox.games.ld35.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.LD35;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (Consts.WIDTH * Consts.DESKTOP_SCALE);
		config.height = ((int) (Consts.HEIGHT * Consts.DESKTOP_SCALE));
		new LwjglApplication(new LD35(), config);
	}
}
