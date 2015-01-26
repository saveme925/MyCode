package com.travelcheck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.travelcheck.adapter.EmailAdapter;
import com.travelcheck.adapter.FavouriteListAdapter;
import com.travelcheck.adapter.PhoneAdapter;
import com.travelcheck.app.AppController;
import com.travelcheck.db.DBHelper;
import com.travelcheck.library.util.Constants;
import com.travelcheck.library.util.Constants.ACTIVITY_STATES;
import com.travelcheck.model.EmailModel;
import com.travelcheck.model.FavouritesModel;
import com.travelcheck.model.PhoneModel;
import com.travelcheck.util.AppLocationService;
import com.travelcheck.util.BottomBarDrawerLayout;
import com.travelcheck.util.BottomBarDrawerLayout.DrawerListener;
import com.travelcheck.util.ImageLoadingUtils;
import com.travelcheck.util.LocationAddress;
import com.travelcheck.util.Util;

public class FavouritesContacts extends BaseActivity implements OnTouchListener {
	/**
	 * Global variables
	 */
	private TextView mPickContacts;
	private TextView mAddEmail;
	private TextView mAddPhoneNumber;
	private ProgressDialog mProgressDialog = null;
	private Dialog builder;
	private ListView mEmailList;
	private ListView mPhoneList;
	private List<EmailModel> l_emailAddress;
	private List<PhoneModel> l_phoneNumber;
	private TextView mTakePicture;
	private GoogleMap googleMap;
	private MarkerOptions mOptions;
	String extStorageDirectory;
	String Image;
	Bitmap photo;
	private DBHelper dbh;
	private TextView mCallFav;
	private TextView mFollowMe;
	// LocationManager mLocationManager;
	public TextView currentLocTxt;
	private Location location;
	private Double mLatitude, mLongitude;
	String CityName = "";
	String StateName = "";
	String CountryName = "";
	boolean gps_enabled;
	private ImageLoadingUtils utils;
	private AppLocationService mAppLocationService;
	private TextView mEmailListBottom;
	private TextView mPhoneListBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater l_inflater = LayoutInflater
				.from(FavouritesContacts.this);

		View l_view = l_inflater.inflate(R.layout.new_dashboard, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		utils = new ImageLoadingUtils(this);
		l_emailAddress = new ArrayList<EmailModel>();
		l_phoneNumber = new ArrayList<PhoneModel>();
		mAppLocationService = new AppLocationService(FavouritesContacts.this);
		findViewById(l_view);
		getLatLong();
		mDrawerList.setOnItemClickListener(listClickListener);

	}

	private void getLatLong() {

		try {

			Location gpsLocation = mAppLocationService
					.getLocation(LocationManager.GPS_PROVIDER);
			if (gpsLocation != null) {
				mLatitude = gpsLocation.getLatitude();
				mLongitude = gpsLocation.getLongitude();
				LocationAddress.getAddressFromLocation(mLatitude, mLongitude,
						getApplicationContext(), new GeocoderHandler());

			} else {
				buildAlertMessageNoGps();
			}
		} catch (Exception e) {
			Util.toastMessage(FavouritesContacts.this,
					"No location found this time!");

		}
	}

