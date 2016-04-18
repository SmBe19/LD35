package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl2_6 extends NarratorState {

	private float passedTimeSinceDead;

	public NarratorState_Lvl2_6() {
		super(NarratorSounds.lvl2_6);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if(!narratorSounds.get().isPlaying()){
				if(gameWorld.isGameWon()) {
					nextState = new NarratorState_Lvl3_1();
					narrator.setCurrentLevel(3);
					narrator.setNeedLevelReload(true);
				}
			}
			if(gameWorld.isGameLost()){
				if(gameWorld.getDeathReason() == GameWorld.DeathReason.water){
					if(!narratorSounds.get().isPlaying()) {
						nextState = new NarratorState_Lvl2_7();
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
