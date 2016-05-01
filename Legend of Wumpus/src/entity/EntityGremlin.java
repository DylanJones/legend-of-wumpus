package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;

import display.World;
import display.WumpusPanel;

public class EntityGremlin extends EntityMinion {
	private static Image gremlinNorth;
	private static Image gremlinSouth;
	private static Image gremlinEast;
	private static Image gremlinWest;
	private int facing = World.NORTH;
	private long lastAttackTime = 0;
	
	static {
		try {
			gremlinNorth = ImageIO.read(new File("assets/gremlin/gremlin-north.png")).getScaledInstance(32, 32,Image.SCALE_FAST);
			gremlinSouth = ImageIO.read(new File("assets/gremlin/gremlin-south.png")).getScaledInstance(32, 32,Image.SCALE_FAST);
			gremlinEast = ImageIO.read(new File("assets/gremlin/gremlin-east.png")).getScaledInstance(32, 32,Image.SCALE_FAST);
			gremlinWest = ImageIO.read(new File("assets/gremlin/gremlin-west.png")).getScaledInstance(32, 32,Image.SCALE_FAST);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error reading Gremlin images");
			System.exit(1);
		}
	}

	public EntityGremlin(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.health = 100;
		this.sprite = gremlinNorth;
	}

	@Override
	public void collide(Entity e) {
		attack(e);
	}

	@Override
	public void damage(int amount, Entity damageSource) {
		System.out.println("gremlin damage");
		health -= amount;
	}

	@Override
	public void attack(Entity target) {
		if (System.currentTimeMillis() - lastAttackTime > 1000 && target instanceof Player) {
			lastAttackTime = System.currentTimeMillis();
			target.damage(2, this);
		}
	}

	@Override
	public void tick() {
		int playerX = World.getThePlayer().getX();
		int playerY = World.getThePlayer().getY();
		if (Math.abs(this.x - playerX) + Math.abs(this.y - playerY) < 300) {
			if (Math.abs(this.x - playerX) > Math.abs(this.y - playerY)) {
				int increment = (x - playerX) > 1 ? -1 : 1;
				if(increment > 0) {
					facing = World.EAST;
				}
				else {
					facing = World.WEST;
				}
				this.x += increment;
			} else {
				int increment = (y - playerY) > 1 ? -1 : 1;
				if(increment > 0) {
					facing = World.SOUTH;
				}
				else {
					facing = World.NORTH;
				}
				this.y += increment;
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		switch(facing) {
		case World.NORTH:
			g.drawImage(gremlinNorth, x, y, null);
			break;
		case World.SOUTH:
			g.drawImage(gremlinSouth, x, y, null);
			break;
		case World.EAST:
			g.drawImage(gremlinEast, x, y, null);
			break;
		case World.WEST:
			g.drawImage(gremlinWest, x, y, null);
			break;
		}
	}
}