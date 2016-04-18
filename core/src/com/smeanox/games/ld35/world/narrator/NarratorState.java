package com.smeanox.games.ld35.world.narrator;

import com.smeanox.games.ld35.world.Actor;
import com.smeanox.games.ld35.world.Ball;
import com.smeanox.games.ld35.world.GameWorld;

public abstract class NarratorState {

	protected NarratorState nextState;
	protected NarratorSounds narratorSounds;
	protected boolean playedSound;
	protected boolean releasedRocks;
	protected boolean releasedActors;

	public NarratorState(NarratorSounds narratorSounds) {
		this.narratorSounds = narratorSounds;
		nextState = this;
	}

	protected void playSound(Narrator narrator){
		narrator.play(narratorSounds);
		playedSound = true;
	}

	protected void stopSound(Narrator narrator){
		narrator.stop(narratorSounds);
	}

	protected void releaseRocks(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(actor instanceof Ball){
				actor.setFrozen(false);
			}
		}
		releasedRocks = true;
	}

	protected void freezeRocks(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(actor instanceof Ball){
				actor.setFrozen(true);
			}
		}
		releasedRocks = false;
	}

	protected void releaseActors(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(!(actor instanceof Ball)){
				actor.setFrozen(false);
			}
		}
		releasedActors = true;
	}

	protected void freezeActors(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(!(actor instanceof Ball)){
				actor.setFrozen(true);
			}
		}
		releasedActors = false;
	}

    public abstract void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator);

	public NarratorState getNarratorState(){
		return nextState;
	}

	public void skip(float passedTime, GameWorld gameWorld, Narrator narrator){
		stopSound(narrator);
	}
}
