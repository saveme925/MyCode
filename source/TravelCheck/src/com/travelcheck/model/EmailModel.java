package com.travelcheck.model;

import android.R.bool;


public class EmailModel {
	
	private String mName;
	private boolean mCheck;
	
	
	public void setProperties(String p_name) {
		
		mName	=	p_name;
		setmCheck(false);
		
	}


	public String getmName() {
		return mName;
	}


	public void setmName(String mName) {
		this.mName = mName;
	}


	public boolean getmCheck() {
		return mCheck;
	}


	public void setmCheck(Boolean mCheck) {
		this.mCheck = mCheck;
	}

}
