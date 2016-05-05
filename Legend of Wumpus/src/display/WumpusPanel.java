package display;

import entity.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
/**
 * This is the class which loads everything into the JPanel and sets up a few
 * global variables. There should only ever be one instance of WumpusPanel.
 */
public class WumpusPanel extends JPanel {
	// This SHOULD be private. We must make it so.
	private static KeyboardHandler kb;
	private static Image bg;

	static {
		try {
			bg = ImageIO.read(new File("assets/bg.png")).getScaledInstance(640, 480, Image.SCALE_FAST);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error reading background images");
			System.exit(1);
		}
	}

	public WumpusPanel() {
		super();
		kb = new KeyboardHandler();
		addKeyListener(kb);
		setFocusable(true);
		WorldBackend.startTicker(this, kb);
	}

	@Override
	public void paintComponent(Graphics g) {
		// Clear the screen
		if (bg != null) {
			g.drawImage(bg, 0, 0, null);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		HUD.drawHud(g);
		for (Entity e : WorldBackend.getAllEntities()) {
			e.draw(g);
		}
	}
}
