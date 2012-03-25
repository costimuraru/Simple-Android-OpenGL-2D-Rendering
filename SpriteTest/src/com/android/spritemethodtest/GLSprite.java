/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.spritemethodtest;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;

/**
 * This is the OpenGL ES version of a sprite. It is more complicated than the
 * CanvasSprite class because it can be used in more than one way. This class
 * can draw using a grid of verts, a grid of verts stored in VBO objects, or
 * using the DrawTexture extension.
 */
public class GLSprite extends Renderable {
	// The OpenGL ES texture handle to draw.
	private int mTextureName;
	// The id of the original resource that mTextureName is based on.
	private int mResourceId;
	// If drawing with verts or VBO verts, the grid object defining those verts.
	private Grid mGrid;
	public boolean dirty;
	private Bitmap bitmap;
	private int bitmapId;

	public GLSprite(int resourceId) {
		super();
		mResourceId = resourceId;
	}

	public GLSprite(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public GLSprite(Bitmap bitmap, int bitmapId) {
		super();
		this.bitmap = bitmap;
		this.bitmapId = bitmapId;
	}

	public int getBitmapId() {
		return bitmapId;
	}

	public void setTextureName(int name) {
		mTextureName = name;
	}

	public int getTextureName() {
		return mTextureName;
	}

	public void setResourceId(int id) {
		mResourceId = id;
	}

	public int getResourceId() {
		return mResourceId;
	}

	public void setGrid(Grid grid) {
		mGrid = grid;
	}

	public Grid getGrid() {
		return mGrid;
	}

	public void draw(GL10 gl) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureName);

		if (mGrid == null) {
			// Draw using the DrawTexture extension.
			((GL11Ext) gl).glDrawTexfOES(x, y, z, width, height);
		} else {
			// Draw using verts or VBO verts.
			gl.glPushMatrix();
			gl.glLoadIdentity();
			gl.glTranslatef(x, y, z);

			mGrid.draw(gl, true, false);

			gl.glPopMatrix();
		}
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public GLSprite clone() {
		GLSprite cloned = new GLSprite(bitmap);
		cloned.bitmapId = bitmapId;
		cloned.dirty = dirty;
		cloned.width = width;
		cloned.height = height;
		cloned.mGrid = mGrid;
		cloned.mResourceId = mResourceId;
		cloned.mTextureName = mTextureName;
		cloned.x = x;
		cloned.y = y;
		cloned.z = z;
		cloned.velocityX = velocityX;
		cloned.velocityY = velocityY;
		cloned.velocityZ = velocityZ;
		return cloned;
	}
}
