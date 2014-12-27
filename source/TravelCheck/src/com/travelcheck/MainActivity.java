package com.travelcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	public Button CameraBtn;
	String extStorageDirectory;
	String Image;
	private static final int CAMERA_REQUEST = 1888;
	Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		init();
	}

	// Initialize widgets
	public void init() {
		CameraBtn = (Button) findViewById(R.id.CameraBtn);
		CameraBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.CameraBtn:
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);

			break;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	// store image in sd-card
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap) data.getExtras().get("data");
			OutputStream outStream = null;
			File file = new File(extStorageDirectory, "Travel_check");
			try {

				buildAlertMessageNoGps();
				outStream = new FileOutputStream(file);
				photo.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				outStream.flush();
				outStream.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}
	//Alert dialog when mobile GPS is disabled
		private void buildAlertMessageNoGps() {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do you want to share image?")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("image/jpg");
					i.putExtra(Intent.EXTRA_EMAIL, new String[] {"someone@gmail.com"} );
					i.putExtra(Intent.EXTRA_SUBJECT, "Please find attached image");
					i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/myImage.gif"));
					startActivity(i);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
					dialog.cancel();
				}
			});
			final AlertDialog alert = builder.create();
			alert.show();
		}
}
