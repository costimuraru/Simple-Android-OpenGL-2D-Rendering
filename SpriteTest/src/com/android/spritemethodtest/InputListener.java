package com.android.spritemethodtest;

import android.os.Handler;
import android.view.MotionEvent;

public class InputListener {
	private Handler handler;

	public InputListener(InputController inputController) {
		this.handler = inputController.getHandler();
	}

	public void onWindowResize(int w, int h) {
		Util.sendHandlerMessage(handler, InputController.HANDLER_INPUT_EVENT, new InputEvent(InputEvent.WINDOW_CHANGED, w, h));
	}

	public void onTouchEvent(MotionEvent event) {
		Util.sendHandlerMessage(handler, InputController.HANDLER_INPUT_EVENT, new InputEvent(InputEvent.TOUCH_EVENT, event));
	}
}
