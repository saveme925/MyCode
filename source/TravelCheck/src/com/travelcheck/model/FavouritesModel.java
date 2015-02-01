package com.travelcheck.model;

public class FavouritesModel {
	
	private String mName;
	private String mType;
	
	public void setProperties(String p_name,String p_type) {
		
		mName		=	p_name;
		mType		=	p_type;
	}
	

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}


	public String getmType() {
		return mType;
	}


	public void setmType(String mType) {
		this.mType = mType;
	}

}
