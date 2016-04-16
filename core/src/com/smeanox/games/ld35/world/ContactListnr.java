package com.smeanox.games.ld35.world;

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
			if(hero.isSensor() && !other.isSensor()){
				gameWorld.getHero().setOnGround(true);
			}
			if (!hero.isSensor() && other.getBody().getUserData() instanceof Button) {
				Button button = (Button) other.getBody().getUserData();
				gameWorld.getHero().setLastButton(button);
				button.startInteract();
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
			if(hero.isSensor() && !other.isSensor()){
				gameWorld.getHero().setOnGround(false);
			}
			if (!hero.isSensor() && other.getBody().getUserData() instanceof Button) {
				Button button = (Button) other.getBody().getUserData();
				if (gameWorld.getHero().getLastButton() == button) {
					gameWorld.getHero().setLastButton(null);
				}
				button.endInteract();
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}