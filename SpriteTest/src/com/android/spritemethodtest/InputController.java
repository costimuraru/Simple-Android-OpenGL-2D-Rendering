package com.android.spritemethodtest;

import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.MotionEvent;

public class InputController {
	protected static final int HANDLER_INPUT_EVENT = 0;
	private GLScene scene;
	Handler mHandler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case InputController.HANDLER_INPUT_EVENT:
				handleInputEvent((InputEvent) msg.obj);
				break;
			}
			return true;
		}
	});

	public InputController(GLScene scene) {
		this.scene = scene;
	}

	public Handler getHandler() {
		return mHandler;
	}

	public void handleInputEvent(InputEvent inputEvent) {
		switch (inputEvent.getType()) {
		case InputEvent.WINDOW_CHANGED:
			scene.windowSizeChanged((int) inputEvent.getX(), (int) inputEvent.getY());
			// setBackground(R.drawable.background);
			break;
		case InputEvent.TOUCH_EVENT:
			MotionEvent event = inputEvent.getEvent();
			//TODO use event
			break;
		}
	}
}
