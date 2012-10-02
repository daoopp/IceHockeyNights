package ice.hockey;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class GameRenderer implements GLSurfaceView.Renderer 
{
	private int[] handles = new int[6];
	private float[] mViewMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	float[] mLightModelMatrix = new float[16];
    float[] mLightPosInWorldSpace = new float[4];
    float[] mLightPosInEyeSpace  = new float[4];
	private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
	
	/* Game Objects */
	Puck puck;

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) 
	{
		// Set the background clear color to gray.
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
	
		// Position the eye behind the origin.
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = 1.5f;

		// We are looking toward the distance
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;

		// Set our up vector. This is where our head would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
										
		
		// Create a program object and store the handle to it.
		int programHandle = Shader.getShaderProgram();	
		if (programHandle == 0)
		{
			throw new RuntimeException("Error creating program.");
		}
        
		handles[0] = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        handles[1] = GLES20.glGetUniformLocation(programHandle, "u_MVMatrix"); 
        handles[2] = GLES20.glGetUniformLocation(programHandle, "u_LightPos");
        handles[3] = GLES20.glGetAttribLocation(programHandle, "a_Position");
        handles[4] = GLES20.glGetAttribLocation(programHandle, "a_Color");
        handles[5] = GLES20.glGetAttribLocation(programHandle, "a_Normal");        
        
        puck = new Puck();
        puck.setHandles(handles);
	}		

	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) 
	{
		// Set the OpenGL viewport to the same size as the surface.
		GLES20.glViewport(0, 0, width, height);

		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;
		
		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
		puck.setMatrix(mProjectionMatrix, mViewMatrix);
	}	

	@Override
	public void onDrawFrame(GL10 glUnused) 
	{
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
	
		Matrix.setIdentityM(mLightModelMatrix, 0);
        Matrix.translateM(mLightModelMatrix, 0, 1.0f, 0.0f, 0.0f);      
        //Matrix.rotateM(mLightModelMatrix, 0, 20f, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 0.0f);
               
		Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
		Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightPosInWorldSpace, 0);
		
		puck.draw(mLightPosInEyeSpace);
	}	
}