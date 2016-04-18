package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl1_1 extends NarratorState {

	private float passedTimeSinceRocks;

	public NarratorState_Lvl1_1() {
		super(NarratorSounds.lvl1_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
			narrator.setHeroFrozen(true);
			freezeRocks(gameWorld);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(!releasedRocks){
					narrator.setHeroFrozen(false);
					releaseRocks(gameWorld);
					passedTimeSinceRocks = 0;
				} else {
					passedTimeSinceRocks += delta;
					if(passedTimeSinceRocks > 5){
						nextState = new NarratorState_Lvl1_10();
					}
					if(gameWorld.isGameLost()){
						nextState = new NarratorState_Lvl1_2();
					}
				}
			}
		}
	}
}
