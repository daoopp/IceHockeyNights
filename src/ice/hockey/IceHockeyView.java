package ice.hockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class IceHockeyView extends GLSurfaceView {
	GameRenderer renderer;
	
	public IceHockeyView(Context context) {
		super(context);
		renderer = new GameRenderer();
		
		setEGLContextClientVersion(2);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
}
