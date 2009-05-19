//package mmi;

import java.awt.*;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 * Testklasse für Frame, Tunneldarstellung und Kollision
 * 
 * Muss man umschreiben, generell wird als Tunnel nur ein Rectangle übergeben
 */

public class AnimatedCanvas extends JFrame { // implements MouseMotionListener

	private int WIDTH; // Fensterbreite
	private int HEIGHT; // Fensterhöhe
	private int TWIDTH; // Tunnel width
	private final String direction0 = "horizontal, left-right";
	private final String direction1 = "vertical, top-bottom";
	private final String direction2 = "horizontal, right-left";
	private final String direction3 = "vertical, bottom-top";
	private String directionMsg;
	/**
	 * TDIRECTION: Tunnel Direction 0: horizontal, left-right; 1: vertical,
	 * top-bottom; 2: horizontal, right-left 3: vertical, bottom-top
	 */
	private int TDIRECTION; // Tunnel direction
	private Rectangle tunnel;

	int hitCounter = 0; // Zähler für Treffer

	private static int DELAY = 1; // repaint Delay, später bessern Thread
	Image buffer; // screen buffer, flackerfreie darstellung
	Color hitColor = new Color(1.0f, 0.0f, 0.0f, 1.0f); // Farbe für getroffene
	// Fläche
	Color hitColorBox1; // aktuelle Farbe für getroffene Fläche
	Color hitColorBox2; // aktuelle Farbe für getroffene Fläche
	Color boxColor = Color.GREEN; // Farbe für flächen ausserhalb des tunnels
	Rectangle box1, box2; // Die Flächen ausserhalb des Tunndels

	// Lässt den Mauscursor auf dem Bildschirm positionieren
	// => Position = FensterPosition + Mausposition
	private Robot robot;
	private int mouseX, mouseY; // Speichert Mausposition (nur für Ausgabe)

	// /**
	// * Mouse Moved Listener Kollision Schaut ob der Mauszeiger ausserhalb des
	// * Tunnels ist und wenn ja setzt ihn wieder an den Tunnelrand
	// */
	// public void mouseMoved(MouseEvent e) {
	// mouseX = e.getX();
	// mouseY = e.getY();
	//
	// int xPos = (int) this.getX() + e.getX();
	//
	// if (mouseY > (int) box2.getY()) // Falls unter Tunnel
	// {
	// robot.mouseMove(xPos, (int) (this.getY() + box2.getY()));
	// hitColorBox2 = hitColor; // getroffene Fläche markieren
	// hitCounter++;
	// } else if (mouseY < (int) box1.getHeight()) // Falls über Tunnel
	// {
	// robot.mouseMove(xPos, (int) (this.getY() + box1.getHeight()));
	// hitColorBox1 = hitColor; // getroffene Fläche markieren
	// hitCounter++;
	// }
	// }
	//
	// /**
	// *
	// */
	// public void mouseDragged(MouseEvent e) {
	// }

