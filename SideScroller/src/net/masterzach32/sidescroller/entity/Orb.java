package net.masterzach32.sidescroller.entity;
import java.awt.*;
import java.awt.image.BufferedImage;

import net.masterzach32.sidescroller.assets.Assets;
import net.masterzach32.sidescroller.gamestate.LevelState;
import net.masterzach32.sidescroller.tilemap.TileMap;

public class Orb extends MapObject {
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private double range, range2, r0, r1, r2;
	private int stage;
	
	public Orb(TileMap tm, double vx, double vy, boolean right) {
		super(tm);
		
		x = LevelState.getPlayer().getx();
		y = LevelState.getPlayer().gety();
		
		int speed = 6;
		range = 120;
		range2 = 55;
		stage = 1;
		
		facingRight = right;
		if(facingRight) moveSpeed = speed;
		else moveSpeed = -speed;
		
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		
		// load sprites
		try {
			BufferedImage spritesheet = Assets.getImageAsset("fireball");
			
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Call this on an entity that has been hit
	 */
	public void setHit() {
		if(hit) return;
		hit = true;
	}

	private void getNextPosition() {
		if(stage == 1) {
			dx = moveSpeed;
			r0 += moveSpeed;
			if(r0 >= range) {
				stage = 2;
				r0 = 0;
			}
		}
		if(stage == 2) {
			dx = (moveSpeed/6);
			r1 += (moveSpeed/6);
			if(r1 >= range2) {
				stage = 3;
				r1 = 0;
			}
		}
		if(stage == 3) {
			dx = -moveSpeed;
			r2 += moveSpeed; 
			if(r2 >= (range + range2)) {
				stage = 0;
				r2 = 0;
			}
		}
	}
	
	public void tick() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.tick();
	}
	
	public void render(Graphics2D g) {
		setMapPosition();
		super.render(g);
	}
	
	public boolean removeOrb() {
		if(stage == 0) return true;
		else return false;
	}
}