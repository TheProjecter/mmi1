package mmi;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


/**
 * Testklasse f�r Frame, Tunneldarstellung und Kollision
 * 
 *	Muss man umschreiben, generell wird als Tunnel nur ein Rectangle �bergeben
 */
public class AnimatedCanvas extends JFrame implements MouseMotionListener
{
	public static final int WIDTH 	= 800;								// Fensterbreite
	public static final int HEIGHT	= 800;								// Fensterh�he
	
	// Der Tunnel bestehend aus Breite und H�he. X, Y Koordinaten sind egal
	// Tunnel wird einfach immer in die Mitte gesetzt
	Rectangle tunnel = new Rectangle( WIDTH, 64 );	
	
	int hitCounter = 0;																		// Z�hler f�r Treffer
	
	private static int DELAY = 1;													// repaint Delay, sp�ter bessern Thread
  Image buffer;																					// screen buffer, flackerfreie darstellung
  Color hitColor = new Color( 1.0f, 0.0f, 0.0f, 1.0f );	// Farbe f�r getroffene Fl�che
  Color hitColorBox1;																		// aktuelle Farbe f�r getroffene Fl�che
  Color hitColorBox2;																		// aktuelle Farbe f�r getroffene Fl�che
  Color boxColor = Color.GREEN;													// Farbe f�r fl�chen ausserhalb des tunnels
  Rectangle box1, box2;																	// Die Fl�chen ausserhalb des Tunndels
  
  // L�sst den Mauscursor auf dem Bildschirm positionieren
  // => Position = FensterPosition + Mausposition
  Robot robot;																		  
  int mouseX, mouseY;																		// Speichert Mausposition (nur f�r Ausgabe)
  
  
  
  /**
   * Mouse Moved Listener
   *	Kollision
   *	Schaut ob der Mauszeiger ausserhalb des Tunnels ist und wenn ja
   *	setzt ihn wieder an den Tunnelrand
   */
  public void mouseMoved(MouseEvent e)
  {
		mouseX = e.getX();
		mouseY = e.getY();
		
		int xPos = (int) this.getX() + e.getX();
		
		if( mouseY > (int)box2.getY() )																				// Falls unter Tunnel
		{
			robot.mouseMove( xPos, (int)(this.getY() + box2.getY()) );
			hitColorBox2 = hitColor;																						// getroffene Fl�che markieren
			hitCounter++;
		}
		else if( mouseY < (int)box1.getHeight() )															// Falls �ber Tunnel
		{
			robot.mouseMove( xPos, (int) (this.getY() + box1.getHeight()) );
			hitColorBox1 = hitColor;																						// getroffene Fl�che markieren
			hitCounter++;
		}		
  }
  
  
  /**
   *
   */
  public void mouseDragged(MouseEvent e) {}


  
	/**
	 * paint
	 *	Zeichenroutine
	 */
  public void paint(Graphics g)
  {
  	// Buffer l�schen
    Graphics bufferG = buffer.getGraphics();      
    bufferG.setColor(Color.WHITE);      
    bufferG.fillRect(0, 0, WIDTH, HEIGHT);
    
    // Fl�chen ausserhalb Tunnel zeichnen
    bufferG.setColor(boxColor);
    bufferG.fillRect( (int)box1.getX(), (int)box1.getY(), (int)box1.getWidth(), (int)box1.getHeight() );
    bufferG.fillRect( (int)box2.getX(), (int)box2.getY(), (int)box2.getWidth(), (int)box2.getHeight() );
    
    // Trefferfl�chen ausfaden (Alpha)
    if ( hitColorBox1.getAlpha() > 0) {	hitColorBox1 = new Color (255, 0, 0, hitColorBox1.getAlpha()-1 ); }
    if ( hitColorBox2.getAlpha() > 0) {	hitColorBox2 = new Color (255, 0, 0, hitColorBox2.getAlpha()-1 ); }    

    // Trefferfl�chen zeichnen
    bufferG.setColor( hitColorBox1 );
    bufferG.fillRect( (int)box1.getX(), (int)box1.getY(), (int)box1.getWidth(), (int)box1.getHeight() );
    bufferG.setColor( hitColorBox2 );
    bufferG.fillRect( (int)box2.getX(), (int)box2.getY(), (int)box2.getWidth(), (int)box2.getHeight() );
    
    // Textdarstellung
    bufferG.setColor(Color.BLACK);
    bufferG.drawString( "Maus X: " + mouseX + " | Maus Y: " + mouseY + " | Hits: " + hitCounter, 100, 100 );

   	// Buffer auf Canvas zeichnen
    g.drawImage(buffer, 0, 0, this);
  }
  
  
  /**
   * Timed Repaint starten
   *	sp�ter besser Thread um Delay zu verringern
   */
  public void go() {
  	
  	// repaint in DELAY-Intervallen aufrufen
    TimerTask task = new TimerTask()
    {    	
      public void run() { repaint(); }				// ruft paint Methode auf
    };
    
    Timer timer = new Timer();
    timer.schedule(task, 0, DELAY);
  }
  
  
  /**
   * Konstruktor
   */
  public AnimatedCanvas() throws AWTException
  {
  	 setDefaultCloseOperation(EXIT_ON_CLOSE);
  	
  	// Buffer initialisieren
  	buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  	hitColorBox1 = new Color( 1.0f, 0.0f, 0.0f, 0.0f );
  	hitColorBox2 = new Color( 1.0f, 0.0f, 0.0f, 0.0f );
  	
  	// Mouse MotionListener
//  	addMouseMotionListener(this);
  	// Maus Manipulation
  	robot = new Robot();

  	// Rectangles (Fl�chen und Tunnel) berechnen
  	// TODO: vertikal horizontal
  	int box1Height 	= (int)((HEIGHT/2)-tunnel.getHeight()/2);
  	int box2Start		= (int)(box1Height + tunnel.getHeight());
  	int box2Height	= (int)(HEIGHT - box2Start);
  	
  	box1 = new Rectangle( 0, 0, WIDTH, box1Height  );
  	box2 = new Rectangle( 0, box2Start, WIDTH, box2Height );
  }


	/**
	 * Programm starten
	 */
  public static void main(String args[])
  {
  	try
  	{
    	AnimatedCanvas f = new AnimatedCanvas();
    	f.setSize(WIDTH, HEIGHT);
	    f.setTitle("Buffered Animated Canvas");
	    f.setVisible(true);
	    f.go();
    }
    catch( AWTException e)
    {
    }
    
  }
  
}