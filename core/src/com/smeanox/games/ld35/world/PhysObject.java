package com.smeanox.games.ld35.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public interface PhysObject {
	void addToWorld(World world);
	Body getBody();
}
