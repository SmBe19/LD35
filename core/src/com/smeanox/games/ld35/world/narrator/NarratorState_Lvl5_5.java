package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl5_5 extends NarratorState {

	private float passedTimeSinceDead;
	private float totalPassedTime;
	private boolean fellInWater;

	public NarratorState_Lvl5_5() {
		super(NarratorSounds.lvl5_5);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		totalPassedTime += delta;
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				nextState = new NarratorState_Lvl6_1();
				narrator.setCurrentLevel(6);
				narrator.setNeedLevelReload(true);
			}
		}
	}
}
