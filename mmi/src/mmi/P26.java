//package mmi;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class P26 implements MouseMotionListener,Observer{ 
	private static List64 experiment;
	private static AnimatedCanvas experimentGUI;
	private static Logger experimentLogger;
	private int hitCounter;
	private double MOUSESPEED;
	private static Tunnel currentTunnel;
	private static Boolean tunnelReady=false;
	private static String user;
	private static String gender;

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
		
//			switch(experimentGUI.getTunnelDirection()){
//			case 0: {
//				if(xPos>=experimentGUI.getBox1().getWidth()){
//					tunnelReady=true;
//					break;	
//				}
//			}
//			case 1: {
//				if(xPos>=experimentGUI.getBox1().getWidth()){
//					tunnelReady=true;
//					break;	
//				}
//			}
//			case 2: {
//				if(xPos>=experimentGUI.getBox1().getWidth()){
//					tunnelReady=true;
//					break;	
//				}
//			}
//			case 3: {
//				if(xPos>=experimentGUI.getBox1().getWidth()){
//					tunnelReady=true;
//					break;	
//				}
//			}
//			}
			if (experimentGUI.getMouseY() > (int) experimentGUI.getBox2().getY()) // Falls unter Tunnel
			{
				experimentGUI.getRobot().mouseMove(xPos, (int) (experimentGUI.getJFrame().getY()+experimentGUI.getBox2().getY()));
				experimentGUI.setHitColorBox2(experimentGUI.getHitColor()); // getroffene Fläche markieren
				experimentGUI.hitCounter++;
			} else if (experimentGUI.getMouseY() < (int) experimentGUI.getBox1().getHeight()) // Falls über Tunnel
			{
				experimentGUI.getRobot().mouseMove(xPos, (int) (experimentGUI.getJFrame().getY()+experimentGUI.getBox1().getHeight()));
				experimentGUI.setHitColorBox1(experimentGUI.getHitColor()); // getroffene Fläche markieren
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
		user=args[1];
		gender=args[2];
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
			//experimentLogger.addTestcase(args[0], new Tescase());
//			for(int i =0;i<= 63;i++){
//				currentTunnel=experiment.getNext();
//				while (true) {
//					//wait
//					if (tunnelReady){ break;}
//				}
//			}
			currentTunnel=experiment.getNext();
			experimentGUI.setNewTunnel(currentTunnel);//loads the first tunnel
//			myProg.experimentGUI.setSize(800,600);
//			myProg.experimentGUI.setMousePosition(0);
//			myProg.experimentGUI.setTitle("Buffered Animated Canvas");
//			myProg.experimentGUI.setVisible(true);
//			myProg.experimentGUI.go();
			
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

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		currentTunnel=experiment.getNext();//loads every next tunnel
		experimentGUI.setNewTunnel(currentTunnel);
		experimentLogger.addTestcase(user, new Testcase(user,currentTunnel.getWidth()+"/"+currentTunnel.getLength()+"/"+currentTunnel.getDirection(),new Dimension(currentTunnel.getWidth(),currentTunnel.getLength()),MOUSESPEED,new Date(System.currentTimeMillis()),experimentGUI.hitCounter,user,gender));// logging the results
		
	}
}
