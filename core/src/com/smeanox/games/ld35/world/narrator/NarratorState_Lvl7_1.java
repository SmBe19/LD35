package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl7_1 extends NarratorState {

	private float passedTimeSinceDead;
	private float totalPassedTime;
	private boolean usedUpTime;

	public NarratorState_Lvl7_1() {
		super(NarratorSounds.lvl7_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		totalPassedTime += delta;
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(gameWorld.isGameWon()){
					nextState = new NarratorState_Lvl8_1();
					narrator.setCurrentLevel(8);
					narrator.setNeedLevelReload(true);
				} else if(totalPassedTime > 60 && !usedUpTime){
					narratorSounds = NarratorSounds.lvl7_2;
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
