package net.masterzach32.sidescroller.entity.living;

import net.masterzach32.sidescroller.entity.MapObject;
import net.masterzach32.sidescroller.tilemap.TileMap;

public class EntityLiving extends MapObject {
	
	public int health, maxHealth, shield, maxShield;
	
	public HealthBar healthBar;

	public EntityLiving(TileMap tm) {
		super(tm);
	}

	
}