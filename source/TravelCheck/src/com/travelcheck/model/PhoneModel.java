package com.travelcheck.model;

import com.travelcheck.util.Util;



public class PhoneModel {
	
	private String mName;
	private boolean mCheck;
	
	
	public void setProperties(String p_name) {
		
		mName = p_name;
		if (Util.l_contact_list != null && Util.l_contact_list.size() > 0) {
			for (int i = 0; i < Util.l_contact_list.size(); i++) {
				if (mName.equals(Util.l_contact_list.get(i).getmName())) {
					setmCheck(true);
				}

			}
		} else
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
