package com.smeanox.games.ld35.world.narrator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum NarratorSounds {
	lvl1_1("nar/Lvl1_1.mp3"),
	lvl1_2("nar/Lvl1_2.mp3"),
	lvl1_3("nar/Lvl1_3.mp3"),
	lvl1_4("nar/Lvl1_4.mp3"),
	lvl1_5("nar/Lvl1_5.mp3"),
	lvl1_6("nar/Lvl1_6.mp3"),
	lvl1_7("nar/Lvl1_7.mp3"),
	lvl1_8("nar/Lvl1_8.mp3"),
	lvl1_9("nar/Lvl1_9.mp3"),
	lvl1_10("nar/Lvl1_10.mp3"),
	lvl2_1("nar/Lvl2_1.mp3"),
	lvl2_2("nar/Lvl2_2.mp3"),
	lvl2_3("nar/Lvl2_3.mp3"),
	lvl2_4("nar/Lvl2_4.mp3"),
	lvl2_5("nar/Lvl2_5.mp3"),
	lvl2_6("nar/Lvl2_6.mp3"),
	lvl2_7("nar/Lvl2_7.mp3"),
	lvl3_1("nar/Lvl3_1.mp3"),
	lvl3_2("nar/Lvl3_2.mp3"),
	lvl3_3("nar/Lvl3_3.mp3"),
	lvl3_4("nar/Lvl3_4.mp3"),
	lvl3_5("nar/Lvl3_5.mp3"),
	lvl3_6("nar/Lvl3_6.mp3"),
	lvl3_7("nar/Lvl3_7.mp3"),
	lvl3_8("nar/Lvl3_8.mp3"),
	lvl3_9("nar/Lvl3_9.mp3"),
	lvl3_10("nar/Lvl3_10.mp3"),
	lvl3_11("nar/Lvl3_11.mp3"),
	lvl3_12("nar/Lvl3_12.mp3"),
	lvl3_13("nar/Lvl3_13.mp3"),
	lvl4_1("nar/Lvl4_1.mp3"),
	lvl4_2("nar/Lvl4_2.mp3"),
	lvl5_1("nar/Lvl5_1.mp3"),
	lvl5_2("nar/Lvl5_2.mp3"),
	lvl5_3("nar/Lvl5_3.mp3"),
	lvl5_4("nar/Lvl5_4.mp3"),
	lvl5_5("nar/Lvl5_5.mp3"),
	lvl6_1("nar/Lvl6_1.mp3"),
	lvl6_2("nar/Lvl6_2.mp3"),
	lvl7_1("nar/Lvl7_1.mp3"),
	lvl7_2("nar/Lvl7_2.mp3"),
	lvl8_1("nar/Lvl8_1.mp3"),
	lvl8_2("nar/Lvl8_2.mp3"),
	lvl8_3("nar/Lvl8_3.mp3"),
	lvlX_1("nar/LvlX_1.mp3"),
	lvlX_2("nar/LvlX_2.mp3"),
	lvlX_3("nar/LvlX_3.mp3"),
	lvlX_4("nar/LvlX_4.mp3"),
	;

	private Music music;
	private String musicFile;

	NarratorSounds(String musicFile) {
		this.musicFile = musicFile;
	}

	public Music get(){
		if(music == null){
			music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
		}
		return music;
	}

	public void dispose(){
		music.dispose();
		music = null;
	}
}
