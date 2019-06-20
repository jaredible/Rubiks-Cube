package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main2 extends Canvas implements KeyListener {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final String TITLE = "Testing";
	@SuppressWarnings("unused")
	private final BufferedImage image;

	private final Cube2 cube = new Cube2(Size.THREE);

	public Main2() {
		addKeyListener(this);

		image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	}

	public void paint(Graphics g) {
		int xx = (getWidth() - WIDTH) / 2;
		int yy = (getHeight() - HEIGHT) / 2;
		g.setColor(Color.BLACK);
		g.fillRect(xx, yy, WIDTH, HEIGHT);

		// paint cube
	}

	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 37:
			cube.twist(cube.getCurrentFace(), Direction.CCW);
			break;
		case 39:
			cube.twist(cube.getCurrentFace(), Direction.CW);
			break;
		case 49:
			cube.setCurrentFace(Face.UP);
			break;
		case 50:
			cube.setCurrentFace(Face.DOWN);
			break;
		case 51:
			cube.setCurrentFace(Face.FRONT);
			break;
		case 52:
			cube.setCurrentFace(Face.BACK);
			break;
		case 53:
			cube.setCurrentFace(Face.LEFT);
			break;
		case 54:
			cube.setCurrentFace(Face.RIGHT);
			break;
		default:
			break;
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		Main2 main = new Main2();
		Dimension dimension = new Dimension(WIDTH, HEIGHT);
		main.setMinimumSize(dimension);
		main.setMaximumSize(dimension);
		main.setPreferredSize(dimension);

		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(main, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}
}
