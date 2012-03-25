package com.android.spritemethodtest;

import com.android.spritemethodtest.GLSurfaceView.Renderer.CustomColor;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceView;

public interface Scene {
	public SurfaceView getSurfaceView();

	/**
	 * Sets a bitmap as the background
	 * 
	 * @param bitmap
	 */
	public void setBitmapBackground(Bitmap bitmap);

	/**
	 * Sets a nine patch resource as the background. It will stretch to fill the
	 * entire screen. When screen orientation will change, this background will
	 * also change to fill the new screen.
	 * 
	 * @param pictureId
	 */
	public void setNinePatchBackground(int pictureId);

	/**
	 * Sets the background color of the scene. No bitmap will be used any more
	 * for the background.
	 * 
	 * @param color
	 */
	public void setBackgroundColor(CustomColor color);

	/**
	 * Adds a sprite to the scene.
	 * 
	 * @param sprite
	 * @param addToMover
	 *            Whether to pass this to the Mover thread
	 */
	public void addSprite(GLSprite sprite, boolean addToMover);

	/**
	 * Sets the mover thread. This will handle the sprites' movement.
	 * 
	 * @param mover
	 */
	public void setMover(Mover mover);

	/**
	 * Creates a sprite corresponding to the given resource id
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public Renderable createSprite(Context context, int resourceId);

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
	public Renderable createSprite(Bitmap bitmap, int bitmapId);

}
