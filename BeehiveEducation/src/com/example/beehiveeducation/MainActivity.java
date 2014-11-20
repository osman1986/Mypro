package com.example.beehiveeducation;

import java.net.URLEncoder;
import java.util.Calendar;

import com.example.beehiveeducation.HttpRequest;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity
{
	Button b1, b2, b3;
	EditText et1, et2, et3;
	final String myTag = "DocsUpload";
	private static long back_pressed;

	private int mHour;
	private int mMinute;

	static final int TIME_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID1 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		Log.i(myTag, "OnCreate()");
		
		
		// for a button

		b1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);

			}
		});
		b2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID1);

			}
		});
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		// display the current date
		updateDisplay();

		b3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Thread t = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						postData();

					}
				});
				t.start();
				Toast.makeText(MainActivity.this, "successful sent", 0).show();

				et1.setText("");
				et2.setText("");
				et3.setText("");
			}

		});

	}

	public void postData()
	{

		String fullUrl = "https://docs.google.com/forms/d/1LX-_fgYe_KC2v_zOX7cywb8COiuH0xYMacLY2zZkK4A/formResponse";
		HttpRequest mReq = new HttpRequest();
		String col1 = et1.getText().toString();
		String col2 = et2.getText().toString();
		String col3 = et3.getText().toString();

		String data = "entry_1881922253=" + URLEncoder.encode(col1) + "&"
				+ "entry_582733798=" + URLEncoder.encode(col2) + "&"
				+ "entry_1420291819=" + URLEncoder.encode(col3);
		String response = mReq.sendPost(fullUrl, data);
		Log.i(myTag, response);

	}

	private void updateDisplay()
	{
		et2.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)));

	}

	private Object pad(int mMinute2)
	{

		if (mMinute2 >= 10)
			return String.valueOf(mMinute2);
		else
			return "0" + String.valueOf(mMinute2);
	}

	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mtimeSetListener1 = new TimePickerDialog.OnTimeSetListener()
	{

		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{

			mHour = hourOfDay;
			mMinute = minute;
			// updateDisplay();
			et2.setText(new StringBuilder().append(pad(mHour)).append(":")
					.append(pad(mMinute)));
		}
	};
	private TimePickerDialog.OnTimeSetListener mtimeSetListener2 = new TimePickerDialog.OnTimeSetListener()
	{

		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{

			mHour = hourOfDay;
			mMinute = minute;
			// updateDisplay();
			et3.setText(new StringBuilder().append(pad(mHour)).append(":")
					.append(pad(mMinute)));
		}
	};

	@Override
	protected Dialog onCreateDialog(int id)
	{
		Dialog d = null;
		switch (id)
		{
		case TIME_DIALOG_ID:
			TimePickerDialog td1 = new TimePickerDialog(this,
					mtimeSetListener1, mHour, mMinute, false);
			d = td1;
			break;
		case TIME_DIALOG_ID1:
			TimePickerDialog td2 = new TimePickerDialog(this,
					mtimeSetListener2, mHour, mMinute, false);
			d = td2;
			break;
		}
		return d;
	}
	public void onBackPressed()
	{
	        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
	        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
	        back_pressed = System.currentTimeMillis();
	        
	}
}
