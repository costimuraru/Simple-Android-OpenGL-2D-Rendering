package com.android.spritemethodtest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.NinePatchDrawable;

import com.android.spritemethodtest.GLSurfaceView.Renderer.CustomColor;

public class GLScene implements Scene {
	private GLSurfaceView mGLSurfaceView;
	private int bgNinePatchResourceId;
	private ArrayList<GLSprite> spriteList;
	private ArrayList<Renderable> movementList;
	private SimpleGLRenderer spriteRenderer;
	private Mover mover;
	private Context context;
	private InputController inputController;

	public GLScene(Context context) {
		this.context = context;
		this.inputController = new InputController(this);
		mGLSurfaceView = new GLSurfaceView(context, new InputListener(inputController));
		spriteRenderer = new SimpleGLRenderer(context);

		// Clear out any old profile results.
		ProfileRecorder.sSingleton.resetAll();

		// Allocate space for the robot sprites + one background sprite.
		spriteList = new ArrayList<GLSprite>();
		spriteList.add(null); // the background

		// This list of things to move. It points to the same content as the
		// sprite list except for the background.
		movementList = new ArrayList<Renderable>();

		spriteRenderer.setSprites(spriteList);
		// spriteRenderer.setVertMode(useVerts, useHardwareBuffers);

		mGLSurfaceView.setRenderer(spriteRenderer);
	}

	/**
	 * Creates a sprite corresponding to the given resource id
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public GLSprite createSprite(Context context, int resourceId) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
		GLSprite sprite = new GLSprite(getBitmapForTexture(bitmap), resourceId);

		sprite.width = sprite.getBitmap().getWidth();
		sprite.height = sprite.getBitmap().getHeight();
		sprite.dirty = true;

		return sprite;
	}

	/**
	 * Returns a sprite corresponding to a given bitmap
	 * 
	 * @param bitmap
	 *            The bitmap used for the sprite
	 * @param bitmapId
	 *            The bitmap id is used to re-use textures (use same ID for same
	 *            bitmaps content)
	 * @return
	 */
	public GLSprite createSprite(Bitmap bitmap, int bitmapId) {
		GLSprite sprite = new GLSprite(getBitmapForTexture(bitmap), bitmapId);

		sprite.width = sprite.getBitmap().getWidth();
		sprite.height = sprite.getBitmap().getHeight();

		return sprite;
	}

	/**
	 * Sets a nine patch resource as the background. It will stretch to fill the
	 * entire screen. When screen orientation will change, this background will
	 * also change to fill the new screen.
	 * 
	 * @param pictureId
	 */
	public void setNinePatchBackground(int pictureId) {
		this.bgNinePatchResourceId = pictureId;
		createBackground(mGLSurfaceView.getWidth(), mGLSurfaceView.getHeight());
	}

	/**
	 * Sets a bitmap as the background
	 * 
	 * @param bitmap
	 */
	public void setBitmapBackground(Bitmap bitmap) {
		this.bgNinePatchResourceId = 0;
		GLSprite background = new GLSprite(bitmap);
		background.width = background.getBitmap().getWidth();
		background.height = background.getBitmap().getHeight();
		background.dirty = true;
		spriteList.set(0, background);
	}

	/**
	 * Sets the background color of the scene. No bitmap will be used any more
	 * for the background.
	 * 
	 * @param color
	 */
	public void setBackgroundColor(CustomColor color) {
		bgNinePatchResourceId = 0;
		spriteList.set(0, null);
		spriteRenderer.setClearColor(color);
	}

	/**
	 * Adds a sprite to the scene.
	 * 
	 * @param sprite
	 * @param addToMover
	 *            Whether to pass this to the Mover thread
	 */
	public void addSprite(GLSprite sprite, boolean addToMover) {
		spriteList.add(sprite);
		if (addToMover)
			movementList.add(sprite);
	}

	/**
	 * Sets the mover thread. This will handle the sprites' movement.
	 * 
	 * @param mover
	 */
	public void setMover(Mover mover) {
		this.mover = mover;
		this.mover.setRenderables(movementList);

		this.mover.setViewSize(mGLSurfaceView.getWidth(), mGLSurfaceView.getHeight());
		mGLSurfaceView.setEvent(this.mover);
	}

	/**
	 * Called by the input controller when window is changed. The background
	 * will be recreated if its a nine patch.
	 * 
	 * @param width
	 * @param height
	 */
	protected void windowSizeChanged(int width, int height) {
		createBackground(width, height);
		if (mover != null)
			mover.setViewSize(width, height);
	}

	private void createBackground(int width, int height) {
		if (bgNinePatchResourceId <= 0 || width <= 0 || height <= 0)
			return;

		GLSprite background = new GLSprite(get9PatchTexture(context, bgNinePatchResourceId, width, height));
		background.width = background.getBitmap().getWidth();
		background.height = background.getBitmap().getHeight();
		background.dirty = true;
		spriteList.set(0, background);
	}

	private Bitmap get9PatchTexture(Context context, int ninePatchResourceId, int width, int height) {
		Bitmap bitmap = getBitmapFrom9Patch(context, ninePatchResourceId, width, height);
		return getBitmapForTexture(bitmap);
	}

	private Bitmap getBitmapFrom9Patch(Context context, int ninePatchResourceId, int width, int height) {
		NinePatchDrawable bg = (NinePatchDrawable) context.getResources().getDrawable(ninePatchResourceId);
		bg.setBounds(0, 0, width, height);

		Bitmap bgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bgBitmap);
		bg.draw(canvas);

		return bgBitmap;
	}

	private static Bitmap getBitmapForTexture(Bitmap bitmap) {
		int width = Util.nextPowerOf2(bitmap.getWidth());
		int height = Util.nextPowerOf2(bitmap.getHeight());

		Bitmap bmOverlay = Bitmap.createBitmap(width, height, bitmap.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		Matrix matrix = new Matrix();
		matrix.postTranslate(0, height - bitmap.getHeight());

		canvas.drawBitmap(bitmap, matrix, null);
		return bmOverlay;
	}

	public void setRenderableArray(ArrayList<Renderable> r) {
		this.movementList = r;
	}

	public ArrayList<GLSprite> getSpriteLsit() {
		return spriteList;
	}

	public ArrayList<Renderable> getMovementList() {
		return movementList;
	}

	public GLSurfaceView getSurfaceView() {
		return mGLSurfaceView;
	}

}
