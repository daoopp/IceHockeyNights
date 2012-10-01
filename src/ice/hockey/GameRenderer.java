package ice.hockey;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class GameRenderer implements GLSurfaceView.Renderer {
	Puck puck;
	Shader shader;
	
	private float[] mViewMatrix = new float[16];

	private float[] mProjectionMatrix = new float[16];
	private int mMVPMatrixHandle;
	private int mPositionHandle;
	private int mColorHandle;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		shader = new Shader();
        mMVPMatrixHandle = GLES20.glGetUniformLocation(shader.getShaderProgram(), "u_MVPMatrix");        
        mPositionHandle = GLES20.glGetAttribLocation(shader.getShaderProgram(), "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(shader.getShaderProgram(), "a_Color");

		puck = new Puck();
		puck.setHandles(mPositionHandle, mColorHandle, mMVPMatrixHandle, shader.getShaderProgram());
		
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = 1.5f;
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;

		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
		puck.setMatrix(mProjectionMatrix);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	    
		puck.draw();
	}
}
