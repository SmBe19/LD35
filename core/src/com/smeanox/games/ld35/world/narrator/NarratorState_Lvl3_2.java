package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl3_2 extends NarratorState {

	private float passedTimeSinceDead;
	private boolean passedDrop;
	private float passedDropSince;
	private boolean reloadedLevel;
	private int dead;

	public NarratorState_Lvl3_2() {
		super(NarratorSounds.lvl3_2);
		dead = 0;
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(!reloadedLevel){
					narrator.setNeedLevelReload(true);
					reloadedLevel = true;
					passedTimeSinceDead = 0;
					passedDrop = false;
				} else {
					if(passedDrop && passedDropSince > NarratorState_Lvl3_1.DROP_TIME){
						nextState = new NarratorState_Lvl3_12();
					}
					if (gameWorld.isGameLost()) {
						dead++;
						playedSound = false;
						reloadedLevel = false;
						switch (dead) {
							case 1:
								narratorSounds = NarratorSounds.lvl3_3;
								break;
							case 2:
								narratorSounds = NarratorSounds.lvl3_4;
								break;
							case 3:
								narratorSounds = NarratorSounds.lvl3_5;
								break;
							case 4:
								narratorSounds = NarratorSounds.lvl3_6;
								break;
							case 5:
								narratorSounds = NarratorSounds.lvl3_7;
								break;
							case 6:
								narratorSounds = NarratorSounds.lvl3_8;
								break;
							case 7:
								narratorSounds = NarratorSounds.lvl3_9;
								break;
							case 8:
								narratorSounds = NarratorSounds.lvl3_10;
								break;
							case 9:
								narratorSounds = NarratorSounds.lvl3_11;
								break;
							default:
								playedSound = true;
								passedTimeSinceDead += delta;
								if(passedTimeSinceDead <= Consts.NARRATOR_DEAD_PAUSE){
									reloadedLevel = true;
								}
						}
					}
				}
			}
			if(passedDrop){
				passedDropSince += delta;
			}
			if(!passedDrop && gameWorld.getHero().getBody().getPosition().x > -9){
				passedDrop = true;
				passedDropSince = 0;
			}
		}
	}
}
