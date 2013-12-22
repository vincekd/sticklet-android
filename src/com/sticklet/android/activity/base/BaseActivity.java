package com.sticklet.android.activity.base;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sticklet.android.R;

public abstract class BaseActivity extends Activity {

	protected Logger logger =  Logger.getLogger(this.getClass().getName());
	
	protected Intent intent;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		intent = getIntent();
		
		setContentView(getContentView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	protected int getContentView() {
		String name = getUnderscoredName();
		return getRValue(R.layout.class, name);
	}

	protected int getMenu() {
		String name = getUnderscoredName().replace("_activity$", "");
		//return getRValue(R.menu.class, name);
		return getRValue(R.menu.class, name);
	}
	
	private int getRValue(Class<?> clazz, String name) {
		int val = -1;
		try {
			//R.layout.class.
			Field field = clazz.getField(name);
			field.setAccessible(true);
			val = (int)field.getInt(null);
			//field.setAccessible(false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return val;
	}

	private String getUnderscoredName() {
		String name = this.getClass().getSimpleName();
		name = name.substring(0, 1).concat(name.substring(1).replaceAll("([A-Z])", "_$1")).toLowerCase();
		return name;
	}
}