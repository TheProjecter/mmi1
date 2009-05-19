//package mmi;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;
//import java.util.Observable;
//import java.util.Observer;

public class P26 /*implements Observer*/ { 
	
	private static final int CANVAS_WIDTH 	= 1174;
	private static final int CANVAS_HEIGHT	= 1174;
	private static final String TITLE 			= "User Study MMI 09 - Gruppe 26";
	
	private static List64 experiment;
	private static AnimatedCanvas experimentGUI;
	private static Logger experimentLogger;
	private int hitCounter;
	private double MOUSESPEED=0.0;
	private static Tunnel currentTunnel;
	private static Boolean tunnelReady=false;
	private static String user;
	private static String gender;

	public P26() throws AWTException {
		this.experiment 			= new List64();
		this.experimentLogger = new Logger();
		
		this.experimentGUI 		= new AnimatedCanvas(CANVAS_WIDTH, CANVAS_HEIGHT, this);
		this.experimentGUI.setTitle( TITLE );
	}

	/**
	 * Programm starten
	 */
	public static void main(String args[]) {
		user=args[0];
		gender=args[1];
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
	
	public void tunnelFinished()
	{
		if (!experiment.getList().isEmpty()){
			// Erst speichern
			
// Zeit von MouseRelease bis Goal (grauses Feld nach Tunnel) erreicht
// Muss noch in den Logger geschrieben werden. Bin da jetzt zu faul (<- Bitte dann löschen :) )
			long timeTakenForThisTunnel = experimentGUI.timeAtEnd - experimentGUI.timeAtStart;

			experimentLogger.addTestcase(user, new Testcase(user,currentTunnel.getWidth()+"/"+currentTunnel.getLength()+"/"+currentTunnel.getDirection(),new Dimension(currentTunnel.getWidth(),currentTunnel.getLength()),MOUSESPEED,new Date(System.currentTimeMillis()),experimentGUI.hitCounter,user,gender));// logging the results
			// dann starten, sonst sind variablen wieder zurückgesetzt
			currentTunnel=experiment.getNext();						// loads every next tunnel
			experimentGUI.setNewTunnel(currentTunnel);		// Startet neues Tunnelszenario
		}else{
			experimentLogger.flush("c:\\");
			experimentGUI.finish();
		}
}

/*
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (!experiment.getList().isEmpty()){
			currentTunnel=experiment.getNext();//loads every next tunnel
			experimentGUI.setNewTunnel(currentTunnel);
			experimentLogger.addTestcase(user, new Testcase(user,currentTunnel.getWidth()+"/"+currentTunnel.getLength()+"/"+currentTunnel.getDirection(),new Dimension(currentTunnel.getWidth(),currentTunnel.getLength()),MOUSESPEED,new Date(System.currentTimeMillis()),experimentGUI.hitCounter,user,gender));// logging the results
			
		}else{
			experimentLogger.flush("c:\\");
		}		
	}
*/
}
