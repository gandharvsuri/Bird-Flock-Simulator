package flockbase;

public class Position {
	// helper class
		public Position(int px, int py) {
			x = px;
			y = py;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setPos(int px, int py) {
			x = px;
			y = py;
		}

		public String toString() {
			return ("( " + x + ", " + y + " )");
		}
		
		private int x, y;
}
