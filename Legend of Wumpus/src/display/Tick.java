package display;

import entity.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This class does the majority of the world engine work. */
public class Tick implements ActionListener {
	private WumpusPanel container;
	private KeyboardHandler kb;

	public Tick(WumpusPanel parent, KeyboardHandler kb) {
		container = parent;
		this.kb = kb;
	}

	/**Absolutely NOTHING related to GUI may happen here.*/
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		movePlayer();
		entityTick();
		collideEntities();
		container.update(container.getGraphics());
		if(World.getGameState() == 2) { //Stop the world engine
			container.ticker.stop();
		}
	}

	private void movePlayer() {
		if(kb.isKeyPressed('w'))
			WumpusPanel.thePlayer.move(5);
	}
	
	/**
	 * Takes all the entities in the world and computes if any are touching. If
	 * they are touching, call <entity>.collide().
	 */
	private void collideEntities() {
		for (Entity e : World.getAllEntities()) {
			// STUB; still needs to be implemented
			for (Entity x : World.getAllEntities()) {
				if (x == e)
					continue;// So it doesn't collide with itself
				// If they are less than 5 pixels apart...
				if (Math.abs(e.getX() - x.getX()) + Math.abs(e.getY() - x.getY()) < 30) {
					e.collide(x);
					// System.out.println("collision");
					// System.out.println("distance: " + Math.abs(e.getX() -
					// x.getX()) + Math.abs(e.getY() - x.getY()));
				}
				// if(x instanceof Player)
				// System.out.println(x.getX());
			}
		}
	}

	private void entityTick() {
		for (Entity e : World.getAllEntities()) {
			e.tick();
		}
	}

}
