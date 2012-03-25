package com.android.spritemethodtest;

import android.view.MotionEvent;

public class InputEvent {
	public static final int WINDOW_CHANGED = 0, TOUCH_EVENT = 1;
	private int type;
	private float x;
	private float y;
	private MotionEvent event;

	public InputEvent(int type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public InputEvent(int type, MotionEvent event) {
		this.type = type;
		this.event = event;
	}

	public MotionEvent getEvent() {
		return event;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
