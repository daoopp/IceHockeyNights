package ice.hockey;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public abstract class Drawable {
    protected int mProgram;
    protected final int COORDS_PER_VERTEX = 3;
    protected int vertexStride;
    protected int vertexShader;
    protected int fragmentShader;
	protected FloatBuffer objectBuffer;
    
	public abstract void draw(float[] mvpMatrix);
	public abstract void update();
	
    public Drawable() {
        vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                  fragmentShaderCode);
    }
	
	protected final float deg2rad(double deg) {
		  return (float) (deg * Math.PI / 180.0);
	}
	
    protected final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition * uMVPMatrix;" +
            "}";

    protected final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";
    
	protected final int loadShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		
		return shader;
	}
}
