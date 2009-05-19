//package mmi;

import java.awt.*;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class AnimatedCanvas extends JFrame implements MouseMotionListener { // implements MouseMotionListener
	
	private P26 p26;														// zirkuläre Abängigkeit

	private int WIDTH; 													// Fensterbreite
	private int HEIGHT; 												// Fensterhšhe
	private int TWIDTH; 												// Tunnel width
	
	private Tunnel currentTunnel;								// Der aktuelle Tunnel
	private Boolean horizontal 					= false;
	private String[] directionTxt 			= { "horizontal, left-right", "vertical, top-bottom", "horizontal, right-left", "vertical, bottom-top" };
	private String hitTxt								= "kein Treffer";
	private final int MOUSESTARTOFFSET	= 50;		// Pixeloffset, wie weit Maus von Tunnel platziert wird
	
	/**
	 * TDIRECTION: Tunnel Direction 0: horizontal, left-right; 1: vertical,
	 * top-bottom; 2: horizontal, right-left 3: vertical, bottom-top
	 */
	private int TDIRECTION; 																		// Tunnel direction
	int hitCounter = 0; 																				// ZŠhler fŸr Treffer

	// Zeichnen
	private static int DELAY = 1; 															// repaint Delay
	private Image buffer; 																			// screen buffer, flackerfreie darstellung
	
	// Darstellung
	private Color hitColorBox1, hitColorBox2; 									// aktuelle Farbe der getroffenen Fläche
	private Color boxColor = Color.GREEN; 											// Farbe für Hit-Flächen
	private Color hitColor = new Color(1.0f, 0.0f, 0.0f, 1.0f); // Trefferfarbe
	
	private Rectangle box1, box2, tunnel, goal;									// Alle benötigten Flächen

	// Maus
	private Robot robot;
	private int startMouseX, startMouseY;
	private int mouseX, mouseY; 																// Speichert Mausposition (nur für Ausgabe)

	/**
	 * Timed Repaint starten spŠter besser Thread um Delay zu verringern
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
	 * Konstruktor
	 */
	public AnimatedCanvas(int canvasWidth, int canvasHeight, P26 p26) throws AWTException {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		WIDTH 	= canvasWidth;
		HEIGHT 	= canvasHeight;
		
		this.p26 = p26;
		
		box1 		= new Rectangle(0,0);
		box2 		=	new Rectangle(0,0);
		tunnel 	= new Rectangle(0,0);
		goal		= new Rectangle(0,0);
		
		// Farbe für Kollisionsflächen, Standard mit Alpha = 0 (kein hit)
		hitColorBox1 	= new Color(1.0f, 0.0f, 0.0f, 0.0f);
		hitColorBox2 	= new Color(1.0f, 0.0f, 0.0f, 0.0f);
		
		// Maus Manipulation
		this.robot 		= new Robot();
		
		// aktuellen Buffer erstellen
		buffer = new BufferedImage(WIDTH, HEIGHT,	BufferedImage.TYPE_INT_RGB);
		
		// Gui starten
		this.setSize( canvasWidth, canvasHeight );
		this.setVisible(true);
		addMouseMotionListener(this);
		
		go();
	}
	
	/**
	 * Mouse Moved Listener Kollision Schaut ob der Mauszeiger ausserhalb des
	 * Tunnels ist und wenn ja setzt ihn wieder an den Tunnelrand
	 * 
	 * @throws AWTException
	 */
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		
		if( box1.contains(mouseX, mouseY) )
		{
			hitCounter++;
			hitColorBox1 = hitColor;
			
			if( horizontal ) {
				robot.mouseMove( mouseX, (int) (box1.getHeight()+this.getY()) );
			} else {
				robot.mouseMove( (int) (box1.getWidth()+this.getX()), mouseY );
			}
		}
		else if( box2.contains(mouseX, mouseY) )
		{
			hitCounter++;
			hitColorBox2 = hitColor;
			
			if( horizontal ) {
				robot.mouseMove( mouseX, (int) (box2.getY()+this.getY()) );
			}	else {
				robot.mouseMove( (int) (box2.getX()+this.getX()), mouseY ); }
		}
		else if( goal.contains(mouseX,mouseY) )
		{
			p26.tunnelFinished();
		}
	}

	public void mouseDragged(MouseEvent e) {}
	
		/**
	 * paint Zeichenroutine
	 */
	public void paint(Graphics g) {
		// Buffer löschen
		Graphics bufferG = buffer.getGraphics();
		bufferG.setColor(Color.WHITE);
		bufferG.fillRect(0, 0, WIDTH, HEIGHT);

		// FlŠchen ausserhalb Tunnel zeichnen
		bufferG.setColor(boxColor);
		bufferG.fillRect((int) box1.getX(), (int) box1.getY(), (int) box1.getWidth(), (int) box1.getHeight());
		bufferG.fillRect((int) box2.getX(), (int) box2.getY(), (int) box2.getWidth(), (int) box2.getHeight());
		
		bufferG.setColor(Color.GRAY);
		bufferG.fillRect((int) goal.getX(), (int) goal.getY(), (int) goal.getWidth(), (int) goal.getHeight());
		
		// TrefferflŠchen ausfaden (Alpha)
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
		bufferG.drawString(	"Movement Direction: Please move the mouse poiner through the tunnel : "+ directionTxt[currentTunnel.getDirection()], 100, 120);
		bufferG.drawString(	hitTxt, 100, 140);
		
		// Tunnel zeichnen (zuletzt, damit visuell korrekt)
		bufferG.setColor(Color.GRAY);
		bufferG.fillRect((int) tunnel.getX(), (int) tunnel.getY(), (int) tunnel.getWidth(), (int) tunnel.getHeight());

		// Buffer auf Canvas zeichnen
		g.drawImage(buffer, 0, 0, this);
	}
	
	/**
	 * setNewTunnel
	 *	erstellt einen neuen Tunnel (bestehend aus mehreren Flächen), berechnet die Startposition der Maus
	 *
	 *	@param newTunnel	der neue darzustellende Tunnel
	 */
	public void setNewTunnel( Tunnel newTunnel )
	{
		this.currentTunnel = newTunnel;
		
		// Variablen zurücksetzen
		hitCounter = 0;
		hitColorBox1 = new Color(1.0f, 0.0f, 0.0f, 0.0f);
		hitColorBox2 = new Color(1.0f, 0.0f, 0.0f, 0.0f);
		
		// HORIZONTALER TUNNEL
		if( currentTunnel.getDirection() == 0 || currentTunnel.getDirection() == 2 )
		{
			horizontal = true;
			
			int tunnelOffsetX 			= (int) (this.getWidth() 	- currentTunnel.getLength())/2;
			int tunnelOffsetY 			= (int) (this.getHeight() - currentTunnel.getWidth())/2;
			int tunnelBorderY 			= (int) (tunnelOffsetY + currentTunnel.getWidth());
			
			// Flächen einstellen
			tunnel.setSize( currentTunnel.getLength(), currentTunnel.getWidth() );
			tunnel.setLocation( tunnelOffsetX, tunnelOffsetY );
						
			box1.setSize( currentTunnel.getLength(), tunnelOffsetY );
			box1.setLocation( tunnelOffsetX, 0 );
			
			box2.setSize( currentTunnel.getLength(), this.getHeight()-tunnelBorderY );
			box2.setLocation( tunnelOffsetX, tunnelBorderY );
			
			
			// Maus Startposition und Zielfläche berechnen
			startMouseY = tunnelOffsetY + currentTunnel.getWidth()/2;
			
			if( currentTunnel.getDirection() == 0 )		// left-right
			{
				startMouseX = tunnelOffsetX - MOUSESTARTOFFSET;
				
				// Zielfläche rechts
				goal.setSize( this.getWidth() - (tunnelOffsetX + currentTunnel.getWidth()) , this.getHeight() );
				goal.setLocation( tunnelOffsetX + currentTunnel.getLength(), 0 );
			}
			else {																		// right-left
				startMouseX = tunnelOffsetX + currentTunnel.getLength() + MOUSESTARTOFFSET;
				
				// Zielfläche links
				goal.setSize( tunnelOffsetX , this.getHeight() );
				goal.setLocation( 0, 0 );
			}
		}
		else	// VERTIKALER TUNNEL
		{
			horizontal = false;
			
			int tunnelOffsetX 	= (int) (this.getHeight() - currentTunnel.getWidth())/2;
			int tunnelOffsetY 	= (int) (this.getWidth() 	- currentTunnel.getLength())/2;
			int tunnelBorderX 	= (int) (tunnelOffsetX + currentTunnel.getWidth());
			
			tunnel.setSize( currentTunnel.getWidth(), currentTunnel.getLength() );
			tunnel.setLocation( tunnelOffsetX, tunnelOffsetY );
						
			box1.setSize( tunnelOffsetX, currentTunnel.getLength() );
			box1.setLocation( 0, tunnelOffsetY );
			
			box2.setSize( this.getWidth() - tunnelBorderX, currentTunnel.getLength() );
			box2.setLocation( tunnelBorderX, tunnelOffsetY );
			
			// Maus Startposition berechnen
			startMouseX = tunnelOffsetX + currentTunnel.getWidth()/2;			
			if( currentTunnel.getDirection() == 1 )		// top-bottom
			{
				startMouseY = tunnelOffsetY - MOUSESTARTOFFSET;
				
				// Zielfläche unten
				goal.setSize( this.getWidth(), this.getHeight() - (tunnelOffsetY + currentTunnel.getLength()) );
				goal.setLocation( 0, tunnelOffsetY + currentTunnel.getLength() );
			}
			else {																		// bottom-top
				startMouseY = tunnelOffsetY + currentTunnel.getLength() + MOUSESTARTOFFSET;
				
				// Zielfläche oben
				goal.setSize( this.getWidth(), tunnelOffsetY  );
				goal.setLocation( 0, tunnelOffsetY + currentTunnel.getLength() );
			}
		} // END TUNNEL
		
		// Maus positionieren
		robot.mouseMove( startMouseX, startMouseY );
		
	}
	
}