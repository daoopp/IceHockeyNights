package ice.hockey;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class GameRenderer implements GLSurfaceView.Renderer {
	Puck puck;
	
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
	
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

	    Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
	    Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, -1.0f);
	    Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);
	    
		puck.draw(mMVPMatrix);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0,0,width,height);
		
		float ratio = (float) width / height;

	    Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {	
		/* Initialise graphics and variables */
		GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		puck = new Puck();
	}
}
