package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl2_0 extends NarratorState {

	public static final float ROCKS_TIME = 4;

	public NarratorState_Lvl2_0() {
		super(NarratorSounds.lvl2_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(gameWorld.isGameLost()){
			nextState = new NarratorState_Lvl2_1();
		}
		if(passedTime > NarratorState_Lvl2_0.ROCKS_TIME){
			nextState = new NarratorState_Lvl2_5();
		}
	}
}
