package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl5_1 extends NarratorState {

	private float passedTimeSinceDead;

	public NarratorState_Lvl5_1() {
		super(NarratorSounds.lvl5_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(gameWorld.getPlatformById(10).isMovingEnabled()){
					nextState = new NarratorState_Lvl5_2();
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
