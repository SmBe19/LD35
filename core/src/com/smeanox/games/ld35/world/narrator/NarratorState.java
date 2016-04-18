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

		System.out.println("init " + narratorSounds.name());
	}

	protected void playSound(Narrator narrator){
		narrator.play(narratorSounds);
		playedSound = true;

		System.out.println("play sound " + narratorSounds.name());
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

		System.out.println("release rocks");
	}

	protected void freezeRocks(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(actor instanceof Ball){
				actor.setFrozen(true);
			}
		}
		releasedRocks = false;

		System.out.println("freeze rocks");
	}

	protected void releaseActors(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(!(actor instanceof Ball)){
				actor.setFrozen(false);
			}
		}
		releasedActors = true;

		System.out.println("release actors");
	}

	protected void freezeActors(GameWorld gameWorld){
		for(Actor actor : gameWorld.getActors()){
			if(!(actor instanceof Ball)){
				actor.setFrozen(true);
			}
		}
		releasedActors = false;

		System.out.println("freeze actors");
	}

    public abstract void update(float delta, float passedTime, GameWorld gameWorld, Narrator narrator);

	public NarratorState getNarratorState(){
		return nextState;
	}

	public void skip(float passedTime, GameWorld gameWorld, Narrator narrator){
		stopSound(narrator);
	}
}
