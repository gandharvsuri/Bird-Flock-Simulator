import flockbase.Bird;

public class TextDisplay implements FlockDisplay {

	App theApp;
	
	public TextDisplay() {
		
	}
	
	public void init() {
		System.out.println("Starting new simulation");
		theApp.start();
	}
	
	public void draw(Bird Bird) {
		
		System.out.println("Bird "+Bird.getName() + " at " + Bird.getPos());
		
	}

	@Override
	public void update() {
		// Nothing to do
		
	}

	@Override
	public void setApp(App app) {
		theApp = app;
		
	}

}
