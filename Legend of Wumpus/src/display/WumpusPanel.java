package display;

import entity.*;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
/**
 * This is the class which loads everything into the JPanel and sets up a few
 * global variables. There should only ever be one instance of WumpusPanel.
 */
public class WumpusPanel extends JPanel {
	private KeyboardHandler kb;

	public WumpusPanel() {
		super();
		kb = new KeyboardHandler();
		addKeyListener(kb);
		setFocusable(true);
		World.startTicker(this, kb);
		World.loadWorld("test.wld");
	}

	@Override
	public void paintComponent(Graphics g) {
		// Draw tiles
		World.renderTiles(g);
		HUD.drawHud(g);
		for (Entity e : World.getAllEntities()) {
			e.draw(g);
		}
	}
}
