package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl1_6 extends NarratorState {

	private float passedTimeSinceDead;

	public NarratorState_Lvl1_6() {
		super(NarratorSounds.lvl1_6);
		releasedRocks = true;
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if(!narratorSounds.get().isPlaying()){
				nextState = new NarratorState_Lvl1_8();
			}
			if(gameWorld.isGameLost()){
				passedTimeSinceDead += delta;
				if(passedTimeSinceDead > Consts.NARRATOR_DEAD_PAUSE) {
					narrator.setNeedLevelReload(true);
					releasedRocks = false;
					passedTimeSinceDead = 0;
				}
			}
		}
	}
}
