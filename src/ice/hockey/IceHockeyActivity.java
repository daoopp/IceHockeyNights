package ice.hockey;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class IceHockeyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_hockey);
        
        Button start = (Button) findViewById(R.id.StartSingle);
        Button join = (Button) findViewById(R.id.JoinMulti);
        Button create = (Button) findViewById(R.id.CreateMulti);
        
        start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startSinglePlayerGame();
			}
        });
        
        join.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				joinMultiPlayerGame();
			}
        });
        
        create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createMultiplayerGame();
			}
        });
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ice_hockey, menu);
        return true;
    }
	
    protected void joinMultiPlayerGame() {
		
	}

	protected void startSinglePlayerGame() {
		setContentView(new IceHockeyGLView(this));
	}
	
	protected void createMultiplayerGame() {
		
	}
    
}
