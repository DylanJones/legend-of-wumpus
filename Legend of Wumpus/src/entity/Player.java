package entity;

import javax.imageio.ImageIO;

import display.World;
import display.WumpusPanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class should handle all of the things related to the Player. It should
 * NOT handle drawing the GUI (beyond drawing the player sprite).
 */
@SuppressWarnings("unused")
public final class Player extends Entity {
	private static Image northImage;
	private static Image southImage;
	private static Image eastImage;
	private static Image westImage;
	private int facing = World.NORTH;

	static {
		try {
			northImage = ImageIO.read(new File("assets/wumpus/north.png"));// .getScaledInstance(arg0,
																			// arg1,
																			// Image.SCALE_REPLICATE);
			southImage = ImageIO.read(new File("assets/wumpus/south.png"));
			eastImage = ImageIO.read(new File("assets/wumpus/east.png"));
			westImage = ImageIO.read(new File("assets/wumpus/west.png"));
		} catch (IOException e) {
			System.err.print("Error reading Player image files");
			System.exit(1);
		}
	}

	public Player() {
		this.health = 10;
		this.x = 300;
		this.y = 300;
	}

	/**
	 * @param g
	 *            the graphics object
	 */
	public void draw(Graphics g) {
		switch (facing) {
		case World.NORTH:
			g.drawImage(northImage, getX(), getY(), null);
			break;
		case World.SOUTH:
			g.drawImage(southImage, getX(), getY(), null);
			break;
		case World.EAST:
			g.drawImage(eastImage, getX(), getY(), null);
			break;
		case World.WEST:
			g.drawImage(westImage, getX(), getY(), null);
			break;
		}
	}

	@Override
	public void collide(Entity e) {
		// System.out.println("Player Collision");
	}

	@Override
	public void damage(int amount, Entity damageSource) {
		health -= amount;
		System.out.println("Player damaged! Health: " + health);
	}

	public void turnLeft() {
		facing = facing >= 3 ? 0 : facing + 1;
	}

	public int getDirection() {
		return facing;
	}

	public void tick() {
		if (health <= 0) {
			World.setGameState(2);
		}
	}

	public void move(int pixels) {
		switch (facing) {
		case World.NORTH:
			y -= pixels;
			break;
		case World.SOUTH:
			y += pixels;
			break;
		case World.EAST:
			x += pixels;
			break;
		case World.WEST:
			x -= pixels;
			break;
		}
	}
}
