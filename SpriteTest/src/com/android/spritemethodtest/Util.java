package com.android.spritemethodtest;

import android.os.Handler;
import android.os.Message;


public class Util {

	public static int nextPowerOf2(int x) {
		if (x < 0)
	        return 0;
	    --x;
	    x |= x >> 1;
	    x |= x >> 2;
	    x |= x >> 4;
	    x |= x >> 8;
	    x |= x >> 16;
	    return x+1;
	}

	public static boolean pow2(int n) {
		if (2 * n == (n ^ n - 1) + 1)
			return true;
		return false;
	}
	
	public static void sendHandlerMessage(Handler handler, int type, Object object) {
		Message message = Message.obtain(handler, type, object);
		handler.sendMessage(message);
	}
}
