package com.mygdx.joemac;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.joemac.joeMacCavermanNinja.JoeMac;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class JogoStarter {
	public static void main (String[] arg) {
		int largura = 1280, altura = 720;
		Lwjgl3ApplicationConfiguration configura = new Lwjgl3ApplicationConfiguration();
		configura.setForegroundFPS(60);
		configura.setTitle("Joe & Mac Caverman Ninja");
		configura.setWindowedMode(largura, altura);
		configura.setResizable(false);
		new Lwjgl3Application(new JoeMac(), configura);
	}
}




