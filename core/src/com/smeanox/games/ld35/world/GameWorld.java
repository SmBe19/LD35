package com.smeanox.games.ld35.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.smeanox.games.ld35.Consts;

import java.util.List;

public class GameWorld {

	private String description;
	private World world;
	private List<Actor> actors;
	private List<Button> buttons;
	private List<Platform> platforms;
	private Hero hero;

	private float phys_accumulator;

	public GameWorld(String description, List<Actor> actors, List<Button> buttons, List<Platform> platforms, Hero hero) {
		this.description = description;
		this.actors = actors;
		this.buttons = buttons;
		this.platforms = platforms;
		this.hero = hero;

		world = new World(new Vector2(0, Consts.PHYS_GRAVITY), true);
		world.setContactListener(new ContactListnr(this));
		phys_accumulator = 0;

		addPhysObjects(actors);
		addPhysObjects(buttons);
		addPhysObjects(platforms);
		hero.addToWorld(world);
	}

	private void addPhysObjects(List<? extends PhysObject> physObjects){
		for (PhysObject physObject : physObjects) {
			physObject.addToWorld(world);
		}
	}

	public void update(float delta) {
		for (Platform platform : platforms) {
			platform.update(delta);
		}
		updatePhysics(delta);
	}

	private void updatePhysics(float delta) {
		float frameTime = Math.min(delta, 0.25f);
		phys_accumulator += frameTime;
		while (phys_accumulator >= Consts.PHYS_TIME_STEP) {
			world.step(Consts.PHYS_TIME_STEP, Consts.PHYS_VELO_ITERATIONS, Consts.PHYS_POS_ITERATIONS);
			phys_accumulator -= Consts.PHYS_TIME_STEP;
		}
	}

	public String getDescription() {
		return description;
	}

	public World getWorld() {
		return world;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public Hero getHero() {
		return hero;
	}
}
