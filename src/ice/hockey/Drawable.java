package ice.hockey;

public abstract class Drawable {
	protected int shaderProgram;
    protected int vertexStride;

	protected float[] mModelMatrix = new float[16];
	protected float[] mMVPMatrix = new float[16];
	protected float[] mViewMatrix = new float[16];
    float[] mProjectionMatrix;
    
    protected float angleInDegrees;
    
	public abstract void draw();
	public abstract void update();
	
	protected final float deg2rad(double deg) {
		  return (float) (deg * Math.PI / 180.0);
	}
}
