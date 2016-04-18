package com.smeanox.games.ld35.world;

import com.smeanox.games.ld35.Consts;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactListnr implements ContactListener {

	GameWorld gameWorld;

	public ContactListnr(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture hero = contact.getFixtureA();
		Fixture other = contact.getFixtureB();
		if(other.getBody().getUserData() == gameWorld.getHero()){
			hero = contact.getFixtureB();
			other = contact.getFixtureA();
		}

		if (hero.getBody().getUserData() == gameWorld.getHero()) {
			if(hero.isSensor() && hero.getUserData() == Hero.SENSOR_GROUND && !other.isSensor()){
				gameWorld.getHero().setOnGround(true);
			}
			if(hero.isSensor() && hero.getUserData() == Hero.SENSOR_WALL_LEFT && !other.isSensor()){
				gameWorld.getHero().setOnWallLeft(true);
			}
			if(hero.isSensor() && hero.getUserData() == Hero.SENSOR_WALL_RIGHT && !other.isSensor()){
				gameWorld.getHero().setOnWallRight(true);
			}
			if(hero.isSensor() && hero.getUserData() instanceof HeroForm && !other.isSensor()){
				gameWorld.getHero().setHeroFormPossible((HeroForm) hero.getUserData(), false);
			}
			if(!hero.isSensor() && other.getBody().getUserData() instanceof Ladder){
				gameWorld.getHero().setOnLadder(true);
			}
			if(!hero.isSensor() && other.getBody().getUserData() instanceof Water){
				if(gameWorld.getHero().getCurrentForm() != HeroForm.turtle){
					gameWorld.setGameLost(true);
					gameWorld.setDeathReason(GameWorld.DeathReason.water);
				} else {
					gameWorld.getHero().setInWater(true);
				}
			}
			if (!hero.isSensor() && other.getBody().getUserData() instanceof Button) {
				Button button = (Button) other.getBody().getUserData();
				gameWorld.getHero().setLastButton(button);
				button.startInteract(gameWorld);
			}
			if (hero.isSensor() && hero.getUserData() == Hero.SENSOR_GROUND && other.getBody().getUserData() instanceof Platform) {
				if (((Platform) other.getBody().getUserData()).getPlatformType() == Platform.PlatformType.destr) {
					((Platform) other.getBody().getUserData()).destroy();
				}
			}
			if(!hero.isSensor() && other.getBody().getUserData() == gameWorld.getWorldBorder()){
				gameWorld.setGameLost(true);
				gameWorld.setDeathReason(GameWorld.DeathReason.outOfBounds);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture hero = contact.getFixtureA();
		Fixture other = contact.getFixtureB();
		if(other.getBody().getUserData() == gameWorld.getHero()){
			hero = contact.getFixtureB();
			other = contact.getFixtureA();
		}

		if (hero.getBody().getUserData() == gameWorld.getHero()) {
			if(hero.isSensor() && hero.getUserData() == Hero.SENSOR_GROUND && !other.isSensor()){
				gameWorld.getHero().setOnGround(false);
			}
			if(hero.isSensor() && hero.getUserData() == Hero.SENSOR_WALL_LEFT && !other.isSensor()){
				gameWorld.getHero().setOnWallLeft(false);
			}
			if(hero.isSensor() && hero.getUserData() == Hero.SENSOR_WALL_RIGHT && !other.isSensor()){
				gameWorld.getHero().setOnWallRight(false);
			}
			if (hero.isSensor() && hero.getUserData() instanceof HeroForm && !other.isSensor()){
				gameWorld.getHero().setHeroFormPossible((HeroForm) hero.getUserData(), true);
			}
			if(!hero.isSensor() && other.getBody().getUserData() instanceof Ladder){
				gameWorld.getHero().setOnLadder(false);
			}
			if(!hero.isSensor() && other.getBody().getUserData() instanceof Water){
				gameWorld.getHero().setInWater(false);
			}
			if (!hero.isSensor() && other.getBody().getUserData() instanceof Button) {
				Button button = (Button) other.getBody().getUserData();
				if (gameWorld.getHero().getLastButton() == button) {
					gameWorld.getHero().setLastButton(null);
				}
				button.endInteract(gameWorld);
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture hero = contact.getFixtureA();
		Fixture other = contact.getFixtureB();
		if(other.getBody().getUserData() == gameWorld.getHero()){
			hero = contact.getFixtureB();
			other = contact.getFixtureA();
		}

		if(hero.getBody().getUserData() == gameWorld.getHero()){
			if(!gameWorld.getHero().isOnGround() && gameWorld.getHero().isOnLadder()
					&& gameWorld.getHero().getCurrentForm() == HeroForm.human && other.getBody().getUserData() instanceof Platform && ((Platform)other.getBody().getUserData()).isLadderPassable()) {
				contact.setEnabled(false);
			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture hero = contact.getFixtureA();
		Fixture other = contact.getFixtureB();
		if(other.getBody().getUserData() == gameWorld.getHero()){
			hero = contact.getFixtureB();
			other = contact.getFixtureA();
		}

		if (hero.getBody().getUserData() == gameWorld.getHero()) {
			float lethalImpulse = gameWorld.getHero().getCurrentForm().getLethalImpulse();
			if(gameWorld.getHero().isTurtleActive()){
				lethalImpulse *= Consts.LETHAL_IMPULSE_MULTIPLIER_TURTLE;
			}
//			if(impulse.getCount() >= 1 && impulse.getNormalImpulses()[0] > 2)
//				System.out.println(impulse.getNormalImpulses()[0]);
			if (!hero.isSensor() && impulse.getCount() >= 1 && impulse.getNormalImpulses()[0] > lethalImpulse) {
				gameWorld.setGameLost(true);
				gameWorld.setDeathReason(GameWorld.DeathReason.impulse);
			}
		}

		hero = contact.getFixtureA();
		other = contact.getFixtureB();
		if(other.getBody().getUserData() instanceof Platform){
			hero = contact.getFixtureB();
			other = contact.getFixtureA();
		}

		if (hero.getBody().getUserData() instanceof Platform) {
			if(((Platform) hero.getBody().getUserData()).getPlatformType() == Platform.PlatformType.bridge
					&& impulse.getCount() >= 1 && impulse.getNormalImpulses()[0] > Consts.BRIDGE_LETHAL_IMPULSE){
				((Platform) hero.getBody().getUserData()).destroy();
			}
		}
	}
}
