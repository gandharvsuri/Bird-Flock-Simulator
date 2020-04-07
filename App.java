import java.util.ArrayList;

import flockbase.Bird;
import flockbase.Flock;

// Implements the Controller in the MVC architecture

class App {

	App(FlockDisplay d) {
		disp = d;
	}

	// initilize the app - updates the initial state of the display
	void init(Flock flock) {
		currentFlock = flock;
		birds = new ArrayList<>(flock.getBirds());
		newFlock = null;

		// allow the display to initialize anything needed there
		disp.init();
	}

	// start the "simulation" of the flight 
	// Starts threads to simulate movement of Birds and to manage the display
	void start() {
		for (Bird bird : birds) {
			// each bird is on a separate thread - allows them to "flap"
			// independently
			Thread t = new Thread() {
				public void run() {
					for (int i = 0; i < 10000; i++) {
						bird.flap();
						try {
							Thread.sleep(flapTimeStep);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			t.start();
		}

		// set up another thread to refresh the display
		Thread simulator = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					updateDisplay();
					try {
						Thread.sleep(dispTimeStep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		simulator.start();
	}

	private void updateDisplay() {
		// forces a redisplay by asking Display to redraw each bird.
		// Assumes Display does not keep track of list of active birds
		for (Bird bird : birds) {		
			disp.draw(bird);
		}
		disp.update();
	}

	void splitFlock() {
		// simple implementation for now - will change
		newFlock = currentFlock.split(birds.size() / 2);
		newFlock.getLeader().setTarget(800, 100);
	}

	void joinFlocks() {
		// for now, assumes at most 2 flocks. Will change
		if (newFlock != null) {
			newFlock.joinFlock(currentFlock);
		}
	}
	
	void setTarget(int x, int y) {
		System.out.println("New target: "+ x + " + " + y);
		currentFlock.getLeader().setTarget(x, y);
	}
	
	void setLeader(Bird b){
		// used to change leader of current flock
		currentFlock.setLeader(b);
	}
	

	private FlockDisplay disp;
	private int dispTimeStep = 200; // millisecs - change this as needed
	private int flapTimeStep = 500; // millisecs - change this as needed
	private ArrayList<Bird> birds;
	private Flock currentFlock, newFlock;

}
