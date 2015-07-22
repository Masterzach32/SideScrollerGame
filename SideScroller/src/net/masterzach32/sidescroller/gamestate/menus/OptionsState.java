package net.masterzach32.sidescroller.gamestate.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import net.masterzach32.sidescroller.assets.Assets;
import net.masterzach32.sidescroller.entity.MapObject;
import net.masterzach32.sidescroller.gamestate.GameState;
import net.masterzach32.sidescroller.main.Game;
import net.masterzach32.sidescroller.main.SideScroller;
import net.masterzach32.sidescroller.tilemap.Background;
import net.masterzach32.sidescroller.util.Utilities;

public class OptionsState extends MenuState {

	private Font subtitleFont;
	
	public static int currentChoice;
	public static String[] options = new String[8]; 
	private static boolean console = true, debug = true;
	
	public OptionsState(SideScroller game) {
		super(game);
	}

	public void init() {
		bg = new Background(Assets.getImageAsset("grassbg"), 1);
		bg.setVector(-0.25, 0);
		
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Century Gothic", Font.BOLD, 32);
		subtitleFont = new Font("Century Gothic", Font.BOLD, 20);
			
		font = new Font("Arial", Font.PLAIN, 12);
		selectfont = new Font("Arial", Font.PLAIN, 14);
	}

	protected void load() {
		currentChoice = 0;
	}

	protected void unload() {}

	public void tick() {
		bg.tick();
	}

	public void render(Graphics2D g) {
		options[0] = "Scale: " + "(" + (SideScroller.SCALE) + "x)"; 
		options[1] = "Resolution: " + "(" + SideScroller.WIDTH * SideScroller.SCALE + "x" + SideScroller.HEIGHT * SideScroller.SCALE + ")";
		if(SideScroller.isSoundEnabled) options[2] = "Sound: (ENABLED)";
		else options[2] = "Sound: (DISABLED)";
		options[3] = "TPS (ticks per second): (" + SideScroller.FPS + ")";
		if(console) options[4] = "Show Console: (TRUE)";
		else options[4] = "Show Console: (FALSE)";
		options[5] = "Key Bindings Menu";
		if(debug) options[6] = "Debug Mode: (ENABLED)";
		else options[6] = "Debug Mode: (DISABLED)";
		options[7] = "Back";
		// draw bg
		bg.render(g);						
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		Utilities.drawCenteredString(g, "SideScroller Project", 45);
		g.setFont(subtitleFont);
		Utilities.drawCenteredString(g, "Options", 75);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(info, 290, 355);
						
		// draw menu options
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setFont(selectfont);
				g.setColor(Color.BLACK);
			} else {
				g.setFont(font);
				g.setColor(Color.RED);
			}
			Utilities.drawCenteredString(g, options[i], (300 + i * 30) / 2);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			if(SideScroller.SCALE >= 4) {
				SideScroller.SCALE = 1;
			} else {
				SideScroller.SCALE += 1;
			}
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension((int) (SideScroller.WIDTH * SideScroller.SCALE), (int) (SideScroller.HEIGHT * SideScroller.SCALE + 20));
			int x = (int) ((screenSize.width/2)-(frameSize.width/2));
			int y = (int) ((screenSize.height/2)-(frameSize.height/2));
			Game.getFrame().setSize(frameSize);
			Game.getFrame().setLocation(x, y);
		}
		if(currentChoice == 1) {
			if(SideScroller.WIDTH == 640) {
				SideScroller.WIDTH = 512;
				SideScroller.HEIGHT = 384;
			} else {
				SideScroller.WIDTH = 640;
				SideScroller.HEIGHT = 360;
			}
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension((int) (SideScroller.WIDTH * SideScroller.SCALE), (int) (SideScroller.HEIGHT * SideScroller.SCALE + 20));
			int x = (int) ((screenSize.width/2)-(frameSize.width/2));
			int y = (int) ((screenSize.height/2)-(frameSize.height/2));
			Game.getFrame().setSize(frameSize);
			Game.getFrame().setLocation(x, y);
		}
		if(currentChoice == 2) {
			if (SideScroller.isSoundEnabled) {
				SideScroller.isSoundEnabled = false;
			} else {
				SideScroller.isSoundEnabled = true;
			}
		}
		if(currentChoice == 3) {
			SideScroller.FPS += 20;
			if (SideScroller.FPS >= 140) {
				SideScroller.FPS = 20;
			}
		}
		if(currentChoice == 4) {
			if(console) {
				console = false;
			} else {
				console = true;
			}
			Game.getConsole().setVisible(console);
			SideScroller.getGame().requestFocus();
		}
		if(currentChoice == 5) {
			GameState.setState(SideScroller.keyConfigState);
		}
		if(currentChoice == 6) {
			if (debug) {
				debug = false;
			} else {
				debug = true;
				console = true;
			}
			MapObject.setShowHitbox(debug);
			Game.getConsole().setVisible(console);
			SideScroller.getGame().requestFocus();
		}
		if(currentChoice == 7) {
			GameState.setState(SideScroller.menuState);
		}
	}
	
	public static boolean isDebugEnabled() {
		return debug;
	}

	public static boolean isConsoleEnabled() {
		return console;
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		} if(k == KeyEvent.VK_ESCAPE) {
			GameState.setState(SideScroller.menuState);
		} if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}

	public void keyReleased(int k) {}

	public void mousePressed(int k) {}

	public void mouseReleased(int k) {}
	
}
