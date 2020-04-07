

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;


import javax.swing.*;

import flockbase.Bird;
import flockbase.Position;


class SwingDisplay implements FlockDisplay {
	
	private Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, 
								Color.MAGENTA, Color.BLACK };
	private JFrame dframe, gframe;
	private JPanel canvas = null;
	
	SwingDisplay() {
		birds = new ArrayList<Bird>();
	}
	
	@Override
	public void init() {
		// start the GUI in the EDT thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setupGUI();
			}
		});
	}
	
	@Override
	public void draw(Bird Bird) {
		// Just maintain the list of active birds
		// will get drawn when canvas gets updated/repainted
		birds.add(Bird);
		
	}
	
	@Override
	public void update() {
		if(canvas != null) {
			// forces paintComponent to get invoked
			canvas.repaint();
		}
	}
	
	@SuppressWarnings("serial")
	private void setupGUI() {
		
		dframe = new JFrame("View of Flocks");
		dframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dframe.setLayout(new BorderLayout());

		// set up a canvas for drawing the position of the Birds
		// implement how Birds should be rendered by overriding paintComponent
		canvas = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int i = 0;
				for(Bird bird: birds) {
					g.setColor(colors[i%6]);
					i++;
					Position loc = bird.getPos();
					//System.out.println(loc);
					// next few lines can be replaced with any other interesting way of drawing birds
					g.fillOval(loc.getX(), loc.getY(), 10, 10);
					g.drawString(bird.getName(),
						 loc.getX() , loc.getY() );
				}
				// done with drawing bird - not needed anymore
				birds.clear();
			}
		};

		canvas.setPreferredSize(new Dimension(1000, 1000));
		// clicking anywhwere on canvas should be interpreted as setting a new target for flock
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Clicked " + e.getX() + ", " + e.getY());
				app.setTarget(e.getX(), e.getY());
			}
		});
		
		dframe.add(canvas, BorderLayout.CENTER);
		
		// create button controls for various app activities
		gframe = new JFrame("Controls");
		gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gframe.setLayout(new BorderLayout());
		
		// "Start" button to star the simulation
		JButton b0 = new JButton("Start");
		gframe.add(b0, BorderLayout.PAGE_START);

		b0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.start();
				}
			}
		);

		// "Split Flock" to split the current flock into 2
		JButton b1 = new JButton("Split Flock");
		gframe.add(b1, BorderLayout.LINE_START);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// request trip
					app.splitFlock();
				}
			}
		);

		// "Merge Flock" tp merge flocks back into one
		JButton b2 = new JButton("Merge Flock");
		gframe.add(b2, BorderLayout.LINE_END);

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// request trip
					app.joinFlocks();
				}
			}
		);
		
		// "Exit" button to allow graceful exit
		JButton b3 = new JButton("Exit");
		gframe.add(b3, BorderLayout.PAGE_END);

		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		dframe.pack();
		dframe.setVisible(true); // starts EDT if not already started
		gframe.pack();
		gframe.setVisible(true); 
		// EDT continues until explicitly terminated with exit (or exception)

	}

	@Override
	public
	void setApp(App appC) {
		// TODO Auto-generated method stub
		app = appC;
	}

	private  App app;	// to allow communication back to app
	private ArrayList<Bird> birds; // temporarily needed for redisplay

	
}

