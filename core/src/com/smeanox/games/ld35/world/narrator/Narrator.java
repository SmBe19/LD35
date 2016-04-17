package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;

public class Narrator {

	private int currentLevel;
	private boolean needLevelReload;

	private float passedTime;

	public Narrator() {
		currentLevel = 0;
		passedTime = 0;
	}

	public void update(GameWorld gameWorld, float delta) {
		passedTime += delta;

		switch (currentLevel){
			case 1:
				update_lvl1(gameWorld, delta);
				break;
			case 2:
				update_lvl2(gameWorld, delta);
				break;
		}
	}

	private int lvl1_stage = 0;
	private int lvl1_died = 0;
	private boolean lvl1_played1 = false;
	private NarratorSounds lvl1_currentNarration = null;
	private void update_lvl1(GameWorld gameWorld, float delta){
		switch (lvl1_stage){
			case 0:
				if(!NarratorSounds.lvl1_1.get().isPlaying()) {
					if(!lvl1_played1) {
						NarratorSounds.lvl1_1.get().play();
						lvl1_played1 = true;
					} else {
						NarratorSounds.lvl1_1.dispose();
						lvl1_stage++;
						// release rocks
					}
				}
				break;
			case 1:
				if(lvl1_currentNarration == null) {
					if (gameWorld.isGameLost()) {
						lvl1_died++;
						switch (lvl1_died) {
							case 1:
								lvl1_currentNarration = NarratorSounds.lvl1_2;
								break;
							case 2:
								lvl1_currentNarration = NarratorSounds.lvl1_3;
								break;
							case 3:
								lvl1_currentNarration = NarratorSounds.lvl1_4;
								break;
							default:
								needLevelReload = true;
						}
						if(lvl1_currentNarration != null){
							lvl1_currentNarration.get().play();
						}
					}
					if(gameWorld.isGameWon()){
						switch (lvl1_died){
							case 0:
								lvl1_currentNarration = NarratorSounds.lvl1_10;
								break;
							case 1:
								lvl1_currentNarration = NarratorSounds.lvl1_7;
								break;
							case 2:
								lvl1_currentNarration = NarratorSounds.lvl1_6;
								break;
							default:
								lvl1_currentNarration = NarratorSounds.lvl1_5;
						}
						if(lvl1_currentNarration != null){
							lvl1_currentNarration.get().play();
						}
					}
				} else {
					if(!lvl1_currentNarration.get().isPlaying()){
						if(gameWorld.isGameWon()){
							if(lvl1_currentNarration == NarratorSounds.lvl1_9){
								lvl1_currentNarration.dispose();
								lvl1_currentNarration = null;
								currentLevel++;
								needLevelReload = true;
							} else if(lvl1_currentNarration == NarratorSounds.lvl1_8 || lvl1_currentNarration == NarratorSounds.lvl1_10){
								lvl1_currentNarration.dispose();
								lvl1_currentNarration = null;
								lvl1_currentNarration = NarratorSounds.lvl1_9;
								lvl1_currentNarration.get().play();
							} else {
								lvl1_currentNarration.dispose();
								lvl1_currentNarration = null;
								lvl1_currentNarration = NarratorSounds.lvl1_8;
								lvl1_currentNarration.get().play();
							}
						} else {
							needLevelReload = true;
							lvl1_currentNarration.dispose();
							lvl1_currentNarration = null;
						}
					}
				}
				break;
		}
	}

	private int lvl2_stage = 0;
	private int lvl2_died = 0;
	private NarratorSounds lvl2_currentNarration = null;
	private void update_lvl2(GameWorld gameWorld, float delta){
		if(lvl2_currentNarration == null){
			if(gameWorld.isGameLost()){
				if(lvl2_stage == 0) {
					lvl2_died++;
					switch (lvl2_died) {
						case 1:
							lvl2_currentNarration = NarratorSounds.lvl2_1;
							break;
						case 2:
							lvl2_currentNarration = NarratorSounds.lvl2_2;
							break;
						case 3:
							lvl2_currentNarration = NarratorSounds.lvl2_3;
							break;
					}
					if(lvl2_currentNarration != null) {
						lvl2_currentNarration.get().play();
					} else {
						needLevelReload = true;
						passedTime = 0;
					}
				} else if (lvl2_stage == 2){
					lvl2_stage++;
					lvl2_currentNarration = NarratorSounds.lvl2_7;
					lvl2_currentNarration.get().play();
				} else {
					needLevelReload = true;
				}
			} else {
				if(lvl2_stage == 0){
					if(passedTime > 10) {
						lvl2_stage++;
						if (lvl2_died == 0) {
							lvl2_currentNarration = NarratorSounds.lvl2_5;
						} else {
							lvl2_currentNarration = NarratorSounds.lvl2_4;
						}
						lvl2_currentNarration.get().play();
					}
				}
				if(lvl2_stage >= 2 && gameWorld.isGameWon()){
					currentLevel++;
					needLevelReload = true;
				}
			}
		} else {
			if(!lvl2_currentNarration.get().isPlaying()){
				switch (lvl2_stage){
					case 0:
						lvl2_currentNarration.dispose();
						lvl2_currentNarration = null;
						if(gameWorld.isGameLost()){
							needLevelReload = true;
							passedTime = 0;
						}
						break;
					case 1:
						lvl2_stage++;
						lvl2_currentNarration.dispose();
						lvl2_currentNarration = NarratorSounds.lvl2_6;
						lvl2_currentNarration.get().play();
						break;
					case 2:
					case 3:
						lvl2_currentNarration.dispose();
						lvl2_currentNarration = null;
						if(lvl2_stage == 3){
							needLevelReload = true;
						}
						break;
				}
			}
		}
	}

	public void loadedLevel(String file){
		needLevelReload = false;
	}

	public boolean isNeedLevelReload() {
		return needLevelReload;
	}

	public String getCurrentFile(){
		return "lvl/lvl" + currentLevel + ".xml";
	}
}
