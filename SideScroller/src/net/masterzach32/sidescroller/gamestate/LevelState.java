package net.masterzach32.sidescroller.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.masterzach32.sidescroller.assets.Assets;
import net.masterzach32.sidescroller.assets.gfx.HUD;
import net.masterzach32.sidescroller.assets.sfx.AudioPlayer;
import net.masterzach32.sidescroller.entity.Animation;
import net.masterzach32.sidescroller.entity.EntityPlayer;
import net.masterzach32.sidescroller.main.SideScroller;
import net.masterzach32.sidescroller.tilemap.TileMap;

public abstract class LevelState extends GameState {
	
	protected static EntityPlayer player;
	protected static TileMap tileMap;
	protected static int i = 0;
	protected static HUD hud;
	protected static Animation animation;
	protected static int width, height;
	protected static BufferedImage[] sprites;
	protected static AudioPlayer spawnSound;

	public LevelState(SideScroller game) {
		super(game);
	}
	
	public static void loadLevels() {
		tileMap = new TileMap(30);
		player = new EntityPlayer(tileMap);
		player.loadSave();
		
		// load assets
		hud = new HUD(player);
		spawnSound = new AudioPlayer(Assets.getAudioAsset("spawn2"));
		
		BufferedImage spritesheet = Assets.getImageAsset("spawn_animation_p");
		
		width = 30;
		height = 30;
		sprites = new BufferedImage[8];
		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(125);
	}
	
	/**
	 * Only use this with game levels, called when the level is completed
	 */
	public abstract void levelCompleted();
	
	/**
	 * Spawns the enemies on the map
	 */
	protected abstract void populateEnemies();
	
	public static EntityPlayer getPlayer() {
		return player;
	}
	
	protected void renderSpawnAnimation(Graphics2D g) {
		if(i <= 60) i++;
		if(!animation.hasPlayedOnce()) {
			animation.tick();
			g.drawImage(animation.getImage(), player.getx() - width / 2, player.gety() - height / 2, null);
		} else {
			
		}
	}
}
