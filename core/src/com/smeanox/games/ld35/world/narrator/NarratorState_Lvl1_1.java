package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl1_1 extends NarratorState {

	public static final float ROCKS_TIME = 4;

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
					if(passedTimeSinceRocks > NarratorState_Lvl1_1.ROCKS_TIME){
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
