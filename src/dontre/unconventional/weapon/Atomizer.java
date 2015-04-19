package dontre.unconventional.weapon;

import java.util.Random;

public class Atomizer {
	public static void atomize(Screen screen, Sprite sprite, SimplePhysicController physicController)
	{
		Random r = new Random();
		for (int i=0; i<sprite.width; i++)
			for (int j=0; j<sprite.height; j++){
				SelfDestroyableSprite spr = new SelfDestroyableSprite(sprite.getX()+i, sprite.getY()+j, 1, 1, new int[]{sprite.getPixels()[(int) (j*sprite.width+i)]});
				physicController.addBody(spr, 0f, 0.001f, (r.nextFloat()-0.5f)/10f, (r.nextFloat()-0.5f)/10f, true);
				screen.sprites.add(spr);
			}
		screen.sprites.remove(sprite);
	}
}
