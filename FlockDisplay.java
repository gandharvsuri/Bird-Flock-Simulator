import flockbase.Bird;

// Base class for View component of MVC architecture

// allows us to easily switch between text-based UI and Swing display UI, for example

interface FlockDisplay {
	// set up 2 way connection between App and FlockDisplay if needed. To be called by method setting up
	// app and display
	void setApp(App app);
	
	// Will be called by app at init time - and before call to any other display method
	// Any initialization needed should be done here
	void init();
	
	// Adds a bird to the list of displayed objects.
	// Called by app for each bird for each timestep. 
	// Actual refresh of display happens only when update called
	void draw(Bird Bird);
	
	// Called by the app to let derived classes update themselves
	void update();
}
