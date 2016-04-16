package com.smeanox.games.ld35.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum Textures {
	spritesheet("img/spritesheet.png"),
	bg1("img/bg1.png"),
	;

	private Texture texture;

	Textures(String file){
		texture = new Texture(Gdx.files.internal(file));
	}

	public Texture get(){
		return texture;
	}
}
