//package mmi;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunP26 extends JFrame
{
	private JButton start;
	private JButton cancel;
	String[] parameters=new String[4];
	
	String[] gender = {"m", "w"};
	String[] speed 	= {"0", "1", "2"};
	
	private javax.swing.JTextField inputName;
	private javax.swing.JComboBox inputGender; 
	private javax.swing.JComboBox inputSpeed;
	private javax.swing.JTextField inputPath;
	private javax.swing.JLabel nameLabel;
	private javax.swing.JLabel genderLabel;
	private javax.swing.JLabel speedLabel;
	private javax.swing.JLabel pathLabel;

	public RunP26()
	{
		this.setSize(1000, 70);
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
				
		nameLabel		= new JLabel("Name:");
		inputName 	= new JTextField(15);
		
		genderLabel	= new JLabel("Gender:");
		inputGender = new JComboBox( gender );		
		
		speedLabel	= new JLabel("Mousespeed:");           
		inputSpeed 	= new JComboBox( speed );
		
		pathLabel		= new JLabel("Results (Path):");
		inputPath 	= new JTextField("C:\\", 20);
		
		start 			= new JButton("Start");
		cancel 			= new JButton("Cancel");
		
		// Startbutton Listener
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				// Name 
				parameters[0]	= inputName.getText();;					
				// Gender			
				parameters[1]	= (String) inputGender.getSelectedItem();
				// Geschwindigkeit
				parameters[2] = (String) inputSpeed.getSelectedItem();		
				// Pfad
				parameters[3]	= inputPath.getText();
				
				// GUI Title <- System.out.println( parameters[0] + ";" + parameters[1] + ";" + parameters[2] + ";" + parameters[2] + ";");
				
				try
				{
					P26 myProg = new P26( parameters[0], parameters[1], parameters[2], parameters[3] );
				} catch (AWTException e1)
				{
					e1.printStackTrace();
				}
			}		
		});
		
		// Cancelbutton Listener
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) { System.exit(0); }
		});
	
		contentPane.add(nameLabel);
		contentPane.add(inputName);
		contentPane.add(genderLabel);
		contentPane.add(inputGender);
		contentPane.add(pathLabel);            
		contentPane.add(inputPath);
		contentPane.add(speedLabel);
		contentPane.add(inputSpeed);
		
		contentPane.add(start);
		contentPane.add(cancel);
		
		this.setVisible(true);
	
		//paint();  
	}

	private void paint()
	{
	}
	
	public static void main(String[] args)
	{        	
		RunP26 view = new RunP26();
	}

}
