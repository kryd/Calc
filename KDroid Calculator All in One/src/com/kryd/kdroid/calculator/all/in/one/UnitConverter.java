package com.kryd.kdroid.calculator.all.in.one;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class UnitConverter extends Activity implements OnItemSelectedListener {

	private String[] category = { "Length", "Weight and Mass", "Capacity",
			"Temperature", "Volume" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unit_converter);

		Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		categorySpinner.setOnItemSelectedListener(this);
		ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, category);

		categoryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		categorySpinner.setAdapter(categoryAdapter);

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
