package ice.hockey;

import android.opengl.GLES20;

public class Shader {
    private int programHandle;
   	private int vertexShader;
    private int fragmentShader;
    
    public Shader() {
        vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        
        programHandle = GLES20.glCreateProgram();            
        GLES20.glAttachShader(programHandle, vertexShader); 
        GLES20.glAttachShader(programHandle, fragmentShader);
        
		GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
		GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
        
        GLES20.glLinkProgram(programHandle);
        GLES20.glUseProgram(programHandle);
    }
    
	protected final int loadShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		
		return shader;
	}
    
    public int getShaderProgram() {
    	return programHandle;
    }
    
    protected final String vertexShaderCode =
    		"uniform mat4 u_MVPMatrix;      \n"		
  		  + "attribute vec4 a_Position;     \n"		
  		  + "attribute vec4 a_Color;        \n"					  

  		  + "varying vec4 v_Color;          \n"	

  		  + "void main()                    \n"	
  		  + "{                              \n"
  		  + "   v_Color = a_Color;          \n"
  		  											
  		  + "   gl_Position = u_MVPMatrix   \n" 	
  		  + "               * a_Position;   \n"   		                                            			 
  		  + "}                              \n";    

    protected final String fragmentShaderCode =
    		"precision mediump float;       \n"
					
    		+ "varying vec4 v_Color;          \n"				  
    		+ "void main()                    \n"		
    		+ "{                              \n"
    		+ "   gl_FragColor = v_Color;     \n"	  
    		+ "}                              \n";
}
