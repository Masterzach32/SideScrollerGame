package net.masterzach32.sidescroller.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import net.masterzach32.sidescroller.assets.Assets;
import net.masterzach32.sidescroller.assets.gfx.ImageLoader;
import net.masterzach32.sidescroller.assets.sfx.AudioLoader;
import net.masterzach32.sidescroller.gamestate.*;
import net.masterzach32.sidescroller.util.LogHelper;

@SuppressWarnings("serial")
public class SideScroller extends JPanel implements Runnable, KeyListener, MouseListener {
	
	// dimensions
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final int SCALE = 2;
	public static final String VERSION = "0.0.1.045";
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	// states
	public static MenuState menuState;
	public static Level1State level1;
	public static Level2State level2;
	
	public static ImageLoader im = new ImageLoader();
	public static AudioLoader am = new AudioLoader();
	
	public SideScroller() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	/**
	 * Called once before the game runs, Initializes objects and assets
	 */
	private void init() {
		LogHelper.logInfo("Launching Game");
		LogHelper.logInfo("OS: " + System.getProperty("os.name"));
		LogHelper.logInfo("OS Version: " + System.getProperty("os.version"));
		LogHelper.logInfo("OS Archetecture: " + System.getProperty("os.arch"));
		LogHelper.logInfo("Java Version: " + System.getProperty("java.version"));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		Assets.init(); 
		
		menuState = new MenuState(this);
		level1 = new Level1State(this);
		level2 = new Level2State(this);
		GameState.setState(menuState);
	}
	
	/**
	 * Game loop
	 */
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
			tick();
			render();
			renderToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void tick() {
		if(GameState.getState() != null)
			GameState.getState().tick();
	}
	
	private void render() {
		if(GameState.getState() != null)
			GameState.getState().render(g);
	}
	
	private void renderToScreen() {
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
	}
	
	public void keyPressed(KeyEvent key) {
		if(GameState.getState() != null)
			GameState.getState().keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key) {
		if(GameState.getState() != null)
			GameState.getState().keyReleased(key.getKeyCode());
	}
	
	public void keyTyped(KeyEvent key) {}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}