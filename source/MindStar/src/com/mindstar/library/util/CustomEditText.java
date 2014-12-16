package com.mindstar.library.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class CustomEditText extends EditText {

	public CustomEditText(Context context, AttributeSet attrSet) {
		super(context, attrSet);
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent keyEvent) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			clearFocus();
		}
		return super.onKeyPreIme(keyCode, keyEvent);
	}
}
