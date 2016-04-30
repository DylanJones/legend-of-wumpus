package display;

import java.util.*;
import entity.Entity;

/** This is a class of constants. It will contain global variables. */
public final class World {
	private static HashSet<Entity> entities = new HashSet<Entity>();
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;

	// Game state: 0 = overworld, 1 = boss, 2 = title screen
	private static int gameState = 0;

	public static void registerEntity(Entity e) {
		entities.add(e);
	}

	public static Set<Entity> getAllEntities() {
		return entities;
	}

	/**
	 * @return the gameState
	 */
	public static int getGameState() {
		return gameState;
	}

	/**
	 * @param gameState
	 *            the gameState to set
	 */
	public static void setGameState(int gameState) {
		World.gameState = gameState;
	}
}
