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
	private final int BYTES_PER_FLOAT = 4;
	
	private float[] modelMatrix = new float[16];
	private float[] MVPMatrix = new float[16];
	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	
	private final int vboIds[] = new int[3];
	
	private final FloatBuffer positionVertices;
	private final FloatBuffer colorVertices;
	private final FloatBuffer normalVertices;
	
    private float angleInDegrees = 0;
    
    private int positionHandle;
    private int colorHandle;
	private int lightHandle;
	private int normalHandler;   
    private int mMVPMatrixHandle;
	private int MVMatrixHandle;

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
		positionVertices = ByteBuffer.allocateDirect(positionVector.length * BYTES_PER_FLOAT)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		positionVertices.put(positionVector).position(0);
		
		colorVertices = ByteBuffer.allocateDirect(colorVector.length * BYTES_PER_FLOAT)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		colorVertices.put(colorVector).position(0);
		
		normalVertices = ByteBuffer.allocateDirect(normalVector.length * BYTES_PER_FLOAT)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();	
		normalVertices.put(normalVector).position(0);
		
		GLES20.glGenBuffers(3, vboIds, 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, positionVertices.capacity() * BYTES_PER_FLOAT, positionVertices, GLES20.GL_STATIC_DRAW);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[1]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, colorVertices.capacity() * BYTES_PER_FLOAT, colorVertices, GLES20.GL_STATIC_DRAW);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[2]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalVertices.capacity() * BYTES_PER_FLOAT, normalVertices,
				GLES20.GL_STATIC_DRAW);
	}
	
	public void setMatrix(float[] projectionMatrix, float[] viewMatrix) {
		this.projectionMatrix = projectionMatrix;
		this.viewMatrix = viewMatrix;
	}
	
	public void draw(float[] lightPosInEyeSpace) {
        Matrix.setIdentityM(modelMatrix, 0);
		Matrix.rotateM(modelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);  
		Matrix.translateM(modelMatrix, 0, 0.0f, 0.0f, 0.0f);
		
        Matrix.multiplyMM(MVPMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, MVPMatrix, 0);
        
        GLES20.glUniformMatrix4fv(MVMatrixHandle, 1, false, MVPMatrix, 0);
        
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[0]);
		GLES20.glEnableVertexAttribArray(positionHandle);
		GLES20.glVertexAttribPointer(positionHandle, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, 0);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[1]);
		GLES20.glEnableVertexAttribArray(colorHandle);
		GLES20.glVertexAttribPointer(colorHandle, COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, 0);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[2]);
		GLES20.glEnableVertexAttribArray(normalHandler);
		GLES20.glVertexAttribPointer(normalHandler, NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, 0, 0);
		
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MVPMatrix, 0);
                
        GLES20.glUniform3f(lightHandle, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
	}

	public void setHandles(int[] handles) {
		this.mMVPMatrixHandle = handles[0];
		this.MVMatrixHandle = handles[1];
		this.lightHandle = handles[2];
		this.positionHandle = handles[3];
		this.colorHandle = handles[4];
		this.normalHandler = handles[5];
	}
	
	@Override
	public void release() {    
		GLES20.glDeleteBuffers(vboIds.length, vboIds, 0);
	}
}
