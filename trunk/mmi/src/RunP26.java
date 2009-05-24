//package mmi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RunP26 extends JFrame{

        private JButton start;
        private JButton cancel;
        String[] parameters=new String[4];
        private javax.swing.JTextField inputName; 
        private javax.swing.JTextField inputGender;
        private javax.swing.JTextField inputSpeed;
        private javax.swing.JTextField inputPath;
        //private javax.swing.JTextField inputMouseSpeed;
        
        public RunP26(){
        	paint();  
         }
        
        private void paint(){ 
        	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // this.setLayout(new FlowLayout(FlowLayout.CENTER));
             Container contentPane = this.getContentPane();
             contentPane.setLayout(new FlowLayout());
             

             this.setSize(400, 100);
             inputName = new JTextField("Your Name");                
             inputGender = new JTextField("gender");               
             inputSpeed = new JTextField("mouse speed 0/1/2?");                
             inputPath = new JTextField("Where to save the results from the tests.");
            // inputMouseSpeed = new JTextField("Mouse Speed");

             start = new JButton("Start");
             cancel = new JButton("Cancel");
             start.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {  
                 	parameters[0]=inputName.getText();;
                 	parameters[1]=inputGender.getText();
                 	parameters[2]=inputSpeed.getText();
                 	parameters[3]=inputPath.getText();
                 	System.out.println(parameters[0]+";" +parameters[1]+";"+parameters[2]+";"+parameters[2]+";");

                 	try {
            			P26 myProg = new P26(parameters[0],parameters[1],parameters[2],parameters[3]);            			
            		} catch (AWTException e1) {
            			// TODO Auto-generated catch block
            			e1.printStackTrace();
            		}
            		
                 }
               });
             cancel.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                 	System.exit(0);
                 }
               });


             contentPane.add(inputName);
             contentPane.add(inputGender);
             contentPane.add(inputPath);
             contentPane.add(inputSpeed);
             contentPane.add(start);
             contentPane.add(cancel);
             
             this.setVisible(true);
             //this.pack();
        }
        public static void main(String[] args){        	
        	RunP26 view = new RunP26();
        	}
}
