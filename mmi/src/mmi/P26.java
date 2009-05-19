package mmi;

import java.awt.AWTException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class P26 implements MouseMotionListener { 
	private List64 experiment;
	private AnimatedCanvas experimentGUI;
	private Logger experimentLogger;
	private int hitCounter;

	public P26() throws AWTException {
		this.experiment = new List64();
		this.experimentGUI = new AnimatedCanvas();
		this.experimentLogger = new Logger();
		experimentGUI.getJFrame().addMouseMotionListener(this);
	}

	/**
	 * Mouse Moved Listener Kollision Schaut ob der Mauszeiger ausserhalb des
	 * Tunnels ist und wenn ja setzt ihn wieder an den Tunnelrand
	 * 
	 * @throws AWTException
	 */
	public void mouseMoved(MouseEvent e) {
		experimentGUI.setMouseX(e.getX());
		experimentGUI.setMouseY(e.getY());

		int xPos = (int) experimentGUI.getJFrame().getX() + e.getX();

		if (experimentGUI.getMouseY() > (int) experimentGUI.getBox2().getY()) // Falls unter Tunnel
		{
			experimentGUI.getRobot().mouseMove(xPos, (int) (experimentGUI.getJFrame().getY()+experimentGUI.getBox2().getY()));
			experimentGUI.setHitColorBox2(experimentGUI.getHitColor()); // getroffene Fl�che markieren
			experimentGUI.hitCounter++;
		} else if (experimentGUI.getMouseY() < (int) experimentGUI.getBox1().getHeight()) // Falls �ber Tunnel
		{
			experimentGUI.getRobot().mouseMove(xPos, (int) (experimentGUI.getJFrame().getY()+experimentGUI.getBox1().getHeight()));
			experimentGUI.setHitColorBox1(experimentGUI.getHitColor()); // getroffene Fl�che markieren
			experimentGUI.hitCounter++;
		}
	}

	/**
	  *
	  */
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * Programm starten
	 */
	public static void main(String args[]) {
//		try {
//			AnimatedCanvas f = new AnimatedCanvas();
//			f.setSize(800, 600);
//			f.setTitle("Buffered Animated Canvas");
//			f.setVisible(true);
//			f.go();
//		} catch (AWTException e) {
//		}
		try {
			P26 myProg= new P26();
			for(int i =0;i<= 63;i++){
				myProg.experimentGUI.setSize(800,600);
				myProg.experimentGUI.setMousePosition(2);
				myProg.experimentGUI.setTitle("Buffered Animated Canvas");
				myProg.experimentGUI.setVisible(true);
				myProg.experimentGUI.go();
			}
			myProg.experimentGUI.setSize(800,600);
			myProg.experimentGUI.setMousePosition(2);
			myProg.experimentGUI.setTitle("Buffered Animated Canvas");
			myProg.experimentGUI.setVisible(true);
			myProg.experimentGUI.go();
			
//			myProg.experimentGUI.setVisible(false);
//			myProg.experimentGUI.go();
//			myProg.experimentGUI.setSize(900,900);
//			myProg.experimentGUI.setTitle("Buffered Animated Canvas1");
//			myProg.experimentGUI.setVisible(true);
//			myProg.experimentGUI.go();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
