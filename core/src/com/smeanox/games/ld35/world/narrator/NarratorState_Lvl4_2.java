package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl4_2 extends NarratorState {

	private float passedTimeSinceDead;

	public NarratorState_Lvl4_2() {
		super(NarratorSounds.lvl4_2);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(gameWorld.isGameWon()){
					nextState = new NarratorState_Lvl5_1();
					narrator.setCurrentLevel(5);
					narrator.setNeedLevelReload(true);
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
