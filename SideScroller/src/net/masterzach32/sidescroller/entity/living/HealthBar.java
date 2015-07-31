package net.masterzach32.sidescroller.entity.living;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

@SuppressWarnings("unused")
public class HealthBar {
	
	private EntityLiving e;
	private float health, maxHealth, hlength, length, shield = 0, maxShield = 0, slength;
	private float dhlength;
	private int width, height, owidth, oheight;
	
	private Color healthBar, damageBar = new Color(200, 0, 0), border = Color.GRAY, shieldBar = Color.BLUE;

	/**
	 * Creates a new health bar for the Entity passed.
	 * @param entity
	 * @param width
	 * @param height
	 * @param color
	 */
	public HealthBar(EntityLiving entity, int width, int height, Color color) {
		e = entity;
		healthBar = color;
		this.width = width;
		this.height = height;
		dhlength = width;
		owidth = e.getWidth();
		oheight = e.getHeight();
	}
	
	private void tick() {
		// health bar
		health = e.health;
		maxHealth = e.maxHealth;
		shield = e.shield;
		maxShield = e.maxShield;
		
		float maxTotal;
		if(health + shield < maxHealth) maxTotal = maxHealth;
		else maxTotal = maxHealth + (health + shield - maxHealth);
		float healthPercent = health / maxTotal;
		float shieldPercent = shield / maxTotal;
			
		hlength = healthPercent * width;
		slength = shieldPercent * width;
		length = hlength + slength;
		
		//if(shield > 0) slength += 1;
			
		// damage bar
		if(length >= dhlength) dhlength = (float) (length - 1);
		if(length < dhlength) dhlength -= .7;
	}
	
	public void render(Graphics2D g) {
		tick();
		
		Point p = e.getScreenLocation();
		int x = p.x;
		int y = p.y;
		
		// FIXME: Fix render bugs - float to int rounding differences.
		
		// health bar
		g.setColor(border);
		g.drawRect((int) (x - width / 2), (int) (y - oheight / 2), (int) width, height);
		g.setColor(damageBar);
		g.fillRect((int) (x - width / 2) + 1, (int) (y - oheight / 2) + 1, (int) dhlength - 1, height - 1);
		g.setColor(healthBar);
		g.fillRect((int) (x - width / 2) + 1, (int) (y - oheight / 2) + 1, (int) hlength - 1, height - 1);
		g.setColor(shieldBar);
		g.fillRect((int) (x - width / 2 + hlength), (int) (y - oheight / 2) + 1, (int) slength, height - 1);
		
		// render effects HUD
		for(int i = 0; i < e.effects.size(); i++) {
			e.effects.get(i).render(g, (int) (x - owidth / 2), (int) (y - oheight / 2), i);
		}
	}
}
