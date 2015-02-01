package com.travelcheck.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.travelcheck.library.util.Constants.DATABASE_VARIABLES;
import com.travelcheck.model.FavouritesModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper implements DATABASE_VARIABLES {

	private static String DB_PATH = "/data/data/com.travelcheck/databases/";
	private static String DB_NAME = "travelcheckdb";
	private final String SELECT_STATEMENT = "SELECT * FROM ";
	public SQLiteDatabase myDataBase;
	private Long mResponseId;
	private final Context myContext;
	static final String TABLE_NAME = "favourites";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		if (!dbExist) {
			// this.getReadableDatabase();
			this.getWritableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public List<FavouritesModel> retrieveFavouritesList() {

		List<FavouritesModel> l_list = new ArrayList<FavouritesModel>();
		Cursor c = myDataBase.rawQuery("select * from " + TABLE_NAME, null);
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {
			FavouritesModel l_model	=	new FavouritesModel();
			String l_name	=	c.getString(c.getColumnIndex(CONTACTS));
			String l_type	=	c.getString(c.getColumnIndex(TYPE));
			l_model.setProperties(l_name,l_type);
			l_list.add(l_model);
			c.moveToNext();
		}
		c.close();
		return l_list;

	}

	public long saveFavourites(String p_contact,String p_type) {

		ContentValues l_cv = new ContentValues();
		l_cv.put(CONTACTS, p_contact);
		l_cv.put(TYPE, p_type);
		mResponseId = myDataBase.insert(TABLE_NAME, null, l_cv);

		return mResponseId;

	}

	public int deleteFavouritesList(String p_contact) {
		int l_res = myDataBase.delete(TABLE_NAME, CONTACTS + "=?",
				new String[] { p_contact });
		return l_res;

	}
}