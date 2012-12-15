package com.kryd.kdroid.calculator.all.in.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

	public Button calcButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		calcButton = (Button) findViewById(R.id.calcButton);
		calcButton.setOnClickListener(mainMenuListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	OnClickListener mainMenuListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == calcButton.getId()) {
				startActivity(new Intent(Main.this, Calculator.class));
			}
		}
	};

}
