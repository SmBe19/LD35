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
			if(hero.isSensor() && hero.getUserData() instanceof HeroForm && !other.isSensor()){
				gameWorld.getHero().setHeroFormPossible((HeroForm) hero.getUserData(), false);
			}
			if (!hero.isSensor() && other.getBody().getUserData() instanceof Button) {
				Button button = (Button) other.getBody().getUserData();
				gameWorld.getHero().setLastButton(button);
				button.startInteract(gameWorld);
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
			if(hero.isSensor() && hero.getUserData() instanceof HeroForm && !other.isSensor()){
				gameWorld.getHero().setHeroFormPossible((HeroForm) hero.getUserData(), true);
			}
			if (!hero.isSensor() && other.getBody().getUserData() instanceof Button) {
				Button button = (Button) other.getBody().getUserData();
				if (gameWorld.getHero().getLastButton() == button) {
					gameWorld.getHero().setLastButton(null);
				}
				button.endInteract(gameWorld);
			}
			if(!hero.isSensor() && other.getBody().getUserData() == gameWorld.getWorldBorder()){
				gameWorld.setGameLost(true);
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

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
			if (!hero.isSensor() && impulse.getCount() >= 1 && impulse.getNormalImpulses()[0] > 5) {
				System.out.println(impulse.getNormalImpulses()[0]);
			}
			if (!hero.isSensor() && impulse.getCount() >= 1 && impulse.getNormalImpulses()[0] > Consts.LETHAL_IMPULSE) {
				gameWorld.setGameLost(true);
			};
		}

	}
}