	/**
	 * paint Zeichenroutine
	 */
	public void paint(Graphics g) {
		// Buffer löschen
		Graphics bufferG = buffer.getGraphics();
		bufferG.setColor(Color.WHITE);
		bufferG.fillRect(0, 0, WIDTH, HEIGHT);

		// Flächen ausserhalb Tunnel zeichnen
		bufferG.setColor(boxColor);
		bufferG.fillRect((int) box1.getX(), (int) box1.getY(), (int) box1.getWidth(), (int) box1.getHeight());
		bufferG.fillRect((int) box2.getX(), (int) box2.getY(), (int) box2.getWidth(), (int) box2.getHeight());

		// Trefferflächen ausfaden (Alpha)
		if (hitColorBox1.getAlpha() > 0) {
			hitColorBox1 = new Color(255, 0, 0, hitColorBox1.getAlpha() - 1);
		}
		if (hitColorBox2.getAlpha() > 0) {
			hitColorBox2 = new Color(255, 0, 0, hitColorBox2.getAlpha() - 1);
		}

		// Trefferflächen zeichnen
		bufferG.setColor(hitColorBox1);
		bufferG.fillRect((int) box1.getX(), (int) box1.getY(), (int) box1.getWidth(), (int) box1.getHeight());
		bufferG.setColor(hitColorBox2);
		bufferG.fillRect((int) box2.getX(), (int) box2.getY(), (int) box2.getWidth(), (int) box2.getHeight());

		// Textdarstellung
		bufferG.setColor(Color.BLACK);
		bufferG.drawString("Maus X: " + mouseX + " | Maus Y: " + mouseY + " | Hits: " + hitCounter, 100, 100);
		bufferG.drawString(	"Movement Direction: Please move the mouse poiner through the tunnel : "+ directionMsg, 100, 120);

		// Buffer auf Canvas zeichnen
		g.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Timed Repaint starten später besser Thread um Delay zu verringern
	 */
	public void go() {

		// repaint in DELAY-Intervallen aufrufen
		TimerTask task = new TimerTask() {
			public void run() {
				repaint();
			} // ruft paint Methode auf
		};

		Timer timer = new Timer();
		timer.schedule(task, 0, DELAY);
	}

	/**
	 * Konstruktor 1
	 */
	public AnimatedCanvas() throws AWTException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setWindowHeight(600);
		setWindowWidth(800);
		setTunnelWidth(16);

		tunnel = new Rectangle(this.WIDTH, this.TWIDTH);
		// Buffer initialisieren
		buffer = new BufferedImage(this.WIDTH, this.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		hitColorBox1 = new Color(1.0f, 0.0f, 0.0f, 0.0f);
		hitColorBox2 = new Color(1.0f, 0.0f, 0.0f, 0.0f);

		// Mouse MotionListener
		// addMouseMotionListener(this);
		// Maus Manipulation
		this.robot = new Robot();

		// Rectangles (Flächen und Tunnel) berechnen
		// TODO: vertikal horizontal
		int box1Height = (int) ((this.HEIGHT / 2) - tunnel.getHeight() / 2);
		int box2Start = (int) (box1Height + tunnel.getHeight());
		int box2Height = (int) (this.HEIGHT - box2Start);

		box1 = new Rectangle(0, 0, this.WIDTH, box1Height);
		box2 = new Rectangle(0, box2Start, this.WIDTH, box2Height);
	}

	//
	// /**
	// * Konstruktor 2
	// */
	// public AnimatedCanvas(int wHeight, int wWidth, int tWidth, int
	// tDirection)
	// throws AWTException {
	// // setDefaultCloseOperation(EXIT_ON_CLOSE);
	// // initial allocate the private variable
	// setWindowHeight(wHeight);
	// setWindowWidth(wWidth);
	// setTunnelWidth(tWidth);
	// // Der Tunnel bestehend aus Breite und Höhe. X, Y Koordinaten sind egal
	// // Tunnel wird einfach immer in die Mitte gesetzt
	// tunnel = new Rectangle(this.WIDTH, this.TWIDTH);
	// setTunnelDirection(tDirection);
	// this.setSize(this.WIDTH, this.TWIDTH);
	// // Buffer initialisieren
	// buffer = new BufferedImage(this.WIDTH, this.HEIGHT,
	// BufferedImage.TYPE_INT_RGB);
	// hitColorBox1 = new Color(1.0f, 0.0f, 0.0f, 0.0f);
	// hitColorBox2 = new Color(1.0f, 0.0f, 0.0f, 0.0f);
	//
	// // Mouse MotionListener
	// // addMouseMotionListener(this);
	// // Maus Manipulation
	// this.robot = new Robot();
	//
	// // Rectangles (Flächen und Tunnel) berechnen
	// // TODO: vertikal horizontal
	// int box1Height = (int) ((this.HEIGHT / 2) - tunnel.getHeight() / 2);
	// int box2Start = (int) (box1Height + tunnel.getHeight());
	// int box2Height = (int) (this.HEIGHT - box2Start);
	//
	// box1 = new Rectangle(0, 0, this.WIDTH, box1Height);
	// box2 = new Rectangle(0, box2Start, this.WIDTH, box2Height);
	// }

	/**
	 * public void setTunnelWidth(int tWidth)
	 */
	public void setTunnelWidth(int tWidth) {
		this.TWIDTH = tWidth;
	}

	/**
	 * public void getTunnelWidth()
	 */
	public int getTunnelWidth() {
		return this.TWIDTH;
	}

	/**
	 * public void setWindowWidth(int wWidth)
	 */
	public void setWindowWidth(int wWidth) {
		this.WIDTH = wWidth;
	}

	/**
	 * public int getWindowWidth()
	 */
	public int getWindowWidth() {
		return this.WIDTH;
	}

	/**
	 * public void setWindowHeight(int wHeight)
	 */
	public void setWindowHeight(int wHeight) {
		this.HEIGHT = wHeight;
	}

	/**
	 * public int getWindowHeight()
	 */
	public int getWindowHeight() {
		return this.HEIGHT;
	}

	/**
	 * public void setTunnelDirection(int tDirection)
	 */
	public void setTunnelDirection(int tDirection) {
		this.TDIRECTION = tDirection;
	}

	/**
	 * public int getTunnelDirection()()
	 */
	public int getTunnelDirection() {
		return this.TDIRECTION;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public JFrame getJFrame() {
		return this;
	}
	/**
	 * TDIRECTION: Tunnel Direction 
	 * 0: horizontal, left-right; 
	 * 1: vertical, top-bottom; 
	 * 2: horizontal, right-left 
	 * 3: vertical, bottom-top
	 */
	public void setMousePosition(int mDirection) {
		setTunnelDirection(mDirection);
		switch (mDirection) {
		case 0: {
			robot.mouseMove(this.getX() + (int) box1.getX(), (int) (box1.getX()+ box1.getHeight() + (tunnel.height / 2)));
			directionMsg=direction0;
		}
		case 1: {
			robot.mouseMove(this.getX()+ (int) (box1.getX() + (box1.getWidth() / 2)), (int) box1.getY());
			directionMsg=direction1;
		}
		case 2: {
			robot.mouseMove((this.getX() +(int) (box1.getX() + box1.getWidth())),(int) (box1.getX() + box1.getHeight() + (tunnel.height / 2)));
			directionMsg=direction2;
		}
		case 3: {
			robot.mouseMove(this.getX(), (int) (box1.getY()+ tunnel.getY() + box2.getY()));
			directionMsg=direction3;
		}
		}
	}

	public Rectangle getBox1() {
		return box1;
	}

	// public void setBox1(Rectangle box1) {
	// this.box1 = box1;
	// }

	public Rectangle getBox2() {
		return box2;
	}

	// public Color getHitColorBox1() {
	// return hitColorBox1;
	// }

	public void setHitColorBox1(Color hitColorBox1) {
		this.hitColorBox1 = hitColorBox1;
	}

	// public Color getHitColorBox2() {
	// return hitColorBox2;
	// }

	public void setHitColorBox2(Color hitColorBox2) {
		this.hitColorBox2 = hitColorBox2;
	}

	public Color getHitColor() {
		return hitColor;
	}

	// public void setHitColor(Color hitColor) {
	// this.hitColor = hitColor;
	// }

	// public void setBox2(Rectangle box2) {
	// this.box2 = box2;
	// }
	// /**
	// * Programm starten
	// */
	// public static void main(String args[]) {
	// try {
	// AnimatedCanvas f = new AnimatedCanvas(800, 600);
	// f.setSize(800, 600);
	// f.setTitle("Buffered Animated Canvas");
	// f.setVisible(true);
	// f.go();
	// } catch (AWTException e) {
	// }
	//
	// }
}