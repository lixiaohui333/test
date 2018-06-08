package com.meeting.client.comm;

import android.util.Log;

import com.meeting.client.android.application.MyApplication;

public class LogHelper {

//	 public static boolean isDebug = false;
	public static boolean isDebug = Config.isLog;
	private static final String TAG = MyApplication.getInstance().getPackageName();


	public static int d(String msg) {

		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.d(TAG, msg);
		} else {
			return 0;
		}
	}

	public static int d(String subTAG, String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.d(subTAG, msg);
		} else {
			return 0;
		}
	}

	public static int i(String subTAG, String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return myi(subTAG, msg);
		} else {
			return 0;
		}
	}
	public static int myi(String subTAG, String msg) {
		int maxLength=300;

		if(msg.length()<=maxLength){
			return Log.i(subTAG, msg);
		} else {
			String leftMsg=msg.substring(maxLength+1,msg.length());
			Log.i(subTAG, msg.substring(0,maxLength));
			return myi(subTAG,leftMsg);
		}
	}



	public static int i(String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			Log.i("com.meeting",msg);
			return 0;
		} else {
			return 0;
		}
	}

	public static int e(String subTAG, String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.e(subTAG, msg);
		} else {
			return 0;
		}
	}

	public static int e(String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.e(TAG, msg);
		} else {
			return 0;
		}
	}

	public static int w(String subTAG, String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.w(subTAG, msg);
		} else {
			return 0;
		}
	}

	public static int w(String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.w(TAG, msg);
		} else {
			return 0;
		}
	}

	public static int v(String subTAG, String msg) {
		if (null == msg)
			return 0;
		if (isDebug) {
			return Log.v(subTAG, msg);
		} else {
			return 0;
		}
	}

	public static int w(String subTAG, Exception e) {
		if (null == e)
			return 0;
		if (isDebug) {
			return Log.w( subTAG, e.getMessage());
		} else {
			return 0;
		}
	}

	public static int e(String subTAG, String msg, Exception e) {
		if (null == msg && null == e)
			return 0;
		if (isDebug) {
			return Log.e(subTAG, msg + "." + e.getMessage());
		} else {
			return 0;
		}
	}

	public static int e(String subTAG, String msg, Throwable e) {
		if (null == msg && null == e)
			return 0;
		if (isDebug) {
			return Log.e(subTAG, msg, e);
		} else {
			return 0;
		}
	}

}
