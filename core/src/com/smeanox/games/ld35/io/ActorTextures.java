package com.smeanox.games.ld35.io;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smeanox.games.ld35.Consts;

public enum ActorTextures {
	turtle(Textures.spritesheet.get(), 0*Consts.TEX_WIDTH_HERO, 1*Consts.TEX_HEIGHT_HERO, Consts.TEX_WIDTH_HERO, Consts.TEX_HEIGHT_HERO),
	;

	private TextureRegion textureRegion;

	ActorTextures(Texture texture, int x, int y, int width, int height) {
		this.textureRegion = new TextureRegion(texture, x, y, width, height);
	}

	public TextureRegion get() {
		return textureRegion;
	}
}
