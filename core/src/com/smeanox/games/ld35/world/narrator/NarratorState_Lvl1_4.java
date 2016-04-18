package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl1_4 extends NarratorState {

	private float passedTimeSinceDead;
	private boolean reloadedLevel;

	public NarratorState_Lvl1_4() {
		super(NarratorSounds.lvl1_4);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(!reloadedLevel){
					narrator.setNeedLevelReload(true);
					reloadedLevel = true;
					passedTimeSinceDead = 0;
				} else {
					if(gameWorld.isGameLost()){
						passedTimeSinceDead += delta;
						if(passedTimeSinceDead > Consts.NARRATOR_DEAD_PAUSE){
							reloadedLevel = false;
						}
					} else if(passedTime > NarratorState_Lvl1_1.ROCKS_TIME){
						nextState = new NarratorState_Lvl1_5();
					}
				}
			}
		}
	}
}
