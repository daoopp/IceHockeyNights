package ice.hockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class Puck extends Drawable {
	private final FloatBuffer mTriangle1Vertices;
	private final int mBytesPerFloat = 4;
	private final int mPositionOffset = 0;
	private final int mPositionDataSize = 3;
	private final int mColorOffset = 3;
	private final int mColorDataSize = 4;
	private final int mStrideBytes = 7 * mBytesPerFloat;
	
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
	
	final float[] triangle1VerticesData = {
            -0.5f, -0.2f, 0.0f, 
            1.0f, 0.0f, 0.0f, 1.0f,

            0.5f, -0.2f, 0.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 0.5f, 0.0f, 
            0.0f, 1.0f, 0.0f, 1.0f };
			
	public Puck() {		
		mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		mTriangle1Vertices.put(triangle1VerticesData).position(0);
	}
	
	public void setHandles(int position, int color, int matrix, int sProgram) {
		this.shaderProgram = sProgram;
		this.mMVPMatrixHandle = matrix;
		this.mPositionHandle = position;
		this.mColorHandle = color;
	}
	
	public void setMatrix(float[] projectionMatrix) {
		this.mProjectionMatrix = projectionMatrix;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw() {			   
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 0.0f, -1.0f, 0.0f);
		Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);        

		mTriangle1Vertices.position(mPositionOffset);
		GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
        		mStrideBytes, mTriangle1Vertices);                      
        GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
        mTriangle1Vertices.position(mColorOffset);
		GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
        		mStrideBytes, mTriangle1Vertices);              
        GLES20.glEnableVertexAttribArray(mColorHandle);
        
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
	}
}
