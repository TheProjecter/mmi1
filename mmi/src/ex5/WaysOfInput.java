package ex5;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WaysOfInput extends JFrame implements MouseListener, MouseMotionListener, ActionListener, KeyListener {

	protected final static int BUTTONSIZE = 100;
	
	protected static Robot robot;
	
	public static final String NORMAL = "NORMAL";
	public static final String SEPSPACE = "SEPSPACE";
	public static final String DWELL = "DWELL";
	
	protected static String mode = NORMAL;
	
	protected WaitThread waitThread;
	protected boolean simulatedClick = false;
	
	protected static JRadioButton radNormal;
	
	JPanel mainPanel;
	JPanel optionPanel;
	JTextField textField;
	
	protected boolean ignoreME = false;

	private static long firstInputTime = 0;
	
	private static long lastInputTime = 0;

	protected static long end = 0;

	protected static long maximized = 0;
	
	private static long start = 0;
	
	
	/**
	 * Main entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		final WaysOfInput woi = new WaysOfInput();
		
		start = System.currentTimeMillis();
		
		woi.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	        end = System.currentTimeMillis();
            	        JFileChooser fileChooser = new JFileChooser();
            	        fileChooser.showSaveDialog(woi);
            	        if(fileChooser.getSelectedFile()!=null){
            	        	File file = fileChooser.getSelectedFile();
            	        	try {
								FileWriter writer = new FileWriter(file);
								writer.write("Start>Maximize;Maximize>InputStart;InputStart>InputEnd;InputEnd>End");
								writer.write(System.getProperty("line.separator"));
								writer.write(new Long(maximized-start).toString());
								writer.write(";");
								writer.write(new Long(firstInputTime-maximized).toString());
								writer.write(";");
								writer.write(new Long(lastInputTime-firstInputTime).toString());
								writer.write(";");
								writer.write(new Long(end-lastInputTime).toString());
								writer.write(System.getProperty("line.separator"));
								writer.flush();
								writer.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            	        }
                System.exit(0);
            }
            public void windowLostFocus(WindowEvent e) {
            	super.windowLostFocus(e);
            	radNormal.doClick();
            }
            public void windowDeactivated(WindowEvent e) {
            	super.windowDeactivated(e);
            	radNormal.doClick();
            }
            public void windowStateChanged(WindowEvent e) {
            	super.windowStateChanged(e);
            	if(e.getNewState()==Frame.MAXIMIZED_BOTH){
            		maximized = System.currentTimeMillis();
            	}
            };
            
        });
		
		woi.addComponentListener(new ComponentAdapter(){
		@Override
		public void componentResized(ComponentEvent e) {
			super.componentResized(e);
			maximized = System.currentTimeMillis();
		}	
		});
		
		woi.setVisible(true);
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
	
	
	public WaysOfInput() {
		Dimension dim = new Dimension(640, 480);
		this.setSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.setPreferredSize(dim);
		
		this.setTitle("Test of different ways of input ...");
		
		this.addKeyListener(this);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.BLUE);
		mainPanel.setFocusable(true);
		
		mainPanel.addMouseMotionListener(this);
		
		dim = new Dimension(600, 400);
		mainPanel.setSize(dim);
		mainPanel.setMinimumSize(dim);
		mainPanel.setMaximumSize(dim);
		mainPanel.setPreferredSize(dim);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = col;
				c.gridy = row;
				c.fill = GridBagConstraints.BOTH;
				JButton butt = new JButton(new Integer(3*row + col +1).toString());
				butt.addMouseMotionListener(this);
				butt.addMouseListener(this);
				dim = new Dimension(BUTTONSIZE, BUTTONSIZE);
				butt.setPreferredSize(dim);
//				butt.setSize(dim);
//				butt.setMinimumSize(dim);
				butt.setFocusable(false);
				mainPanel.add(butt, c);
			}
		}
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton butt = new JButton("0");
		butt.addMouseMotionListener(this);
		butt.addMouseListener(this);
		butt.setFocusable(false);
		dim = new Dimension(3*BUTTONSIZE, BUTTONSIZE);
		butt.setPreferredSize(dim);
//		butt.setSize(dim);
//		butt.setMinimumSize(dim);
		mainPanel.add(butt, c);
		
		mainPanel.addKeyListener(this);
		
		
		optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		this.getContentPane().add(optionPanel, BorderLayout.EAST);
		optionPanel.setFocusable(false);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(150, 30));
		optionPanel.add(textField);
		
		radNormal = new JRadioButton("Normal", true);
		radNormal.setActionCommand(NORMAL);
		JRadioButton radSepClick = new JRadioButton("Spacebar", true);
		radSepClick.setActionCommand(SEPSPACE);
		JRadioButton radDwell = new JRadioButton("Dwell Time", true);
		radDwell.setActionCommand(DWELL);
		ButtonGroup radGroup = new ButtonGroup();
		radGroup.add(radNormal);
		radGroup.add(radSepClick);
		radGroup.add(radDwell);
		radNormal.addActionListener(this);
		radSepClick.addActionListener(this);
		radDwell.addActionListener(this);
		optionPanel.add(radNormal);
		optionPanel.add(radSepClick);
		optionPanel.add(radDwell);
		
		
		waitThread = new WaitThread(this);
		waitThread.start();
		mainPanel.requestFocusInWindow();
	}
	

	public void dwelled() {
		if (mode.equals(DWELL)) {
			try {
				simulatedClick = true;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			} catch (ClassCastException cce) {
				// ignore
			}
		}
	}
	
	
	public void mouseMoved(MouseEvent e) {
		waitThread.ping();
	}


	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
		if (mode.equals(SEPSPACE))
			mainPanel.requestFocusInWindow();
	}


	public void mouseClicked(MouseEvent e) {
		if (mode.equals(NORMAL) || simulatedClick) {
			if (e.getSource() instanceof JButton) {
				textField.setText(textField.getText() + ((JButton)e.getSource()).getText());
				if(firstInputTime == 0){
					firstInputTime = System.currentTimeMillis();
				}else if(textField.getText().equals("13975222")){
					lastInputTime = System.currentTimeMillis();
				}
			}
		}
		simulatedClick = false;
	}


	public void keyTyped(KeyEvent e) {
		if (mode.equals(SEPSPACE)) {
			if (e.getKeyChar() == ' ') {
				simulatedClick = true;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}
	}


	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
	}
}