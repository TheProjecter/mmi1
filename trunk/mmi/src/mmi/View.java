package mmi;

import java.awt.*;
import javax.swing.*;

public class View extends JFrame{

	private JButton start;
	private JButton stop;
	
	public View(){
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setLayout(new FlowLayout());
		this.setSize(1200, 1200);
		
		start = new JButton("Start");
		stop = new JButton("Stop");

		this.getContentPane().add(start);
		this.getContentPane().add(stop);
		
		this.setVisible(true);
		//this.pack();

	}
	
	public static void main(String[] args){
		View view = new View();
	}
}
