package flockbase;

abstract public class Bird {
	protected Bird() {
		pos = new Position(0, 0);
		target = new Position(0, 0);
	}

	public String getName() {
		return "Bird";
	}

	public void setFlock(Flock f) {
		flock = f;
	}

	public Flock getFlock() {
		return flock;
	}

	// Set the position of the bird
	public void setPos(int x, int y) {
		pos.setPos(x, y);
	}

	public Position getPos() {
		return pos;
	}

	// point the bird to a target it should fly towards
	//
	// The leader also gets to decide the speed at which the flock will fly -
	// how far it will fly with one "flap"
	public void setTarget(int xt, int yt) {
		target.setPos(xt, yt);
	}

	protected Position getTarget() {
		return target;
	}

	// invoked during the "run" process by the thread
	// Do not change this unless you really need to.
	// In that case, make sure this base class implementation is invoked
	public void flap() {
		updatePos();
	}

	// this bird is now nominated as the leader
	abstract public void becomeLeader();

	// this bird is asked to move out of the leader position
	abstract public void retireLead();

	// to be implemented by derived classes to make the bird move for one
	// timestep
	abstract protected void updatePos();

	public static int getMaxSpeed() 
	{
		return maxSpeed;
	}
	
	public static void setMaxSpeed(int speed) {
		maxSpeed = speed;
	}
	
	private static int maxSpeed = 10;
	private Position pos;
	private Flock flock;
	private Position target;
}
