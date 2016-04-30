package display;

import entity.*;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
/**
 * This is the class which loads everything into the JPanel and sets up a few
 * global variables.  There should only ever be one instance of WumpusPanel.
 */
public class WumpusPanel extends JPanel {
	public static Timer ticker;
	private static KeyboardHandler kb;
	public static Player thePlayer;

	public WumpusPanel() {
		super();
		kb = new KeyboardHandler();
		addKeyListener(kb);
		setFocusable(true);
		ticker = new Timer(33, new Tick(this, kb));
		ticker.start();
		thePlayer = new Player();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// GUI update(a static class)
		/* TEMPORARY UNTIL WE GET AN ACTUAL GUI */
		if (World.getGameState() == 2) {
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 50));
			g.drawString("You lost", getHeight() / 2, getWidth() / 2);
		}
		for (Entity e : World.getAllEntities()) {
			e.draw(g);
		}
	}
}
