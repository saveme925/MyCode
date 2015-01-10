package com.travelcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	public Button CameraBtn;
	String extStorageDirectory;
	String Image;
	private static final int CAMERA_REQUEST = 1888;
	LocationManager service;
	Location location;
	boolean enabled;
	Bitmap photo;
	double latitude,longitude;
	public TextView BottomTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		//service = (LocationManager) getSystemService(LOCATION_SERVICE);
		 
		//location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//latitude=location.getLatitude();
		//longitude=location.getLongitude();
		enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER); 
		if(!enabled)
		{
			buildAlertMessageNoGps();
		}
		init();
	}

	// Initialize widgets
	public void init() {
		CameraBtn = (Button) findViewById(R.id.CameraBtn);
		CameraBtn.setOnClickListener(this);
		BottomTxt=(TextView) findViewById(R.id.LocationTxt);

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
		new GetAddressTask(getApplicationContext());
	}

	// store image in sd-card
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap) data.getExtras().get("data");
			OutputStream outStream = null;
			File file = new File(extStorageDirectory, "Travel_check");
			try {

				buildAlertMessage_ShareImage();
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
	//Alert dialog to share Image
	private void buildAlertMessage_ShareImage() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to share image?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
				if(!enabled)
				{buildAlertMessageNoGps();
				}
				else
				{
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("image/jpg");
				i.putExtra(Intent.EXTRA_EMAIL, new String[] {"someone@gmail.com"} );
				i.putExtra(Intent.EXTRA_SUBJECT, "Please find attached image");
				i.putExtra(Intent.EXTRA_TEXT, "Latitude is latitude,Longitude is longitude");
				i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/myImage.gif"));
				startActivity(i);
				}
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

	//Alert dialog when mobile GPS is disabled
	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("GPS seems to be disabled. Do you want to enable now?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);

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
	
	 private class GetAddressTask extends
     AsyncTask<Location, Void, String> {
 Context mContext;
 public GetAddressTask(Context context) {
     super();
     mContext = context;
 }

 /**
  * Get a Geocoder instance, get the latitude and longitude
  * look up the address, and return it
  *
  * @params params One or more Location objects
  * @return A string containing the address of the current
  * location, or an empty string if no address can be found,
  * or an error message
  */
 @Override
 protected String doInBackground(Location... params) {
     Geocoder geocoder =
             new Geocoder(mContext, Locale.getDefault());
     // Get the current location from the input parameter list
     Location loc = params[0];
     // Create a list to contain the result address
     List<Address> addresses = null;
     try {
         /*
          * Return 1 address.
          */
         addresses = geocoder.getFromLocation(loc.getLatitude(),
                 loc.getLongitude(), 1);
     } catch (IOException e1) {
     Log.e("LocationSampleActivity",
             "IO Exception in getFromLocation()");
     e1.printStackTrace();
     return ("IO Exception trying to get address");
     } catch (IllegalArgumentException e2) {
     // Error message to post in the log
     String errorString = "Illegal arguments " +
             Double.toString(loc.getLatitude()) +
             " , " +
             Double.toString(loc.getLongitude()) +
             " passed to address service";
     Log.e("LocationSampleActivity", errorString);
     e2.printStackTrace();
     return errorString;
     }
     // If the reverse geocode returned an address
     if (addresses != null && addresses.size() > 0) {
         // Get the first address
         Address address = addresses.get(0);
         /*
          * Format the first line of address (if available),
          * city, and country name.
          */
         String addressText = String.format(
                 "%s, %s, %s",
                 // If there's a street address, add it
                 address.getMaxAddressLineIndex() > 0 ?
                         address.getAddressLine(0) : "",
                 // Locality is usually a city
                 address.getLocality(),
                 // The country of the address
                 address.getCountryName());
         // Return the text
         return addressText;
     } else {
         return "No address found";
     }
     
 }
 protected void onPostExecute(String address) {
 
     //set Text Address in textview here
	 BottomTxt.setText(address);
 }

}
}
