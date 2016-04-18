package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl5_2 extends NarratorState {

	private float passedTimeSinceDead;
	private float totalPassedTime;
	private boolean fellInWater, usedUpTime;

	public NarratorState_Lvl5_2() {
		super(NarratorSounds.lvl5_2);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		totalPassedTime += delta;
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(gameWorld.isGameWon()){
					nextState = new NarratorState_Lvl5_5();
				} else if(totalPassedTime > 90 && !usedUpTime){
					narratorSounds = NarratorSounds.lvl5_4;
					playedSound = false;
					usedUpTime = true;
				}
			}
			if(gameWorld.isGameLost()){
				if(!fellInWater && gameWorld.getDeathReason() == GameWorld.DeathReason.water){
					if(!narratorSounds.get().isPlaying()) {
						narratorSounds = NarratorSounds.lvl5_3;
						playedSound = false;
						fellInWater = true;
					}
				} else {
					passedTimeSinceDead += delta;
					if (passedTimeSinceDead > Consts.NARRATOR_DEAD_PAUSE) {
						narrator.setNeedLevelReload(true);
						passedTimeSinceDead = 0;
					}
				}
			}
		}
	}
}
