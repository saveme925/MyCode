package com.travelcheck;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ShareImagePopUp extends Activity implements OnClickListener {

	public Button ShareBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle("Share Screen");
		setContentView(R.layout.share_image);
		Initialize();
		Toast.makeText(this, "Share your picture now", 0).show();

	}

	public void Initialize() {
		ShareBtn = (Button) findViewById(R.id.ShareBtn);
		ShareBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("image/jpg");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "someone@gmail.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Please find attached image");
		i.putExtra(Intent.EXTRA_STREAM,
				Uri.parse("file:///mnt/sdcard/myImage.gif"));
		startActivity(i);
	}
}
