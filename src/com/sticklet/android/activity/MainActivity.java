package com.sticklet.android.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sticklet.android.R;
import com.sticklet.android.activity.base.BaseActivity;
import com.sticklet.android.constants.StickletAndroidConstants;
import com.sticklet.android.database.StickletContract.Notebook;

public class MainActivity extends BaseActivity {
	
	Account mAccount;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//mAccount = CreateSyncAccount(this);
	}
	
	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);

		EditText text = (EditText) findViewById(R.id.edit_message);
		String message = text.getText().toString();
		intent.putExtra(StickletAndroidConstants.EXTRA_MESSAGE, message);
		
		startActivity(intent);
	}
	
	public static Account CreateSyncAccount(Context context) {
		Account newAccount = new Account(StickletAndroidConstants.ACCOUNT, StickletAndroidConstants.ACCOUNT_TYPE);
		
		AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
		
		if (accountManager.addAccountExplicitly(newAccount, null, null)) {
			return newAccount;
		}
		return null;
	}
}
