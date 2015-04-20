package dontre.unconventional.weapon;

public class SpriteWithHealth extends Sprite{
	float health = 100;
	public SpriteWithHealth(float x, float y, float width, float height,
			int[] pixels,float health) {
		super(x, y, width, height, pixels);
		
	}
	public void makeDamage(float delta)
	{
		health-=delta;
	}
	
}
