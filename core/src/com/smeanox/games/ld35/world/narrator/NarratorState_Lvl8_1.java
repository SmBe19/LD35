package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl8_1 extends NarratorState {

	private float passedTimeSinceDead;
	private boolean usedUpTime;
	private boolean droppedSign;

	public NarratorState_Lvl8_1() {
		super(NarratorSounds.lvl8_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(gameWorld.isGameWon()){
					nextState = new NarratorState_Lvl8_3();
				} else if(gameWorld.getHero().getBody().getPosition().x > 15 && !usedUpTime){
					narratorSounds = NarratorSounds.lvl8_2;
					playedSound = false;
					usedUpTime = true;
				} else if(usedUpTime && !droppedSign){
					releaseActors(gameWorld);
					droppedSign = true;
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
