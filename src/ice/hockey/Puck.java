package ice.hockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class Puck extends Drawable {
	static final int POSITION_DATA_SIZE = 3;	
	static final int NORMAL_DATA_SIZE = 3;
	static final int COLOR_DATA_SIZE = 4;
	
	private float[] mModelMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private final float[] mLightPosInEyeSpace = new float[4];
	
	final int vboIds[] = new int[3];
	
	private final FloatBuffer positionVertices;
	private final FloatBuffer colorVertices;
	private final FloatBuffer normalVertices;
	private final int mBytesPerFloat = 4;
	
    private float angleInDegrees = 0;
	
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
	private int MVMatrixHandle;
	private int lightHandle;
	private int normalHandler;

	private final float[] positionVector = {
            -0.5f, -0.25f, 0.0f,         
            0.5f, -0.25f, 0.0f,           
            0.0f, 0.55f, 0.0f                 
	};
	
	private final float[] colorVector = {
			1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
	};
	
	private final float[] normalVector = {
			0f, 0f, 1f,				
			0f, 0f, 1f,				
			0f, 0f, 1f,			
	};
		
	public Puck() {		
		positionVertices = ByteBuffer.allocateDirect(positionVector.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		positionVertices.put(positionVector).position(0);
		
		colorVertices = ByteBuffer.allocateDirect(colorVector.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		colorVertices.put(colorVector).position(0);
		
		normalVertices = ByteBuffer.allocateDirect(normalVector.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		normalVertices.put(normalVector).position(0);
		
		
		GLES20.glGenBuffers(3, vboIds, 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, positionVertices.capacity() * mBytesPerFloat, positionVertices, GLES20.GL_STATIC_DRAW);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[1]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, colorVertices.capacity() * mBytesPerFloat, colorVertices, GLES20.GL_STATIC_DRAW);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[2]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalVertices.capacity() * mBytesPerFloat, normalVertices,
				GLES20.GL_STATIC_DRAW);
		
	}
	
	public void setMatrix(float[] projectionMatrix, float[] viewMatrix) {
		this.mProjectionMatrix = projectionMatrix;
		this.mViewMatrix = viewMatrix;
	}
	
	public void draw(float[] mLightPosInEyeSpace2) {
        Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);  
		Matrix.translateM(mModelMatrix, 0, -0.9f, -0.7f, 0.0f);
        
		GLES20.glUniformMatrix4fv(MVMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glUniform3f(lightHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);
		
		// Pass in the position information
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[0]);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, 0);
		
		// Pass in the color information
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[1]);
		GLES20.glEnableVertexAttribArray(mColorHandle);
		GLES20.glVertexAttribPointer(mColorHandle, COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, 0);
		
		// Pass in the normal information
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[2]);
		GLES20.glEnableVertexAttribArray(normalHandler);
		GLES20.glVertexAttribPointer(normalHandler, NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, 0, 0);
		
       
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
	}

	public void setHandles(int[] handles) {
		this.mMVPMatrixHandle = handles[0];
		this.MVMatrixHandle = handles[1];
		this.lightHandle = handles[2];
		this.mPositionHandle = handles[3];
		this.mColorHandle = handles[4];
		this.normalHandler = handles[5];
	}
}
