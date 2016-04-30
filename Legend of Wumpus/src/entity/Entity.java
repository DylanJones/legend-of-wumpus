package entity;

import java.awt.Graphics;
import java.awt.Image;

import display.World;

/**
 * This is the abstract Entity class. It is the superclass of all dynamic world
 * objects.
 */
public abstract class Entity {
	// Variables are protected so they can be accessed by subclasses
	protected int x;
	protected int y;
	protected Image sprite; // This is what is being drawn, not
							// a container for all sprite states.
	protected int health;

	// Make sure that ALL entities are registered.
	public Entity() {
		World.registerEntity(this);
	}

	/** Called every tick. AI code should go here. */
	public abstract void tick();

	/**
	 * Will be called whenever the entity collides with another entity or the
	 * side of the screen.
	 */
	public abstract void collide(Entity e);

	/**
	 * Called whenever the entity takes damage. Animations can be implemented
	 * here.
	 */
	public abstract void damage(int amount, Entity damageSource);

	/**
	 * Called every tick to draw the entity's sprite. All methods that override
	 * this should have a call to super.draw()
	 */
	public void draw(Graphics g) {
		g.drawImage(sprite, getX(), getY(), null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isAlive() {
		return health > 0;
	}
}
