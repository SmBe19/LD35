package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl2_1 extends NarratorState {

	private boolean reloadedLevel;
	private int dead;
	private float passedTimeSinceDead;

	public NarratorState_Lvl2_1() {
		super(NarratorSounds.lvl2_1);
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
				} else {
					if(passedTime > NarratorState_Lvl2_0.ROCKS_TIME){
						nextState = new NarratorState_Lvl2_4();
					}
					if(gameWorld.isGameLost()) {
						dead++;
						playedSound = false;
						reloadedLevel = false;
						switch (dead){
							case 1:
								narratorSounds = NarratorSounds.lvl2_2;
								break;
							case 2:
								narratorSounds = NarratorSounds.lvl2_3;
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
		}
	}
}
