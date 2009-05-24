//package mmi;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Hauptklasse, erstellt GUI und logged Ergebnisse
 */
public class P26 extends JFrame
{
	private static final int CANVAS_WIDTH 	= 1174;
	private static final int CANVAS_HEIGHT 	= 1174;
	private static final String TITLE 			= "User Study MMI 09 - Gruppe 26";

	private static List64 experiment;
	private static AnimatedCanvas experimentGUI;
	private static Logger experimentLogger;
	private static Tunnel currentTunnel;
	
	// Benutzerdaten
	private static String	user		= "unknown";
	private static String gender	= "n/a";
	private static float speed		= 1.0f;			
	private String RESULTPATH			= "";

	public P26(String username, String usergender, String mousespeed, String path) throws AWTException
	{
		this.user				= username;
		this.gender			= usergender;
		this.RESULTPATH	= path;
		
		switch ( Integer.valueOf(mousespeed) )
		{
			case 0: this.speed 	= 0.5f; break;
			case 1: this.speed 	= 1.0f; break;
			case 2: this.speed 	= 1.5f; break;
			default: this.speed = 1.0f; break;
		}
			
		this.experiment 			= new List64();
		this.experimentLogger = new Logger();

		// GUI initialisieren
		this.experimentGUI = new AnimatedCanvas(CANVAS_WIDTH, CANVAS_HEIGHT, this);
		this.experimentGUI.mouseSpeed = speed;
		this.experimentGUI.setTitle(TITLE + " : Testperson: " + user + ", Geschlecht: " + gender + ", Mausgeschwindigkeit: " + speed );

		// Tunnel laden
		currentTunnel = experiment.getNext();
		// Test starten
		experimentGUI.setNewTunnel(currentTunnel);
	}

	public void tunnelFinished()
	{	
		if (!experiment.getList().isEmpty())
		{
			// Zeit von MouseRelease bis Goal (grauses Feld nach Tunnel) erreicht
			long timeTakenForThisTunnel = experimentGUI.timeAtEnd - experimentGUI.timeAtStart;
		
			// Ergebnisse Loggen
			Testcase currentTest = new Testcase(user, currentTunnel.getWidth() + "/" + currentTunnel.getLength() + "/" + currentTunnel.getDirection(), new Dimension(currentTunnel.getWidth(), currentTunnel.getLength()), experimentGUI.mouseSpeed, timeTakenForThisTunnel, experimentGUI.hitCounter, user, gender);
			experimentLogger.addTestcase(user, currentTest );
	
			// Nächsten Tunnel laden
			currentTunnel = experiment.getNext();
			// Tunnel erstellen und starten
			experimentGUI.setNewTunnel(currentTunnel);
		} else {
			// Logdatei speichern
			experimentLogger.flush(RESULTPATH);
			// Experiment beenden
			experimentGUI.finish();
		}
	}
}
