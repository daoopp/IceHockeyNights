package ice.hockey;

import android.opengl.GLES20;

public class Shader {	
	public static int getShaderProgram() {
		int programHandle = GLES20.glCreateProgram();
		
		if (programHandle != 0) 
		{
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, getShader(GLES20.GL_VERTEX_SHADER));			

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, getShader(GLES20.GL_FRAGMENT_SHADER));
			
			// Bind attributes
			GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
			GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
			
			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// If the link failed, delete the program.
			if (linkStatus[0] == 0) 
			{				
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}
		
		return programHandle;
	}
	
	private static int getShader(int type) {
		int shaderHandle = GLES20.glCreateShader(type);

		if (shaderHandle != 0) 
		{
			if(type == GLES20.GL_FRAGMENT_SHADER)
				GLES20.glShaderSource(shaderHandle, fragmentShader);
			else
				GLES20.glShaderSource(shaderHandle, vertexShader);
			// Compile the shader.
			GLES20.glCompileShader(shaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0) 
			{				
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}
		
		return shaderHandle;
	}
	
	public static final String vertexShader = 
	    "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
	
	  + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
	  + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.			  
	  
	  + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
	  
	  + "void main()                    \n"		// The entry point for our vertex shader.
	  + "{                              \n"
	  + "   v_Color = a_Color;          \n"		// Pass the color through to the fragment shader. 
	  											// It will be interpolated across the triangle.
	  + "   gl_Position = u_MVPMatrix   \n" 	// gl_Position is a special variable used to store the final position.
	  + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in 			                                            			 
	  + "}                              \n";    // normalized screen coordinates.

	public static final String fragmentShader =
		"precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a precision in the fragment shader.	
		+ "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the triangle per fragment.		  
		+ "void main()                    \n"		// The entry point for our fragment shader.
		+ "{                              \n"
		+ "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.		  
		+ "}                              \n";	
}
