package com.travelcheck;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Follow_me extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.follow_me);
}
}