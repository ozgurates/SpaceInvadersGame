import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Timer;

import javax.swing.ButtonGroup;
	import javax.swing.JButton;
	import javax.swing.JCheckBox;
	import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
	import javax.swing.JRadioButton;
	import javax.swing.JTextField;



public class IntroMenu extends JFrame {
	
			
		FlowLayout f1;
		BorderLayout b1;
		GridLayout g1;
		JCheckBox j1;
		JCheckBox j2;
		JRadioButton x;
		JRadioButton y;
		ButtonGroup bg;
		JButton jbPlay;
		Timer timer;
		public static int second = 0;
		public static int minute = 0;
		DecimalFormat dFormat = new DecimalFormat("00");
		public String ddsecond,ddminute;
		
		JLabel counterLabel;
		JLabel angleLabel;
		JLabel velocityLabel;
		
		Timer timer2;
		Timer timer3;
		
		
		
		
		
		public IntroMenu() {
			super("Space Invaders");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			counterLabel = new JLabel("");
			counterLabel.setText("Timer: 00:00");
			counterLabel.setForeground(Color.white);
			
			
			angleLabel = new JLabel("");
			angleLabel.setText("Angle: 0.0");
			angleLabel.setForeground(Color.white);
			
			velocityLabel = new JLabel("");
			velocityLabel.setText("velocity: 0.0 km/s ");
			velocityLabel.setForeground(Color.white);
			
			
			north();
			center();
			south();
			
			
		}
		
		public void north() {
			JPanel jpNorth = new JPanel();
			add(jpNorth,BorderLayout.NORTH);
			
			jpNorth.setBackground(Color.black);
			
			JLabel lblA = new JLabel("WELCOME");
			lblA.setForeground(Color.white);
			jpNorth.add(lblA);
		}
		
		
		public void center() {
			JPanel jpCenter = new JPanel();
			add(jpCenter,BorderLayout.CENTER);
			
			jpCenter.setLayout(null);
			jpCenter.setBackground(Color.black);
		
			
			
			
			JLabel lblName = new JLabel("Full Name");
			lblName.setBounds(1,15,60,25);
			lblName.setForeground(Color.white);
			JTextField txtName = new JTextField();
			txtName.setBounds(80,15,90,25);
			
			
			JLabel lblx = new JLabel("First time playing?");
			lblx.setBounds(1,60,110,25);
			lblx.setForeground(Color.white);
			x = new JRadioButton("yes");
			x.setBounds(130,60,60,15);
			y = new JRadioButton("no");
			y.setBounds(190,60,60,15);
			
			bg= new ButtonGroup();
			bg.add(x);
			bg.add(y);
			
			jpCenter.add(lblName);
			jpCenter.add(txtName);
			jpCenter.add(lblx);
			jpCenter.add(x);
			jpCenter.add(y);
			jpCenter.add(angleLabel);
			
			
		}
		public void south() {
			JPanel jpSouth = new JPanel();
			add(jpSouth,BorderLayout.SOUTH);
			
			f1 = new FlowLayout();
			jpSouth.setLayout(f1);
			
			
			jpSouth.setBackground(Color.black);
			
			jbPlay = new JButton("Play");
			
			JButton jbExit = new JButton("Exit");
			jbExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
				
			});
			
			jpSouth.add(jbPlay);
			jpSouth.add(jbExit);
			jpSouth.add(counterLabel);
			jpSouth.add(angleLabel);
			jpSouth.add(velocityLabel);			
		}	
		
		
		public void drawTimer(){
			
			timer = new Timer(1000,new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					second++;
	    			ddsecond = dFormat.format(second);
	    			ddminute = dFormat.format(minute);
	    			
	    			counterLabel.setText("Timer: " +ddminute + ":" + ddsecond);
	    			
	    			if(second == 60) {
	    				second = 0;
	    				minute++;
	    				
	    				ddsecond = dFormat.format(second);
	        			ddminute = dFormat.format(minute);
	        			
	        			counterLabel.setText("Timer:" +ddminute + ":" + ddsecond);
	    			}
	    				    			
	    						
				}
			});
			timer.start();
		}
		
		public void drawAngle() {
			timer2 = new Timer(100,new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					angleLabel.setText("Angle: "+Board.xOfPlayer);
					
				}
			});
			timer2.start();
		}
		
		public void drawVelocity() {
			timer3 = new Timer(100,new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Board.isStopped) {
						velocityLabel.setText("Velocity: 0.0 km/s");
					}
					else {
						velocityLabel.setText("Velocity: 380 km/s");
					}
					
				}
			});
			timer3.start();
			
		}
	
}

