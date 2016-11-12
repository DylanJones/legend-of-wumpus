package entity;

import javax.imageio.ImageIO;

import display.Direction;
import display.World;
import display.MusicPlayer;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * This class should handle all of the things re lated to the Player. It should
 * NOT handle drawing the GUI (beyond drawing the player sprite).
 */
public final class Player extends Entity {
	/**
	 * Serial ID for Serialization to disk
	 */
	private static final long serialVersionUID = -7931553165420215402L;
	private static final int ATTACK_TIME = 500; // attack duration in millis
	private static final int ATTACK_COOLDOWN = 200; // attack cooldown in millis
	private static int maxHealth = 16; // Maximum health the player can have
	// Player's Images
	private static Image northImage1;
	private static Image northImage2;
	private static Image southImage1;
	private static Image southImage2;
	private static Image eastImage1;
	private static Image eastImage2;
	private static Image westImage1;
	private static Image westImage2;
	private static Image northAttackImage;
	private static Image southAttackImage;
	private static Image eastAttackImage;
	private static Image westAttackImage;

	private boolean canTakeDamage = true;
	private long lastDamageTime = 0; // Last time something attacked it
	private long attackStartTime = 0; // Last time it attacked
	private int triforcePieces = 0; // Number of triforce pieces the player has

	static {
		// Load images into the variables they belong to
		try {
			// Walking images
			northImage1 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/north1.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			northImage2 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/north2.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			southImage1 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/south1.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			southImage2 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/south2.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			eastImage1 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/east1.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			eastImage2 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/east2.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			westImage1 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/west1.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			westImage2 = ImageIO.read(
					Player.class.getResource("/assets/wumpus/west2.png"))
					.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
			// Attacking images
			northAttackImage = ImageIO
					.read(Player.class
							.getResource("/assets/wumpus/attack_north.png"))
					.getScaledInstance(32, 56, Image.SCALE_REPLICATE);
			southAttackImage = ImageIO
					.read(Player.class
							.getResource("/assets/wumpus/attack_south.png"))
					.getScaledInstance(32, 54, Image.SCALE_REPLICATE);
			eastAttackImage = ImageIO.read(
					Player.class.getResource("/assets/wumpus/attack_east.png"))
					.getScaledInstance(54, 30, Image.SCALE_REPLICATE);
			westAttackImage = ImageIO.read(
					Player.class.getResource("/assets/wumpus/attack_west.png"))
					.getScaledInstance(54, 30, Image.SCALE_REPLICATE);
		} catch (Exception e) {
			// Error Handling
			System.err.print("Error reading Player image files");
			System.exit(1);
		}
	}

	public Player() {
		this.health = maxHealth; // Player starts with full health
		this.spriteHeight = northImage1.getHeight(null); // Player's height is
															// assigned
		this.spriteWidth = northImage1.getWidth(null); // Player's width is
														// assigned
		// Starting coordinates and direction
		this.x = 5;
		this.y = 5;
		this.facing = Direction.NORTH;
	}

	/**
	 * @param g
	 *            the graphics object to draw on
	 */
	// Draws Player sprite on screen
	@Override
	public void draw(Graphics2D g) {
		int[] sCoords = World.getScreenCoordinates(x, y);
		sCoords[0] = sCoords[0] - spriteWidth / 2;
		sCoords[1] = sCoords[1] - spriteHeight / 2;
		boolean whichImage = (((int) (x * 2) % 2) == 1)
				^ (((int) (y * 2) % 2) == 1);
		Image imageToDraw;
		switch (facing) {
		case NORTH:
			if (System.currentTimeMillis() - this.attackStartTime < ATTACK_TIME)
				imageToDraw = northAttackImage;
			else if (whichImage)
				imageToDraw = northImage1;
			else
				imageToDraw = northImage2;
			break;
		case SOUTH:
			if (System.currentTimeMillis() - this.attackStartTime < ATTACK_TIME)
				imageToDraw = southAttackImage;
			else if (whichImage)
				imageToDraw = southImage1;
			else
				imageToDraw = southImage2;
			break;
		case EAST:
			if (System.currentTimeMillis() - this.attackStartTime < ATTACK_TIME)
				imageToDraw = eastAttackImage;
			else if (whichImage)
				imageToDraw = eastImage1;
			else
				imageToDraw = eastImage2;
			break;
		case WEST:
			if (System.currentTimeMillis() - this.attackStartTime < ATTACK_TIME)
				imageToDraw = westAttackImage;
			else if (whichImage)
				imageToDraw = westImage1;
			else
				imageToDraw = westImage2;
			break;
		default:
			System.err.println("Invalid player facing");
			imageToDraw = null;
		}
		setHitbox(imageToDraw);
		g.drawImage(imageToDraw, sCoords[0], sCoords[1], null);
	}

