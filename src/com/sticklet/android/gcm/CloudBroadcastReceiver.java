package com.sticklet.android.gcm;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sticklet.android.constants.StickletAndroidConstants;

public class CloudBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		
		String messageType = gcm.getMessageType(intent);
		
		//ContentResolver.requestSync(StickletAndroidConstants.ACCOUNT, StickletAndroidConstants.AUTHORITY, null);
	}
}