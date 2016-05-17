package display;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entity.EntityGremlin;

/** A class to contain the World template data */
public final class WorldTemplate {
	public final ArrayList<String> entities = new ArrayList<String>();
	public final ArrayList<String> walls = new ArrayList<String>();
	public String version;
	public String image;
	private String worldNorth;
	private String worldSouth;
	private String worldEast;
	private String worldWest;

	public WorldTemplate(String filename) {
		parseFile(filename);
	}

	private void parseFile(String filename) {
		// Read the filename into this object's private fields
	}

	public void load() {
		// Spawn in all the walls and entites
	}

	private void createEntity(String entity) {
		// Parse the given string and spawn in a new entity
	}

	public String getNextWorld(int direction) {
		// Return the world filename as if the player walked off the scree in
		// the speccified direction.
		return null;
	}
}
