package com.smeanox.games.ld35.io;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.smeanox.games.ld35.world.Actor;
import com.smeanox.games.ld35.world.Ball;
import com.smeanox.games.ld35.world.Button;
import com.smeanox.games.ld35.world.GameWorld;
import com.smeanox.games.ld35.world.Hero;
import com.smeanox.games.ld35.world.HeroForm;
import com.smeanox.games.ld35.world.Ladder;
import com.smeanox.games.ld35.world.Platform;
import com.smeanox.games.ld35.world.Text;
import com.smeanox.games.ld35.world.Water;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelReader {

	public static GameWorld readLevel(FileHandle file) throws IOException {
		XmlReader xmlReader = new XmlReader();
		XmlReader.Element xlevel = xmlReader.parse(file);
		XmlReader.Element xdescription = xlevel.getChildByName("description");
		XmlReader.Element xhero = xlevel.getChildByName("hero");
		Hero hero = new Hero(xhero.getFloat("start_x"), xhero.getFloat("start_y"), xhero.getFloat("dest_x"), xhero.getFloat("dest_y"), HeroForm.valueOf(xhero.get("start_form", "human")));
		XmlReader.Element xplatforms = xlevel.getChildByName("platforms");
		List<Platform> platforms = new ArrayList<Platform>();
		for(XmlReader.Element xplatform : xplatforms.getChildrenByName("platform")){
			float x = xplatform.getFloat("x");
			float y = xplatform.getFloat("y");
			List<Vector2> points = new ArrayList<Vector2>();
			for(XmlReader.Element xpoint : xplatform.getChildrenByName("point")){
				points.add(new Vector2(xpoint.getFloat("x"), xpoint.getFloat("y")));
			}
			platforms.add(new Platform(xplatform.getInt("id"),
					x,
					y,
					xplatform.getFloat("width"),
					xplatform.getFloat("height"),
					points,
					Platform.PlatformType.valueOf(xplatform.get("type", "normal")),
					xplatform.getFloat("start_x", x),
					xplatform.getFloat("start_y", y),
					xplatform.getFloat("end_x", x),
					xplatform.getFloat("end_y", y),
					xplatform.getBoolean("moving_enabled", false),
					xplatform.getFloat("moving_velo", 1),
					xplatform.getFloat("hold_time", 1)));
		}
		XmlReader.Element xbuttons = xlevel.getChildByName("buttons");
		List<Button> buttons = new ArrayList<Button>();
		for (XmlReader.Element xbutton : xbuttons.getChildrenByName("button")) {
			List<Integer> toggleInteract = new ArrayList<Integer>();
			List<Integer> toggleActive = new ArrayList<Integer>();
			for(XmlReader.Element xtoggleInteract : xbutton.getChildrenByName("toggle_interact")) {
				toggleInteract.add(xtoggleInteract.getInt("dest_id"));
			}
			for(XmlReader.Element xtoggleActive : xbutton.getChildrenByName("toggle_active")) {
				toggleActive.add(xtoggleActive.getInt("dest_id"));
			}
			buttons.add(new Button(xbutton.getInt("id"),
					xbutton.getFloat("x"),
					xbutton.getFloat("y"),
					xbutton.getFloat("width"),
					xbutton.getFloat("height"),
					toggleInteract,
					toggleActive));
		}
		XmlReader.Element xladders = xlevel.getChildByName("ladders");
		List<Ladder> ladders = new ArrayList<Ladder>();
		for(XmlReader.Element xladder : xladders.getChildrenByName("ladder")){
			ladders.add(new Ladder(xladder.getInt("id"),
					xladder.getFloat("x"),
					xladder.getFloat("width"),
					xladder.getFloat("start_y"),
					xladder.getFloat("end_y"),
					xladder.getBoolean("in_air", false)));
		}
		XmlReader.Element xwaters = xlevel.getChildByName("waters");
		List<Water> waters = new ArrayList<Water>();
		for(XmlReader.Element xwater : xwaters.getChildrenByName("water")) {
			waters.add(new Water(xwater.getInt("id"),
					xwater.getFloat("x"),
					xwater.getFloat("y"),
					xwater.getFloat("width"),
					xwater.getFloat("height")));
		}
		XmlReader.Element xactors = xlevel.getChildByName("actors");
		List<Actor> actors = new ArrayList<Actor>();
		for(XmlReader.Element xactor : xactors.getChildrenByName("actor")) {
			actors.add(new Actor(xactor.getInt("id"),
					xactor.getFloat("x"),
					xactor.getFloat("y"),
					xactor.getFloat("width"),
					xactor.getFloat("height"),
					ActorTextures.valueOf(xactor.get("texture")).get(),
					xactor.getBoolean("collision", true)));
		}
		for(XmlReader.Element xball : xactors.getChildrenByName("ball")) {
			actors.add(new Ball(xball.getInt("id"),
					xball.getFloat("x"),
					xball.getFloat("y"),
					xball.getFloat("vx"),
					xball.getFloat("vy"),
					xball.getFloat("radius")));
		}
		XmlReader.Element xtexts = xlevel.getChildByName("texts");
		List<Text> texts = new ArrayList<Text>();
		for(XmlReader.Element xtext : xtexts.getChildrenByName("text")){
			texts.add(new Text(xtext.getFloat("x"),
					xtext.getFloat("y"),
					xtext.getFloat("scale"),
					xtext.get("text"),
					Color.valueOf(xtext.get("color", "ffffffff"))));
		}
		
		return new GameWorld(xdescription.getText(), actors, buttons, ladders, waters, platforms, texts, hero);
	}
}
