package com.android.spritemethodtest;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Scene scene = createScene();
		setContentView(scene.getSurfaceView());
	}

	private Scene createScene() {
		// our OpenGL scene
		GLScene scene = new GLScene(this);

		// can set a nine patch for the background
		scene.setNinePatchBackground(R.drawable.nine_patch_bg);

		// can set a bitmap for the background
		// scene.setBitmapBackground(someBitmap);

		// can set a color for the background
		// scene.setBackgroundColor(new CustomColor(0, 0, 0, 1));

		// create some sprites
		GLSprite sprite = scene.createSprite(getApplicationContext(), R.drawable.skate1);
		// initial position
		sprite.x = 100;
		sprite.y = 200;
		GLSprite sprite2 = scene.createSprite(getApplicationContext(), R.drawable.skate2);
		// initial position
		sprite2.x = 300;
		sprite2.y = 400;
		
		GLSprite sprite3 = scene.createSprite(getApplicationContext(), R.drawable.skate3);
		// initial position
		sprite2.x = 400;
		sprite2.y = 500;

		// add sprites to the scene
		scene.addSprite(sprite, true);
		scene.addSprite(sprite2, true);
		scene.addSprite(sprite3, true);
		scene.addSprite(sprite.clone(), true);
		scene.addSprite(sprite2.clone(), true);
		scene.addSprite(sprite3.clone(), true);

		// this thread controls the sprites' positions
		scene.setMover(new Mover());
		return scene;
	}
}