	// Entity collision system, called when an entity collides with a Player
	@Override
	public void collide(Entity e) {
		if (System.currentTimeMillis() - this.attackStartTime < ATTACK_TIME)
			e.damage(2, this);
	}

	// Called when something collides with Player
	@Override
	public void damage(int amount, Entity damageSource) {
		if (System.currentTimeMillis() - lastDamageTime > 1000 && canTakeDamage) {
			lastDamageTime = System.currentTimeMillis();
			// We're invulrnaberale while attacking
			if (System.currentTimeMillis() - this.attackStartTime > ATTACK_TIME) {
				if (health > 0 && canTakeDamage) {// Stops Player from having
													// negative health
					health -= amount;
					if (health <= 0) { // did it go below 0?
						World.setGameState(2);
					}
				}
				System.out.println("Player damaged! Health: " + health);
				MusicPlayer.playSoundEffect("/assets/music/Hurt.wav");
			}
		}
	}

	// Function for player to turnLeft on the screen; similar function to karel
	public void turnLeft() {
		facing = facing.getLeft();
	}

	// Required by superclass
	public void tick() {
	}

	// Movment method for player
	public void move(double amount) {
		boolean canMove = !World.willCollideTile(this, amount);
		// Can't move while attacking
		if (System.currentTimeMillis() - this.attackStartTime < ATTACK_TIME)
			canMove = false;
		// Move in specified direction
		if (canMove) {
			x = facing.moveInDirection(x, y, amount)[0];
			y = facing.moveInDirection(x, y, amount)[1];
			if (x < 0) {
				x = 15.9;
				World.loadWorld(Direction.WEST);
			}
			if (x > 15.9) {
				x = 0.01;
				World.loadWorld(Direction.EAST);
			}
			if (y < 0.01) {
				y = 10.4;
				World.loadWorld(Direction.SOUTH);
			}
			if (y > 10.4) {
				y = 0.1;
				World.loadWorld(Direction.NORTH);
			}
		}
	}

	// Called when Player wants to attack; Hopefully when near an enemy
	public void attack() {
		// Added so that player can not always be attacking
		if (System.currentTimeMillis() - this.attackStartTime - ATTACK_COOLDOWN > ATTACK_TIME) {
			attackStartTime = System.currentTimeMillis();
			MusicPlayer.playSoundEffect("/assets/music/Sword.wav");
		}
	}

	// Heal's the Player
	public void heal(int amount) {
		health += amount;
		if (health > maxHealth)
			health = maxHealth;
	}

	// Returns Player's maxHealth
	public int getMaxHealth() {
		return maxHealth;
	}

	// Allows for the ability to increase maximum health
	public void setMaxHealth(int healthValue) {
		maxHealth = healthValue;
	}

	// Returns the amount of triforces the player has gathered
	public int getTriforces() {
		return triforcePieces;
	}

	// Gives the player another Triforce Piece
	public void addTriforce() {
		triforcePieces += 1;
		if (triforcePieces >= 3) {
			World.setGameState(3);
		}
	}

	/**
	 * @return the canTakeDamage
	 */
	public boolean getCanTakeDamage() {
		return canTakeDamage;
	}

	/**
	 * @param canTakeDamage
	 *            the canTakeDamage to set
	 */
	public void setCanTakeDamage(boolean canTakeDamage) {
		this.canTakeDamage = canTakeDamage;
	}
}
