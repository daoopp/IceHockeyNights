package ice.hockey;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class IceHockeyActivity extends Activity {
	IceHockeyView iceHockeyView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_hockey);
        
        iceHockeyView = new IceHockeyView(this);
        
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
	protected void onPause() {
		super.onPause();
		iceHockeyView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		iceHockeyView.onResume();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ice_hockey, menu);
        return true;
    }
	
    protected void joinMultiPlayerGame() {
		
	}

	protected void startSinglePlayerGame() {
		setContentView(iceHockeyView);
	}
	
	protected void createMultiplayerGame() {
		
	}
    
}
