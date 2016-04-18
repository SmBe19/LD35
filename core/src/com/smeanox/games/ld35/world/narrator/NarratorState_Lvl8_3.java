package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl8_3 extends NarratorState {

	private float passedTimeSinceDead;
	private float totalPassedTime;
	private boolean fellInWater;

	public NarratorState_Lvl8_3() {
		super(NarratorSounds.lvl8_3);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		totalPassedTime += delta;
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				narrator.setRollCredits(true);
			}
		}
	}
}
