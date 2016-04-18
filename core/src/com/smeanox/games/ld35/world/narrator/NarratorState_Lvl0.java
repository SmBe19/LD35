package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl0 extends NarratorState {

	private float passedTimeSinceDead;

	public NarratorState_Lvl0() {
		super(NarratorSounds.lvl1_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(gameWorld.isGameLost()){
			passedTimeSinceDead += delta;
			if (passedTimeSinceDead > Consts.NARRATOR_DEAD_PAUSE) {
				narrator.setNeedLevelReload(true);
				passedTimeSinceDead = 0;
			}
		}
	}
}
