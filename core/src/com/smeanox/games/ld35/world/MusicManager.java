package com.smeanox.games.ld35.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.smeanox.games.ld35.Consts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicManager {

	private List<String> musics;
	private Music currentMusic;
	private float musicPause;
	private Random rand;


	public MusicManager() {
		rand = new Random(System.currentTimeMillis());

		musics = new ArrayList<String>();
		musics.add("msc/Song001.mp3");
		musics.add("msc/Song002.mp3");
		musics.add("msc/Song003.mp3");
	}

	public void update(float delta){
		if(!Consts.MUSIC_ENABLED){
			return;
		}
		if(currentMusic == null){
			musicPause -= delta;
			if(musicPause <= 0){
				int rnd = rand.nextInt(musics.size());
				currentMusic = Gdx.audio.newMusic(Gdx.files.internal(musics.get(rnd)));
				currentMusic.setVolume(Consts.MUSIC_VOLUME);
				currentMusic.play();
			}
		} else{
			if(!currentMusic.isPlaying()){
				currentMusic.dispose();
				currentMusic = null;
				musicPause = Consts.MUSIC_PAUSE;
			}
		}
	}

	public void stop(){
		if(currentMusic != null){
			currentMusic.stop();
			currentMusic.dispose();
			currentMusic = null;
		}
	}
}
