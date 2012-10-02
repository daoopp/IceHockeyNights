package ice.hockey;

public abstract class Drawable {	
	protected final float deg2rad(double deg) {
		  return (float) (deg * Math.PI / 180.0);
	}

	abstract public void release();
}
