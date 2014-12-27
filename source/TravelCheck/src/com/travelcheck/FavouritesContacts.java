package com.travelcheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.travelcheck.adapter.EmailAdapter;
import com.travelcheck.adapter.FavouriteListAdapter;
import com.travelcheck.adapter.PhoneAdapter;
import com.travelcheck.db.DBHelper;
import com.travelcheck.model.EmailModel;
import com.travelcheck.model.FavouritesModel;
import com.travelcheck.model.PhoneModel;
import com.travelcheck.util.Util;

public class FavouritesContacts extends Activity {

	/**
	 * Global variables
	 */

	private Button mPickContacts;
	private TextView mAddEmail;
	private TextView mAddPhoneNumber;
	private ProgressDialog mProgressDialog = null;
	private Dialog builder;
	private ListView mEmailList;
	private ListView mPhoneList;
	private List<EmailModel> l_emailAddress;
	private List<PhoneModel> l_phoneNumber;
	private DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);

		findViewById();
	}

	/**
	 * Method to find id's for all user input
	 */

	private void findViewById() {

		mPickContacts = (Button) findViewById(R.id.btn_pickcontacts);
		mPickContacts.setOnClickListener(pickContactsClickListener);

	}

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
	private TextView mEmailListBottom;
	private TextView mPhoneListBottom;

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
		mEmailList = (ListView) view.findViewById(R.id.favourite_list);
		TextView mAddFav = (TextView) view.findViewById(R.id.add_button);
		mAddFav.setOnClickListener(makeFavClickListener);
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

}
