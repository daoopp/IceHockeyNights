package ice.hockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.opengl.GLES20;
import android.util.FloatMath;

public class Puck extends Drawable {
	private float color[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	private int vertexCount;
	
	public Puck() {
		int points = 40;
		float[] vertices = new float[(points+1)*3];

		//CENTER OF CIRCLE
		vertices[0]=0.0f;
		vertices[1]=0.0f;
		vertices[2]=0.0f;

		for (int i = 3; i<(points+1)*3; i+=3){
		    float rad = deg2rad(360.0 - i*360/(points*3));
		    vertices[i] = (float) (FloatMath.cos(rad));
		    vertices[i+1] = (float) (FloatMath.sin(rad));
		    vertices[i+2] = 0;
		}
		
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        objectBuffer = bb.asFloatBuffer();
        objectBuffer.put(vertices);
        objectBuffer.position(0);
        
        vertexCount = vertices.length / COORDS_PER_VERTEX;
        vertexStride = vertexCount * 4;

        mProgram = GLES20.glCreateProgram();            
        GLES20.glAttachShader(mProgram, vertexShader); 
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(float[] mvpMatrix) {		
        GLES20.glUseProgram(mProgram);

        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, objectBuffer);

        int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}
