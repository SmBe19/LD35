package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.world.GameWorld;

public class NarratorState_Lvl3_1 extends NarratorState {

	public static final int DROP_TIME = 2;

	private float passedTimeSinceDead;
	private boolean passedDrop;
	private float passedDropSince;

	public NarratorState_Lvl3_1() {
		super(NarratorSounds.lvl3_1);
	}

	@Override
	public void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator) {
		if(!playedSound){
			playSound(narrator);
		} else {
			if (!narratorSounds.get().isPlaying()){
				if(passedDrop && passedDropSince > DROP_TIME){
					nextState = new NarratorState_Lvl3_12();
				}
			}
			if(passedDrop){
				passedDropSince += delta;
			}
			if(!passedDrop && gameWorld.getHero().getBody().getPosition().x > -9){
				passedDrop = true;
				passedDropSince = 0;
			}
			if(gameWorld.isGameLost()){
				if(gameWorld.getDeathReason() == GameWorld.DeathReason.impulse){
					if(!narratorSounds.get().isPlaying()){
						nextState = new NarratorState_Lvl3_2();
					}
				} else {
					passedTimeSinceDead += delta;
					if (passedTimeSinceDead > Consts.NARRATOR_DEAD_PAUSE) {
						narrator.setNeedLevelReload(true);
						passedTimeSinceDead = 0;
						passedDrop = false;
					}
				}
			}
		}
	}
}
