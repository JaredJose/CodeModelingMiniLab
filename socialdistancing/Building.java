package socialdistancing;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/* 
	Building extends JPanel so that we can override the paint method. The paint method is necessary to use the simple
	drawing tools of the library! 
	Simulator implements an ActionListener which adds the method actionPerformed. This method is invoked by the 
	animation timer every timerValue(16ms).
*/
public class Building extends JPanel implements ActionListener{
	// serial suppresses warning
	private static final long serialVersionUID = 1L;
	
	//simulation control objects/values
	JFrame frame;
	Control control; //
	Timer timer; //Event control	
	int time = 0; //Track time as the simulation runs
	
	/*
	 Wall vWall1 = new Wall(550, 0, "SocialDistancingImages/wall2.png", true);
	 Wall vWall2 = new Wall(200, 0, "SocialDistancingImages/wall2.png", true);
	 Wall vWall3 = new Wall(550, 400, "SocialDistancingImages/wall2.png", true);
	 Wall vWall4 = new Wall(200, 400, "SocialDistancingImages/wall2.png", true);
	
	 Wall hWall1 = new Wall(620, 160, "SocialDistancingImages/wall1.png", false);
	 Wall hWall2 = new Wall(-25, 160, "SocialDistancingImages/wall1.png", false);
	 Wall hWall3 = new Wall(620, 400, "SocialDistancingImages/wall1.png", false);
	 Wall hWall4 = new Wall(-25, 400, "SocialDistancingImages/wall1.png", false);
	 Wall[] walls = {vWall1, hWall1, vWall2, hWall2, vWall3, hWall3, vWall4, hWall4};
	 Rectangle[] r = {vWall1.getBounds(), hWall1.getBounds(), vWall2.getBounds(), hWall2.getBounds(),
			vWall3.getBounds(), hWall3.getBounds(), vWall4.getBounds(), hWall4.getBounds()};
			*/
	
	ArrayList<Wall> walls = new ArrayList<Wall>();
	int[][] coords = {{550, 0}, {200, 0}, {550, 400}, {200, 400}, {620, 160}, {-25, 160}, {620, 400}, {-25, 400}};
	
	/* constructor will setup our main Graphic User Interface - a simple Frame! */
	public Building(Control ctl, String title) {
		
		// used for Control callback
		this.control = ctl;
		
		//Setup the GUI
		frame = new JFrame(title);
		frame.setSize(ctl.frameX,ctl.frameY); //set the size
		
		//add this so that hitting the x button will actually end the program
		//the program will continue to run behind the scenes and you might end up with 10+ of them
		//without realizing it
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//make it visible
		frame.setVisible(true);
		frame.add(this); //add this class (JPanel) to the JFrame
	}
	
	//activation of Simulator separated from Constructor 
	public void activate() {
		//Timer for animation
		//Argument 1: timerValue is a period in milliseconds to fire event
		//Argument 2:t any class that "implements ActionListener"
		timer = new Timer(control.timerValue, this); //timer constructor
		timer.restart(); //restart or start
		
		// frame becomes visible
		frame.setVisible(true);		
	}
	
	/* This invoked by Timer per period in milliseconds in timerValue  */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Triggers paint call through polymorphism
		repaint();	
	}

	/* paint method for drawing the simulation and animation */
	@Override
	public void paint(Graphics g) {
		
		//tracking total time manually with the time variable
		time += control.timerValue;
		
		//events
		super.paintComponent(g); // a necessary call to the parent paint method, required for proper screen refreshing
		paintWalls(g);
		control.paintPersons(g); // repaint all objects in simulation
		
	} 
	
	public void createWalls() {
		for(int i = 0; i < 8; i++) {
			if(i < 4) {
				walls.add(new Wall(coords[i][0], coords[i][1], "SocialDistancingImages/wall2.png", true));
			}
			if(i >= 4) {
				walls.add(new Wall(coords[i][0], coords[i][1], "SocialDistancingImages/wall1.png", false));
			}
		}
	}
	
	public void paintWalls(Graphics g) {

		for (Wall wall : walls) {
			g.drawImage(wall.getImage(), wall.getX(), wall.getY(), this);
		}
		
		//sets text color
		g.setColor(Color.BLACK);
		g.setFont(new Font("Roboto", Font.BOLD, 20));
		
		g.drawString("Sprouts", 610, 50);
		g.drawString("Scripps Medical", 5, 50);
		g.drawString("Board and Brew", 5, 440);
		g.drawString("Mr. M's House", 590, 440);
		
	}
		
	
}