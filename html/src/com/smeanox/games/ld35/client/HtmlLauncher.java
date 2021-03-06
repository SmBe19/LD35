package com.smeanox.games.ld35.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.smeanox.games.ld35.Consts;
import com.smeanox.games.ld35.LD35;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration((int) (Consts.WIDTH * Consts.DESKTOP_SCALE), (int) (Consts.HEIGHT * Consts.DESKTOP_SCALE));
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new LD35();
        }
}