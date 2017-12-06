package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Main extends Canvas implements KeyListener {
	private static final long serialVersionUID = 1L;

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
	public static final String WINDOW_TITLE = "Rubik's Cube Simulator v3";

	private final Cube cube = new Cube(3);

	// TODO: DOCUMENT EVERYTHING!!!!!
	// TODO: add cube rotations
	// TODO: add optional mouse selecting
	// TODO: add algorithm execution
	// TODO: 2.5D?
	// TODO: 3D!?
	// TODO: add solving capability and output solution
	// TODO: scalable?
	// TODO: improve efficiency
	// TODO: 300 lines of code or less challenge!
	// TODO: 250 lines of code or less challenge!!
	// TODO: 200 lines of code or less challenge!!!

	public Main() {
		addKeyListener(this);
	}

	public void paint(Graphics g) {
		int xx = (getWidth() - WINDOW_WIDTH) / 2;
		int yy = (getHeight() - WINDOW_HEIGHT) / 2;
		g.setColor(Color.BLACK);
		g.fillRect(xx, yy, WINDOW_WIDTH, WINDOW_HEIGHT);

		cube.paint(g);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public static void main(String[] args) {
		Main main = new Main();
		Dimension dim = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		main.setMinimumSize(dim);
		main.setMaximumSize(dim);
		main.setPreferredSize(dim);

		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(main, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setAlwaysOnTop(true);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() > 48 && e.getKeyCode() <= 54) cube.selectedFace = e.getKeyCode() - 48 - 1;
		else if (Math.abs(e.getKeyCode() - 38) == 1) cube.twist(cube.selectedFace, e.getKeyCode() - 38);
		repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
