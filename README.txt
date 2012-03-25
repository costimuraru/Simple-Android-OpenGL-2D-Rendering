SpriteMethodTest 1.1

Author: Chris Pruett
Extended by: Costi Muraru

Note: This project is an extension to the project developed by Chris Pruett, 
which can be found here [1]. Feel free to read the README provided.

Basically, what this project aims to do is to offer you a simple class that
can be used to render 2D objects in an OpenGL surface.

I modified a little the project written by Chris Pruett by adding a wrapper
class corresponding to a scene. This allows you to use OpenGL very easily in
order to render 2D objects. You can instantiate this class, add various
sprites and have them drawn on the surface view.


Usage:
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

		// add sprites to the scene
		scene.addSprite(sprite, true);
		scene.addSprite(sprite2, true);
		scene.addSprite(sprite.clone(), true);
		scene.addSprite(sprite2.clone(), true);

		// this thread controls the sprites' positions
		scene.setMover(new Mover());
		return scene;
	}
}


[1] http://code.google.com/p/apps-for-android/source/browse/SpriteMethodTest