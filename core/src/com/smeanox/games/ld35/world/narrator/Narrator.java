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
	private boolean heroFrozen;

	private float passedTime;
	private NarratorState currentState;

	private float cameraX;

	public Narrator() {
		currentLevel = 1;
		passedTime = 0;
		needLevelReload = false;
		heroFrozen = false;

		currentState = new NarratorState_Lvl1_1();
	}

	public void update(float delta, GameWorld gameWorld) {
		passedTime += delta;

		currentState.update(delta, passedTime, gameWorld, this);
		currentState = currentState.getNarratorState();
	}

	public void skip(GameWorld gameWorld){
		currentState.skip(passedTime, gameWorld, this);
		currentState = currentState.getNarratorState();
	}

	public void setCameraX(float cameraX) {
		this.cameraX = cameraX;
	}

	public void drawSubtitles(SpriteBatch batch, String subtitle) {
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
//
//	private int lvl1_stage = 0;
//	private int lvl1_died = 0;
//	private boolean lvl1_played1 = false;
//	private NarratorSounds lvl1_currentNarration = null;
//	private void update_lvl1(GameWorld gameWorld, float delta){
//		switch (lvl1_stage){
//			case 0:
//				if(!NarratorSounds.lvl1_1.get().isPlaying()) {
//					if(!lvl1_played1) {
//						play(NarratorSounds.lvl1_1);
//						lvl1_played1 = true;
//					} else {
//						NarratorSounds.lvl1_1.dispose();
//						lvl1_stage++;
//						// release rocks
//					}
//				}
//				break;
//			case 1:
//				if(lvl1_currentNarration == null) {
//					if (gameWorld.isGameLost()) {
//						lvl1_died++;
//						switch (lvl1_died) {
//							case 1:
//								lvl1_currentNarration = NarratorSounds.lvl1_2;
//								break;
//							case 2:
//								lvl1_currentNarration = NarratorSounds.lvl1_3;
//								break;
//							case 3:
//								lvl1_currentNarration = NarratorSounds.lvl1_4;
//								break;
//							default:
//								needLevelReload = true;
//						}
//						if(lvl1_currentNarration != null){
//							play(lvl1_currentNarration);
//						}
//					}
//					if(gameWorld.isGameWon()){
//						switch (lvl1_died){
//							case 0:
//								lvl1_currentNarration = NarratorSounds.lvl1_10;
//								break;
//							case 1:
//								lvl1_currentNarration = NarratorSounds.lvl1_7;
//								break;
//							case 2:
//								lvl1_currentNarration = NarratorSounds.lvl1_6;
//								break;
//							default:
//								lvl1_currentNarration = NarratorSounds.lvl1_5;
//						}
//						if(lvl1_currentNarration != null){
//							play(lvl1_currentNarration);
//						}
//					}
//				} else {
//					if(!lvl1_currentNarration.get().isPlaying()){
//						if(gameWorld.isGameWon()){
//							if(lvl1_currentNarration == NarratorSounds.lvl1_9){
//								lvl1_currentNarration.dispose();
//								lvl1_currentNarration = null;
//								currentLevel++;
//								needLevelReload = true;
//							} else if(lvl1_currentNarration == NarratorSounds.lvl1_8 || lvl1_currentNarration == NarratorSounds.lvl1_10){
//								lvl1_currentNarration.dispose();
//								lvl1_currentNarration = null;
//								lvl1_currentNarration = NarratorSounds.lvl1_9;
//								play(lvl1_currentNarration);
//							} else {
//								lvl1_currentNarration.dispose();
//								lvl1_currentNarration = null;
//								lvl1_currentNarration = NarratorSounds.lvl1_8;
//								play(lvl1_currentNarration);
//							}
//						} else {
//							needLevelReload = true;
//							lvl1_currentNarration.dispose();
//							lvl1_currentNarration = null;
//						}
//					}
//				}
//				break;
//		}
//	}
//
//	private int lvl2_stage = 0;
//	private int lvl2_died = 0;
//	private NarratorSounds lvl2_currentNarration = null;
//	private void update_lvl2(GameWorld gameWorld, float delta){
//		if(lvl2_currentNarration == null){
//			if(gameWorld.isGameLost()){
//				if(lvl2_stage == 0) {
//					lvl2_died++;
//					switch (lvl2_died) {
//						case 1:
//							lvl2_currentNarration = NarratorSounds.lvl2_1;
//							break;
//						case 2:
//							lvl2_currentNarration = NarratorSounds.lvl2_2;
//							break;
//						case 3:
//							lvl2_currentNarration = NarratorSounds.lvl2_3;
//							break;
//					}
//					if(lvl2_currentNarration != null) {
//						play(lvl2_currentNarration);
//					} else {
//						needLevelReload = true;
//						passedTime = 0;
//					}
//				} else if (lvl2_stage == 2){
//					lvl2_stage++;
//					lvl2_currentNarration = NarratorSounds.lvl2_7;
//					play(lvl2_currentNarration);
//				} else {
//					needLevelReload = true;
//				}
//			} else {
//				if(lvl2_stage == 0){
//					if(passedTime > 10) {
//						lvl2_stage++;
//						if (lvl2_died == 0) {
//							lvl2_currentNarration = NarratorSounds.lvl2_5;
//						} else {
//							lvl2_currentNarration = NarratorSounds.lvl2_4;
//						}
//						play(lvl2_currentNarration);
//					}
//				}
//				if(lvl2_stage >= 2 && gameWorld.isGameWon()){
//					currentLevel++;
//					needLevelReload = true;
//				}
//			}
//		} else {
//			if(!lvl2_currentNarration.get().isPlaying()){
//				switch (lvl2_stage){
//					case 0:
//						lvl2_currentNarration.dispose();
//						lvl2_currentNarration = null;
//						if(gameWorld.isGameLost()){
//							needLevelReload = true;
//							passedTime = 0;
//						}
//						break;
//					case 1:
//						lvl2_stage++;
//						lvl2_currentNarration.dispose();
//						lvl2_currentNarration = NarratorSounds.lvl2_6;
//						play(lvl2_currentNarration);
//						break;
//					case 2:
//					case 3:
//						lvl2_currentNarration.dispose();
//						lvl2_currentNarration = null;
//						if(lvl2_stage == 3){
//							needLevelReload = true;
//						}
//						break;
//				}
//			}
//		}
//	}

	public void play(NarratorSounds narratorSound){
		if(lastSound != null){
			lastSound.dispose();
		}
		narratorSound.get().play();
		lastSound = narratorSound;
	}

	public void stop(NarratorSounds narratorSound){
		narratorSound.get().stop();
		lastSound = null;
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
		System.out.println("HeroFrozen " + heroFrozen);
		this.heroFrozen = heroFrozen;
	}

	public boolean isHeroFrozen() {
		return heroFrozen;
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
