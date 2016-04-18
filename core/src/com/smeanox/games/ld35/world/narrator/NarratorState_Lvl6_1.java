package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl6_1 extends NarratorState {

	private float passedTimeSinceDead;
	private float totalPassedTime;
	private boolean usedUpTime;

	public NarratorState_Lvl6_1() {
		super(NarratorSounds.lvl6_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		totalPassedTime += delta;
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(gameWorld.isGameWon()){
					nextState = new NarratorState_Lvl7_1();
					narrator.setCurrentLevel(7);
					narrator.setNeedLevelReload(true);
				} else if(totalPassedTime > 60 && !usedUpTime){
					narratorSounds = NarratorSounds.lvl6_2;
					playedSound = false;
					usedUpTime = true;
				}
			}
			if(gameWorld.isGameLost()){
				passedTimeSinceDead += delta;
				if (passedTimeSinceDead > Consts.NARRATOR_DEAD_PAUSE) {
					narrator.setNeedLevelReload(true);
					passedTimeSinceDead = 0;
				}
			}
		}
	}
}
