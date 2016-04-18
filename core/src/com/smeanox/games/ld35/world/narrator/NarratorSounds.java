package com.smeanox.games.ld35.world.narrator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum NarratorSounds {
	lvl1_1("nar/Lvl1_1.mp3", 15.05f, new String[]{"Ah, I see you have touched my golden orb of infinity.", "For that reason, I will reward you with a nice little gift:", "From now on, you will be able to transform yourself into", "a wolf or a turtle at your will. However, there is a catch to this."}),
	lvl1_2("nar/Lvl1_2.mp3", 11.21f, new String[]{"Hey! I wasn't even done with my monologue and you already died.", "That's no fun! But I will give you a second chance at this.", "How about you try to transform into a wolf to outrun these rocks?"}),
	lvl1_3("nar/Lvl1_3.mp3", 04.73f, new String[]{"My grandma always used to say: 'Don't get crushed by falling rocks'. Try again!"}),
	lvl1_4("nar/Lvl1_4.mp3", 07.01f, new String[]{"Again? Well I guess everyone learns at their own pace. I'll let you have as much time as you want with this."}),
	lvl1_5("nar/Lvl1_5.mp3", 04.71f, new String[]{"Ah, you finally made it, Congratulations. Anyway..."}),
	lvl1_6("nar/Lvl1_6.mp3", 03.82f, new String[]{"That's how it's done. It wasn't that hard, was it? Anyway..."}),
	lvl1_7("nar/Lvl1_7.mp3", 08.72f, new String[]{"That's better. I could have saved you the pain of dying once if I told you this right away, but honestly that would have been much less enjoyable. Anyway..."}),
	lvl1_8("nar/Lvl1_8.mp3", 02.43f, new String[]{"Ah yeah, I was talking about the catch..."}),
	lvl1_9("nar/Lvl1_9.mp3", 07.85f, new String[]{"I have a job for you: Get to the top of this mountain. Sounds nice? Ok, do it! No questions please, 'JUST DO IT'"}),
	lvl1_10("nar/Lvl1_10.mp3", 08.53f, new String[]{"That wasn't the catch yet, that was just a cheap attempt at murdering you. Good job dodging that, by the way. The actual catch is this: ..."}),
	lvl2_1("nar/Lvl2_1.mp3", 08.05f, new String[]{"Be careful, falling rocks right there. Oh, too late, my bad. Maybe you could try transforming into your turtle shell to survive this?"}),
	lvl2_2("nar/Lvl2_2.mp3", 06.58f, new String[]{"Studies have shown that falling rocks can be very unhealthy.", "I suggest you use your turtle form to protect yourself."}),
	lvl2_3("nar/Lvl2_3.mp3", 03.75f, new String[]{"Take as long as you need. It's not like you have anything important to do."}),
	lvl2_4("nar/Lvl2_4.mp3", 07.01f, new String[]{"That's the way you do it. You better remember it, because falling rocks are a recurring theme on this mountain. Also, there's a bear."}),
	lvl2_5("nar/Lvl2_5.mp3", 07.23f, new String[]{"Impressive reflexes. I guess I will have to try harder next time... Maybe use the bear... Did I just say that out loud?"}),
	lvl2_6("nar/Lvl2_6.mp3", 05.74f, new String[]{"Unfortunatley, these rocks destroyed the bridge that used to be here, so you will have to find another way."}),
	lvl2_7("nar/Lvl2_7.mp3", 07.16f, new String[]{"Hmm, it seems like you can't swim. I will leave this hint to you: Turtles can swim. (Bears as well)"}),
	lvl3_1("nar/Lvl3_1.mp3", 06.05f, new String[]{"Oh look, a rope. This calls your human form's greatest strength: Opposable thumbs."}), // opposing???
	lvl3_2("nar/Lvl3_2.mp3", 12.89f, new String[]{"Thirty four. Oh don't mind me, I'm just counting how many times people thought they could make that jump.", "Yeah, you're not the first one. I've given plenty of people transformations before. Maybe your turtle shell will be able to take the fall."}),
	lvl3_3("nar/Lvl3_3.mp3", 04.59f, new String[]{"Thirty five. Fun fact: The bear's body count is at 142"}),
	lvl3_4("nar/Lvl3_4.mp3", 00.94f, new String[]{"Thirty six."}),
	lvl3_5("nar/Lvl3_5.mp3", 01.59f, new String[]{"Thirty seven."}),
	lvl3_6("nar/Lvl3_6.mp3", 01.18f, new String[]{"Thirty eight."}),
	lvl3_7("nar/Lvl3_7.mp3", 00.75f, new String[]{"Forty"}),
	lvl3_8("nar/Lvl3_8.mp3", 00.99f, new String[]{"Forty one"}),
	lvl3_9("nar/Lvl3_9.mp3", 00.99f, new String[]{"Forty one"}),
	lvl3_10("nar/Lvl3_10.mp3", 01.35f, new String[]{"Forty two"}),
	lvl3_11("nar/Lvl3_11.mp3", 04.71f, new String[]{"It seems my counting does not discourage you from dying. I guess I'll have to do it quietly."}),
	lvl3_12("nar/Lvl3_12.mp3", 06.08f, new String[]{"Nicely done. There are some buttons in this area. Maybe your human form can use them. (The Bear can't)"}),
	lvl3_13("nar/Lvl3_13.mp3", 04.06f, new String[]{"A moving platform! I kindly ask you to not think about how this is phsically possible."}),
	lvl4_1("nar/Lvl4_1.mp3", 13.73f, new String[]{"Now this one is a little more complicated. About 73% of all people can't make it.", "Also, I just made that number up, so don't let it discourage you.","By the way, if you find old corpses, don't worry. The bear will get them sooner or later."}),
	lvl4_2("nar/Lvl4_2.mp3", 04.78f, new String[]{"See, stones can be helpful. Although I'm not sure if the same could be said about bears."}),
	lvl5_1("nar/Lvl5_1.mp3", 05.38f, new String[]{"Oh, you're gonna love this part.", "At least if you like the whole running and jumping and falling to your death aspect of it."}),
	lvl5_2("nar/Lvl5_2.mp3", 06.63f, new String[]{"Jumping on moving platforms? That seems tricky.", "Imagine how exciting it would be if there were alligators in the pit below."}),
	lvl5_3("nar/Lvl5_3.mp3", 03.25f, new String[]{"Yep, alligators are definetely on my grocery list."}),
	lvl5_4("nar/Lvl5_4.mp3", 11.38f, new String[]{"Huh, this is taking a while. Ill have you know that 73% of all people would have already finished this by now.", "I really wanted you to help me cure my boredom, but at the moment, it's getting worse."}),
	lvl5_5("nar/Lvl5_5.mp3", 07.21f, new String[]{"Finally. At last we're getting to more exciting bears.", "Oh I mean parts. Sorry, that was a Freudian slip."}),
	lvl6_1("nar/Lvl6_1.mp3", 05.74f, new String[]{"Did you know that bears are from the family of ursidae?", "I don't really know how to pronounce it, but I'm sure it's awesome."}),
	lvl6_2("nar/Lvl6_2.mp3", 07.33f, new String[]{"So, how is this level treating you? Is it bearable? Hahaha. Never gets old."}),
	lvl7_1("nar/Lvl7_1.mp3", 07.76f, new String[]{"Hey, what's your favourite animal?", "Most people think mine is the bear, but I'm really not sure why they'd think that.", "It's actually the giraffe."}),
	lvl7_2("nar/Lvl7_2.mp3", 05.36f, new String[]{"Come to think of it: I've never let someone shapeshift into a bear before.", "That would be fascinating."}),
	lvl8_1("nar/Lvl8_1.mp3", 11.33f, new String[]{"I honestly didn't expect that you'd make it this far. Good on you, I guess.", "All that's left to do now is to touch this auric shere of destiny. I may or may not be making these names up on the spot."}),
	lvl8_2("nar/Lvl8_2.mp3", 17.57f, new String[]{"And here it comes: the thung we've all been waiting for: The Bea...", "wait what? There's no bear? Ah right, it was cut due to budget reasons.", "Damn. All that hype for nothing. Whatever. I think this will serve as a nice replacement."}),
	lvl8_3("nar/Lvl8_3.mp3", 09.58f, new String[]{"You've done it! You've climbed the mountain.", "Well done, congratulations, et cetera, et cetera.", "Now if you don't mind, I have new transformations to design."}),
	lvlX_1("nar/LvlX_1.mp3", 08.45f, new String[]{"And remember: You can turn yourself into a dragon at any time. Then you can just fly to the mountaintop...", "Oh wait, that wasn't you, that was the last guy."}),
	lvlX_2("nar/LvlX_2.mp3", 04.59f, new String[]{"I once gave a lizard the ability to transform into a human. He later became president."}),
	lvlX_3("nar/LvlX_3.mp3", 06.10f, new String[]{"You know, I've been thinking.", "What if I just helped you get up there?", "Hahaha, nope"}),
	lvlX_4("nar/LvlX_4.mp3", 10.37f, new String[]{"Ahm I see you have touched my glowing sphere of eternity, For that reason, I...", "ah hold on, you're still going, right?", "Well I was already starting with a new guy because you're taking so long."}),
	;

	private Music music;
	private float duration;
	private String musicFile;
	private String[] subtitles;

	NarratorSounds(String musicFile, float duration, String[] subtitles) {
		this.musicFile = musicFile;
		this.duration = duration;
		this.subtitles = subtitles;
	}

	public Music get(){
		if(music == null){
			music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
		}
		return music;
	}

	public String[] getSubtitles() {
		return subtitles;
	}

	public String getCurrentSubtitle() {
		if (music == null)
			return null;
		int ix = (int)(Math.floor(subtitles.length * music.getPosition() / duration));
		if (ix >= 0 && ix < subtitles.length) {
			return subtitles[ix];
		}
		return null;
	}

	public float getDuration() {
		return duration;
	}

	public void dispose(){
		music.dispose();
		music = null;
	}
}
