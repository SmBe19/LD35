package com.smeanox.games.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum Sounds {
	BALL("snd/ball.wav", 100),
	BRIDGE("snd/bridge.wav", 10),
	BUTTON("snd/button.wav", 10),
	WATER("snd/water.wav", 400),
	;

	private Sound sound;
	private int minWait;
	private long lastPlayTime = 0;

	Sounds(String file, int minWait) {
		this.minWait = minWait;
		this.sound = Gdx.audio.newSound(Gdx.files.internal(file));
	}

	public void play() {
		if (! Consts.MUSIC_ENABLED)
			return;
		if (System.currentTimeMillis() - lastPlayTime < minWait)
			return;
		lastPlayTime = System.currentTimeMillis();
		sound.play(0.3f);
	}
}