	private OnItemClickListener listClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			openActivity(position);

		}
	};
	private LinearLayout mBottomLayout;
	private BottomBarDrawerLayout mSlideInLayout;

	protected void openActivity(int position) {

		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; // Setting currently selected position
											// in this field so that it will be
											// available in our child
											// activities.

		switch (position) {
		case 2:

			AppController.handleEventForResult(FavouritesContacts.this,
					ACTIVITY_STATES.SETTINGS,
					Constants.INTENT_CONSTANTS.REQUEST_SETTINGS, "");

			break;
		}

	}

	private void displaySharedPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(FavouritesContacts.this);

		String l_emr_message = prefs.getString("prefEmergencyMessage",
				"I\'m in an emergency.");
		String l_emr_contact = prefs.getString("prefEmergencyNumber", "");
		int l_emr_time = prefs.getInt("timeFrequency", 5);

	}

	/**
	 * Method to find id's for all user input
	 * 
	 * @param p_view
	 */

	private void findViewById(View p_view) {
		mSlideInLayout = (BottomBarDrawerLayout) findViewById(R.id.drawerLayout);
		mSlideInLayout.setDrawerListener(sliderDrawerListsner);
		mBottomLayout = (LinearLayout) p_view
				.findViewById(R.id.bottom_bar_linearlayout);

		mPickContacts = (TextView) p_view.findViewById(R.id.txt_pickcontacts);
		mPickContacts.setOnTouchListener(this);
		mPickContacts.setOnClickListener(pickContactsClickListener);

		mTakePicture = (TextView) p_view.findViewById(R.id.txt_takepicture);
		mTakePicture.setOnTouchListener(this);
		mTakePicture.setOnClickListener(cameraClickListener);

		mCallFav = (TextView) p_view.findViewById(R.id.txt_callme);
		mCallFav.setOnTouchListener(this);
		mCallFav.setOnClickListener(callFavouritesClickListener);

		mFollowMe = (TextView) p_view.findViewById(R.id.txt_followme);
		mFollowMe.setOnTouchListener(this);
		mFollowMe.setOnClickListener(followMeClickListener);

		currentLocTxt = (TextView) p_view
				.findViewById(R.id.txt_currentlocation);

	}

	private DrawerListener sliderDrawerListsner = new DrawerListener() {

		@Override
		public void onDrawerStateChanged(int newState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDrawerOpened(View drawerView) {

		}

		@Override
		public void onDrawerClosed(View drawerView) {

		}
	};

	/**
	 * Method for gps alert
	 */
	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(
									@SuppressWarnings("unused") final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							@SuppressWarnings("unused") final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Click listener for call favorites
	 */

	private OnClickListener callFavouritesClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

		}
	};

	/**
	 * Click listener for follow me
	 */

	private OnClickListener followMeClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(FavouritesContacts.this, Follow_me.class);
			startActivity(i);
		}
	};

	/**
	 * Click listener for current location
	 */

	/**
	 * Click listener for show all emails
	 */

	private OnClickListener addEmailClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			mAddPhoneNumber.setBackgroundColor(Color.parseColor("#d3d6db"));
			mAddEmail.setBackgroundColor(Color.WHITE);
			mPhoneList.setVisibility(View.GONE);
			mEmailList.setVisibility(View.VISIBLE);
			mEmailListBottom.setVisibility(View.VISIBLE);
			mPhoneListBottom.setVisibility(View.INVISIBLE);

		}
	};
	/**
	 * Click listener for camera
	 */

	private OnClickListener cameraClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent,
					Constants.INTENT_CONSTANTS.REQUEST_CAMERA);

		}
	};

	/**
	 * Click listener for show all phone numbers
	 */

	private OnClickListener addPhoneNumberClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			mAddEmail.setBackgroundColor(Color.parseColor("#d3d6db"));
			mAddPhoneNumber.setBackgroundColor(Color.WHITE);
			mPhoneList.setVisibility(View.VISIBLE);
			mEmailList.setVisibility(View.GONE);
			mEmailListBottom.setVisibility(View.INVISIBLE);
			mPhoneListBottom.setVisibility(View.VISIBLE);

		}
	};

	private OnClickListener pickContactsClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			AsyncTask<Void, Integer, Void> sublitTask = new AsyncTask<Void, Integer, Void>() {

				/**
				 * Method to show progress dialog
				 * 
				 * @param messageToShow
				 *            : Text you want to show on the dialog
				 * @param context
				 *            : Context of current class
				 */
				public void showProgressDialog(String messageToShow,
						Context context) {
					// Progress dialog initialization
					if (mProgressDialog == null) {
						// mProgressDialog = new ProgressDialog(context);
						mProgressDialog = new ProgressDialog(context);
						mProgressDialog.setCancelable(false);
						mProgressDialog.setIndeterminate(false);
					}
					mProgressDialog.setMessage(messageToShow);
					mProgressDialog.show();
				}

				/**
				 * Method to cancel progress dialog
				 */
				public void cancelProgrssDialog() {
					if (mProgressDialog != null) {
						mProgressDialog.cancel();
					}
					mProgressDialog = null;
				}

				@Override
				protected void onPreExecute() {
					Util.l_contact_list = new ArrayList<FavouritesModel>();
					l_emailAddress = new ArrayList<EmailModel>();
					l_phoneNumber = new ArrayList<PhoneModel>();
					showProgressDialog(getString(R.string.waiting_string),
							FavouritesContacts.this);
					super.onPreExecute();
				}

				@Override
				protected Void doInBackground(Void... params) {
					dbh = new DBHelper(FavouritesContacts.this);
					try {
						dbh.createDataBase();

					} catch (IOException ioe) {
						throw new Error("Unable to create database");
					}
					dbh.openDataBase();
					Util.l_contact_list = dbh.retrieveFavouritesList();
					readContacts(FavouritesContacts.this);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					cancelProgrssDialog();
					if (Util.l_contact_list.size() > 0) {
						ShowFavContactList();
					} else {
						showUserEmails();
					}

					super.onPostExecute(result);
				}

			}.execute();

		}
	};
	
	/**
	 * Method to pick all emails and phone number from device
	 * 
	 * @param p_context
	 *            Context of an activity
	 * @param p_contact_list
	 */

	public void readContacts(Context p_context) {
		ContentResolver cr = p_context.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));

				// get the phone number
				Cursor pCur = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = ?", new String[] { id }, null);
				while (pCur.moveToNext()) {
					String phone = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					PhoneModel l_model = new PhoneModel();
					l_model.setProperties(phone);
					l_phoneNumber.add(l_model);

				}
				pCur.close();

				// get email

				Cursor emailCur = cr.query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
								+ " = ?", new String[] { id }, null);
				while (emailCur.moveToNext()) {
					String email = emailCur
							.getString(emailCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					EmailModel l_model = new EmailModel();
					l_model.setProperties(email);
					l_emailAddress.add(l_model);

				}
				emailCur.close();

			}
		}
	}

	private void ShowFavContactList() {

		builder = new Dialog(FavouritesContacts.this);
		builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
		builder.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		final LayoutInflater inflator = LayoutInflater
				.from(FavouritesContacts.this);
		View view = inflator.inflate(R.layout.favourite_list, null);
		
		TextView l_header	=	(TextView) view.findViewById(R.id.heading);
		l_header.setText("Favourites");
		LinearLayout l_slider	=	(LinearLayout) view.findViewById(R.id.slider_linearlayout);
		l_slider.setVisibility(View.GONE);
		LinearLayout mAddFavouites	=	(LinearLayout) view.findViewById(R.id.add_linearlayout);
		mAddFavouites.setVisibility(View.VISIBLE);
		mEmailList = (ListView) view.findViewById(R.id.favourite_list);
		mAddFavouites.setOnClickListener(makeFavClickListener);
		builder.setContentView(view);
		FavouriteListAdapter l_fav_adapter = new FavouriteListAdapter(
				FavouritesContacts.this, Util.l_contact_list);
		mEmailList.setAdapter(l_fav_adapter);

		setLayoutOfBuilder();

		builder.show();

	}

	private OnClickListener makeFavClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			builder.cancel();
			showUserEmails();

		}
	};

	public void setLayoutOfBuilder() {

		Window window = builder.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();

		wlp.gravity = Gravity.CENTER;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
		window.setAttributes(wlp);
		builder.getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.MATCH_PARENT);

	}

	/**
	 * Method to show all emails and phone number to UI.
	 */

	private void showUserEmails() {

		builder = new Dialog(FavouritesContacts.this);
		builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
		builder.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		final LayoutInflater inflator = LayoutInflater
				.from(FavouritesContacts.this);
		View view = inflator.inflate(R.layout.email_list, null);
		TextView l_header	=	(TextView) view.findViewById(R.id.heading);
		l_header.setText("My Contacts");
		LinearLayout l_slider	=	(LinearLayout) view.findViewById(R.id.slider_linearlayout);
		l_slider.setVisibility(View.GONE);
		
		mEmailList = (ListView) view.findViewById(R.id.email_list);
		mPhoneList = (ListView) view.findViewById(R.id.phone_list);
		mEmailListBottom = (TextView) view
				.findViewById(R.id.addemail_bottom_textview);
		mEmailListBottom.setVisibility(View.VISIBLE);
		mPhoneListBottom = (TextView) view
				.findViewById(R.id.addphonenumber_bottom_textview);
		mPhoneListBottom.setVisibility(View.INVISIBLE);
		mAddEmail = (TextView) view.findViewById(R.id.addemail_textview);
		mAddEmail.setOnClickListener(addEmailClickListener);
		mAddPhoneNumber = (TextView) view
				.findViewById(R.id.addphonenumber_textview);
		mAddPhoneNumber.setOnClickListener(addPhoneNumberClickListener);
		TextView l_cancel = (TextView) view
				.findViewById(R.id.cancelemail_button);
		l_cancel.setOnClickListener(cancelDialogClickListener);
		builder.setContentView(view);
		builder.setOnDismissListener(dialogDismissListener);
		EmailAdapter l_email_adapter = new EmailAdapter(
				FavouritesContacts.this, l_emailAddress, dbh);
		PhoneAdapter l_phone_adapter = new PhoneAdapter(
				FavouritesContacts.this, l_phoneNumber, dbh);

		mEmailList.setAdapter(l_email_adapter);
		mPhoneList.setAdapter(l_phone_adapter);
		setLayoutOfBuilder();
		builder.show();

	}
	
	
	

	/**
	 * Click listener for dismissing dialog
	 */

	private OnDismissListener dialogDismissListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			try {
				dbh.close();
			} catch (Exception e) {
			}
		}
	};

	/**
	 * Listener for cancel dialog
	 */

	private OnClickListener cancelDialogClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			builder.cancel();

		}
	};

	/**
	 * Return call backs from activity method startActivityForResult
	 */

	// store image in sd-card
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Constants.INTENT_CONSTANTS.REQUEST_CAMERA) {

			if (resultCode == RESULT_OK) {

				try {
					String l_path = data.getDataString();
					String filePath = Util.compressImage(l_path,
							FavouritesContacts.this, utils);
					File l_file = new File(filePath);
					String selectedImage = Uri.fromFile(l_file).toString();
					ShareImage(selectedImage);
					// TODO
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (resultCode == RESULT_CANCELED) {

				Util.toastMessage(FavouritesContacts.this, "Cancel");

			}

		} else if (requestCode == Constants.INTENT_CONSTANTS.REQUEST_SETTINGS) {

			try {
				displaySharedPreferences();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void ShareImage(final String p_path) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to share image?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								Intent sharingIntent = new Intent(
										Intent.ACTION_SEND);
								Uri pathOfUri = Uri.parse(p_path);
								sharingIntent.setType("image/png");
								sharingIntent.putExtra(Intent.EXTRA_STREAM,
										pathOfUri);
								startActivity(Intent.createChooser(
										sharingIntent, "Share image using"));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		builder.setTitle("Share Image");
		alert.show();
	}

	/**
	 * Touch listener for dash board options
	 */

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int eventaction = event.getAction();
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:

			makeHighlightView(v);

			break;

		case MotionEvent.ACTION_MOVE:

			makeHighlightView(v);
			break;

		case MotionEvent.ACTION_UP:
			makeUnHighlightView(v);

			break;
		}

		return false;
	}

	/**
	 * Method to Unhighlight selected option from dash board
	 * 
	 * @param v
	 *            selected text view
	 */

	private void makeUnHighlightView(View v) {

		switch (v.getId()) {

		case R.id.txt_pickcontacts:
			mPickContacts.setTextColor(Color.parseColor("#0b1a12"));
			break;

		case R.id.txt_takepicture:
			mTakePicture.setTextColor(Color.parseColor("#0b1a12"));
			break;

		case R.id.txt_callme:
			mCallFav.setTextColor(Color.parseColor("#0b1a12"));
			break;

		case R.id.txt_followme:
			mFollowMe.setTextColor(Color.parseColor("#0b1a12"));

			break;

		case R.id.txt_currentlocation:
			currentLocTxt.setTextColor(Color.parseColor("#0b1a12"));
			break;

		}

	}

	/**
	 * Method to highlight selected option from dash board
	 * 
	 * @param v
	 *            selected text view
	 */

	private void makeHighlightView(View v) {

		switch (v.getId()) {

		case R.id.txt_pickcontacts:
			mPickContacts.setTextColor(Color.WHITE);
			break;

		case R.id.txt_takepicture:
			mTakePicture.setTextColor(Color.WHITE);
			break;

		case R.id.txt_callme:
			mCallFav.setTextColor(Color.WHITE);
			break;

		case R.id.txt_followme:
			mFollowMe.setTextColor(Color.WHITE);
			break;

		case R.id.txt_currentlocation:
			currentLocTxt.setTextColor(Color.WHITE);
			break;

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	/**
	 * function to load map. If map is not created it will create it for you
	 * 
	 * @param p_locationAddress
	 * */
	private void initilizeMap(String p_locationAddress) {

		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				// googleMap = ((SupportMapFragment) getSupportFragmentManager()
				// .findFragmentById(R.id.map)).getMap();

				mOptions = new MarkerOptions().position(
						new LatLng(mLatitude, mLongitude)).title(
						p_locationAddress);
				mOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				googleMap.addMarker(mOptions);

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(mLatitude, mLongitude)).zoom(12)
						.build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));

				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		} catch (Exception e) {

		}
	}


	private class GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
			case 1:
				Bundle bundle = message.getData();
				locationAddress = bundle.getString("address");
				break;
			default:
				locationAddress = null;
			}
			initilizeMap(locationAddress);
		}
	}

}
