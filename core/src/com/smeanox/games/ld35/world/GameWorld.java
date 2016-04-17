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
	private List<Ladder> ladders;
	private List<Water> waters;
	private List<Platform> platforms;
	private List<Text> texts;
	private Hero hero;
	private WorldBorder worldBorder;
	private boolean gameLost, gameWon;

	private float phys_accumulator;

	public GameWorld(String description, List<Actor> actors, List<Button> buttons, List<Ladder> ladders, List<Water> waters, List<Platform> platforms, List<Text> texts, Hero hero) {
		this.description = description;
		this.actors = actors;
		this.buttons = buttons;
		this.ladders = ladders;
		this.waters = waters;
		this.platforms = platforms;
		this.texts = texts;
		this.hero = hero;
		this.worldBorder = new WorldBorder();

		world = new World(new Vector2(0, Consts.PHYS_GRAVITY), true);
		world.setContactListener(new ContactListnr(this));
		phys_accumulator = 0;

		gameLost = false;
		gameWon = false;

		addPhysObjects(actors);
		addPhysObjects(buttons);
		addPhysObjects(ladders);
		addPhysObjects(waters);
		addPhysObjects(platforms);
		hero.addToWorld(world);
		worldBorder.addToWorld(world);
	}

	private void addPhysObjects(List<? extends PhysObject> physObjects){
		for (PhysObject physObject : physObjects) {
			physObject.addToWorld(world);
		}
	}

	public void update(float delta) {
		if(gameWon || gameLost){
			return;
		}
		for (Platform platform : platforms) {
			platform.update(delta);
		}
		updatePhysics(delta);

		if(getHero().reachedDest()){
			gameWon = true;
		}
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

	public List<Ladder> getLadders() {
		return ladders;
	}

	public List<Water> getWaters() {
		return waters;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public Platform getPlatformById(int id){
		for (Platform platform : getPlatforms()) {
			if (platform.getId() == id) {
				return platform;
			}
		}
		return null;
	}

	public List<Text> getTexts() {
		return texts;
	}

	public Hero getHero() {
		return hero;
	}

	public WorldBorder getWorldBorder() {
		return worldBorder;
	}

	public boolean isGameLost() {
		return gameLost;
	}

	public void setGameLost(boolean gameLost) {
		this.gameLost = gameLost;
	}

	public boolean isGameWon() {
		return gameWon;
	}

	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}
}
