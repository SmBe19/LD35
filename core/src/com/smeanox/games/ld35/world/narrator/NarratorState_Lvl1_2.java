package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl1_2 extends NarratorState {

	private boolean reloadedLevel;

	public NarratorState_Lvl1_2() {
		super(NarratorSounds.lvl1_2);
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
				} else {
					if(passedTime > NarratorState_Lvl1_1.ROCKS_TIME){
						nextState = new NarratorState_Lvl1_7();
					}
					if(gameWorld.isGameLost()) {
						nextState = new NarratorState_Lvl1_3();
					}
				}
			}
		}
	}
}
