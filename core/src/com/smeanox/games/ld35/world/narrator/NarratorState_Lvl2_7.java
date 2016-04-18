package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl2_7 extends NarratorState {

	private float passedTimeSinceDead;

	public NarratorState_Lvl2_7() {
		super(NarratorSounds.lvl2_6);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if(!narratorSounds.get().isPlaying()){
				// next state
			}
			if(gameWorld.isGameLost()){
				if(gameWorld.getDeathReason() == GameWorld.DeathReason.water){
					// next state
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
