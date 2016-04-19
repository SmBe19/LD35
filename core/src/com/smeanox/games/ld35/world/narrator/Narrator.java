package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.Font;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.ArrayList;

public class Narrator {

	private NarratorSounds lastSound = null;

	private int currentLevel;
	private boolean needLevelReload;
	private boolean rollCredits;
	private boolean heroFrozen;

	private float passedTime;
	private float lastSoundPlayed;
	private NarratorState currentState;
	private int lastLvlX;

	private float cameraX;

	public Narrator() {
		currentLevel = 1;
		passedTime = 0;
		needLevelReload = false;
		heroFrozen = false;

		lastLvlX = 0;

		currentState = new NarratorState_Lvl1_1();
	}

	public void update(float delta, GameWorld gameWorld) {
		passedTime += delta;

		currentState.update(delta, passedTime, gameWorld, this);
		currentState = currentState.getNarratorState();

		if(lastLvlX < 4 && passedTime - lastSoundPlayed > 62){
			switch (lastLvlX){
				case 0:
					play(NarratorSounds.lvlX_1);
					break;
				case 1:
					play(NarratorSounds.lvlX_2);
					break;
				case 2:
					play(NarratorSounds.lvlX_3);
					break;
				case 3:
					play(NarratorSounds.lvlX_4);
					break;
			}
			lastLvlX++;
		}
	}

	public void skip(GameWorld gameWorld){
		currentState.skip(passedTime, gameWorld, this);
		currentState = currentState.getNarratorState();
	}

	public void setCameraX(float cameraX) {
		this.cameraX = cameraX;
	}

	public void drawSubtitles(SpriteBatch batch, String subtitle) {
		if(!Consts.SUBTITLES_ENABLED){
			return;
		}
		List<String> lines = new ArrayList<String>();
		String line = "";
		String[] words = subtitle.split(" ");
		for (int i = 0; i < words.length; i++) {
			if ( Font.FONT1.getGlyphWidth() * Consts.SUBTITLE_TEXT_SCALE * (line.length() + 1 + words[i].length()) > Consts.WIDTH) {
				lines.add(line);
				line = words[i] + " ";
			} else {
				line = line + words[i] + " ";
			}
		}
		lines.add(line);
		float y = - Consts.HEIGHT/2.f + 0.5f;
		for (int i = lines.size()-1; i >= 0; i--) {
			String l = lines.get(i);
			float lw = l.length() * Consts.SUBTITLE_TEXT_SCALE * Font.FONT1.getGlyphWidth();
			Font.FONT1.drawBordered(batch, l.toUpperCase(), cameraX-lw/2, y, Consts.SUBTITLE_TEXT_SCALE);
			y += Consts.SUBTITLE_TEXT_SCALE * (Consts.SUBTITLE_DIST_Y * Font.FONT1.getGlyphHeight());
		}
	}

	public void drawCurrentSubtitles(SpriteBatch batch) {
		if (lastSound != null && lastSound.get().isPlaying()) {
			String subtitle = lastSound.getCurrentSubtitle();
			if (subtitle != null)
				drawSubtitles(batch, subtitle);
		}
	}

	public void play(NarratorSounds narratorSound){
		if(narratorSound == lastSound){
			return;
		}
		if(lastSound != null){
			stop(lastSound);
		}
		narratorSound.get().play();
		lastSound = narratorSound;
		lastSoundPlayed = passedTime;
	}

	public void stop(NarratorSounds narratorSound){
		narratorSound.get().stop();
		if(lastSound == narratorSound) {
			lastSound.dispose();
			lastSound = null;
		}
	}

	public void loadedLevel(String file){
		needLevelReload = false;
		passedTime = 0;
	}

	public void setNeedLevelReload(boolean needLevelReload) {
		this.needLevelReload = needLevelReload;
	}

	public boolean isNeedLevelReload() {
		return needLevelReload;
	}

	public void setHeroFrozen(boolean heroFrozen) {
		this.heroFrozen = heroFrozen;
	}

	public boolean isHeroFrozen() {
		return heroFrozen;
	}

	public boolean isRollCredits() {
		return rollCredits;
	}

	public void setRollCredits(boolean rollCredits) {
		this.rollCredits = rollCredits;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public String getCurrentFile(){
		return "lvl/lvl" + currentLevel + ".xml";
	}
}
