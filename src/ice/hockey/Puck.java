package ice.hockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class Puck extends Drawable {
	private float[] mModelMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private final float[] mLightPosInEyeSpace = new float[4];
	
	private final FloatBuffer mTriangle1Vertices;
	private final int mBytesPerFloat = 4;
	private final int mPositionOffset = 0;
	private final int mPositionDataSize = 3;
	private final int mColorOffset = 3;
	private final int mColorDataSize = 4;
	private final int mNormalOffset = 3;
	private final int mNormalDataSize = 3;
	private final int mStrideBytes = 10 * mBytesPerFloat;
	
    private float angleInDegrees = 0;
	
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
	private int MVMatrixHandle;
	private int lightHandle;
	private int normalHandler;


	private final float[] triangle1VerticesData = {
            -0.5f, -0.25f, 0.0f, 
            1.0f, 0.0f, 0.0f, 1.0f,
            0f, 0f, 1f,				//Normal data
            
            0.5f, -0.25f, 0.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0f, 0f, 1f,				//Normal data
            
            0.0f, 0.559016994f, 0.0f, 
            0.0f, 1.0f, 0.0f, 1.0f, 
            0f, 0f, 1f,				//Normal data        
	};
		
	public Puck() {		
		mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		mTriangle1Vertices.put(triangle1VerticesData).position(0);
	}
	
	public void setMatrix(float[] projectionMatrix, float[] viewMatrix) {
		this.mProjectionMatrix = projectionMatrix;
		this.mViewMatrix = viewMatrix;
	}
	
	public void draw(float[] mLightPosInEyeSpace2) {
        Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);  
		Matrix.translateM(mModelMatrix, 0, 0.0f, -1.0f, 0.0f);
        
		GLES20.glUniformMatrix4fv(MVMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glUniform3f(lightHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);
		
		mTriangle1Vertices.position(mPositionOffset);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
        		mStrideBytes, mTriangle1Vertices);                     
        GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
        mTriangle1Vertices.position(mColorOffset);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
        		mStrideBytes, mTriangle1Vertices);          
        GLES20.glEnableVertexAttribArray(mColorHandle);
        
        mTriangle1Vertices.position(mNormalOffset);
        GLES20.glVertexAttribPointer(normalHandler, mNormalDataSize, GLES20.GL_FLOAT, false,
        		mStrideBytes, mTriangle1Vertices);          
        GLES20.glEnableVertexAttribArray(normalHandler);
       
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
